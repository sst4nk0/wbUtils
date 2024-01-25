package wb.plugin.wbutils.frameworks.repository;

import wb.plugin.wbutils.frameworks.DatabaseConnectionManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.concurrent.CompletableFuture;
import java.util.stream.StreamSupport;

public abstract class AsyncRepositoryImpl<T extends Entity<T>> implements AsyncRepository<T, Integer> {

    private final DatabaseConnectionManager connectionManager;
    private final Class<T> entityClass;
    private final String saveQuery;
    private final String updateQuery;
    private final String deleteQuery;
    private final String selectQuery;
    private final String selectAllQuery;
    private final String countQuery;
    private final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

    protected AsyncRepositoryImpl(DatabaseConnectionManager connectionManager, Class<T> entityClass, String saveQuery, String updateQuery, String deleteQuery, String selectQuery, String selectAllQuery, String countQuery) {
        this.connectionManager = connectionManager;
        this.entityClass = entityClass;
        this.saveQuery = saveQuery;
        this.updateQuery = updateQuery;
        this.deleteQuery = deleteQuery;
        this.selectQuery = selectQuery;
        this.selectAllQuery = selectAllQuery;
        this.countQuery = countQuery;
    }

    private static void setColumnValues(List<Object> columnValues, PreparedStatement statement) throws SQLException {
        for (int i = 0; i < columnValues.size(); i++) {
            statement.setObject(i + 1, columnValues.get(i));
        }
    }

    private CompletableFuture<Connection> getConnection() {
        Connection connection = connectionThreadLocal.get();
        if (connection == null) {
            return connectionManager.getConnectionAsync().thenApply(conn -> {
                connectionThreadLocal.set(conn);
                return conn;
            });
        }
        return CompletableFuture.completedFuture(connection);
    }

    private CompletableFuture<Void> closeConnection() {
        Connection connection = connectionThreadLocal.get();
        if (connection != null) {
            return connectionManager.closeConnectionAsync(connection).thenRun(connectionThreadLocal::remove);
        } else {
            connectionThreadLocal.remove();
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Boolean> existsById(Integer id) {
        Objects.requireNonNull(id, "Id must not be null");

        return getConnection().thenCompose(connection -> CompletableFuture.supplyAsync(() -> {
            try {
                PreparedStatement statement = connection.prepareStatement(selectQuery);
                statement.setObject(1, id);

                ResultSet resultSet = statement.executeQuery();
                return resultSet.next();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        })).whenComplete((result, throwable) -> closeConnection());
    }

    @Override
    public CompletableFuture<Optional<T>> findById(Integer id) {
        Objects.requireNonNull(id, "Id must not be null");

        return getConnection().thenCompose(connection -> CompletableFuture.supplyAsync(() -> {
            try (final PreparedStatement statement = connection.prepareStatement(selectQuery)) {
                statement.setObject(1, id);

                final ResultSet resultSet = statement.executeQuery();
                T entity = null;
                if (resultSet.next()) {
                    entity = entityClass.getDeclaredConstructor().newInstance().fromResultSet(resultSet);
                }
                return Optional.ofNullable(entity);
            } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        })).whenComplete((result, throwable) -> closeConnection());
    }

    @Override
    public CompletableFuture<List<T>> findAll() {
        return getConnection().thenCompose(connection -> CompletableFuture.supplyAsync(() -> {
            List<T> entities = new ArrayList<>();
            try {
                PreparedStatement statement = connection.prepareStatement(selectAllQuery);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    T entity = entityClass.getDeclaredConstructor().newInstance().fromResultSet(resultSet);
                    entities.add(entity);
                }
            } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            return entities;
        })).whenComplete((result, throwable) -> closeConnection());
    }

    @Override
    public CompletableFuture<List<T>> findAllById(Iterable<Integer> ids) {
        Objects.requireNonNull(ids, "Ids must not be null");

        return getConnection().thenCompose(connection -> CompletableFuture.supplyAsync(() -> {
            List<T> entities = new ArrayList<>();
            try {
                for (Integer id : ids) {
                    Objects.requireNonNull(id, "Id must not be null");
                    PreparedStatement statement = connection.prepareStatement(selectQuery);
                    statement.setObject(1, id);

                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        T entity = entityClass.getDeclaredConstructor().newInstance().fromResultSet(resultSet);
                        entities.add(entity);
                    }
                }
            } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            return entities;
        })).whenComplete((result, throwable) -> closeConnection());
    }

    @Override
    public CompletableFuture<T> save(T entity) {
        Objects.requireNonNull(entity, "Entity must not be null");

        return getConnection().thenCompose(connection -> existsById(entity.getId()).thenCompose(exists -> {
            try {
                connection.setAutoCommit(false);
                executeWrite(entity, connection, exists);
                connection.commit();
                return CompletableFuture.completedFuture(entity);
            } catch (SQLException e) {
                try {
                    if (!connection.isClosed() && !connection.getAutoCommit()) {
                        connection.rollback();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                return CompletableFuture.completedFuture(null);
            }
        })).whenComplete((result, throwable) -> closeConnection());
    }

    public CompletableFuture<T> save(T entity, Connection connection) {
        Objects.requireNonNull(entity, "Entity must not be null");

        return existsById(entity.getId()).thenCompose(exists -> {
            try {
                executeWrite(entity, connection, exists);
                return CompletableFuture.completedFuture(entity);
            } catch (SQLException e) {
                try {
                    if (!connection.isClosed() && !connection.getAutoCommit()) {
                        connection.rollback();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                return CompletableFuture.completedFuture(null);
            }
        });
    }

    private void executeWrite(T entity, Connection connection, Boolean exists) throws SQLException {
        PreparedStatement statement;
        List<Object> columnValues = entity.getColumnValues();
        if (Boolean.TRUE.equals(exists)) {
            statement = prepareUpdate(entity, connection, columnValues);
        } else {
            statement = prepareSave(connection, columnValues);
        }
        statement.executeUpdate();
    }

    private PreparedStatement prepareUpdate(T entity, Connection connection, List<Object> columnValues) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(updateQuery);
        setColumnValues(columnValues, statement);
        statement.setObject(columnValues.size() + 1, entity.getId());
        return statement;
    }

    private PreparedStatement prepareSave(Connection connection, List<Object> columnValues) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(saveQuery);
        setColumnValues(columnValues, statement);
        return statement;
    }

    @Override
    public CompletableFuture<List<T>> saveAll(Iterable<T> entities) {
        Objects.requireNonNull(entities, "Entities must not be null");

        return getConnection().thenCompose(connection -> {
            try {
                connection.setAutoCommit(false);
                final Spliterator<T> spliterator = entities.spliterator();
                final List<CompletableFuture<T>> futures = StreamSupport.stream(spliterator, false).map(entity -> save(entity, connection)).toList();
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
                connection.commit();
                return CompletableFuture.completedFuture(StreamSupport.stream(spliterator, true).toList());
            } catch (SQLException e) {
                try {
                    if (!connection.isClosed() && !connection.getAutoCommit()) {
                        connection.rollback();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                throw new RuntimeException(e);
            }
        }).whenComplete((result, throwable) -> closeConnection());
    }

    @Override
    public CompletableFuture<Long> count() {
        return getConnection().thenCompose(connection -> CompletableFuture.supplyAsync(() -> {
            try {
                PreparedStatement statement = connection.prepareStatement(countQuery);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                } else {
                    throw new RuntimeException("Failed to count entities");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        })).whenComplete((result, throwable) -> closeConnection());
    }

    @Override
    public CompletableFuture<Void> deleteById(Integer id) {
        Objects.requireNonNull(id, "Id must not be null");

        return getConnection().thenCompose(connection -> CompletableFuture.runAsync(() -> {
            try {
                PreparedStatement statement = connection.prepareStatement(deleteQuery);
                statement.setObject(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        })).whenComplete((result, throwable) -> closeConnection());
    }

    @Override
    public CompletableFuture<Void> delete(T entity) {
        Objects.requireNonNull(entity, "Entity must not be null");

        Integer id = entity.getId();
        return deleteById(id);
    }

    @Override
    public CompletableFuture<Void> deleteAllById(Iterable<Integer> ids) {
        Objects.requireNonNull(ids, "Ids must not be null");
        return CompletableFuture.allOf(StreamSupport.stream(ids.spliterator(), false).map(this::deleteById).toArray(CompletableFuture[]::new));
    }

    @Override
    public CompletableFuture<Void> deleteAll(Iterable<T> entities) {
        Objects.requireNonNull(entities, "Entities must not be null");

        return CompletableFuture.allOf(StreamSupport.stream(entities.spliterator(), false).map(this::delete).toArray(CompletableFuture[]::new));
    }

    @Override
    public CompletableFuture<Void> deleteAll() {
        return getConnection().thenCompose(connection -> CompletableFuture.runAsync(() -> {
            try {
                PreparedStatement statement = connection.prepareStatement(deleteQuery);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        })).whenComplete((result, throwable) -> closeConnection());
    }
}
