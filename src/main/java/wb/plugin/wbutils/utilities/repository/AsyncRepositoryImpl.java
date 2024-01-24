package wb.plugin.wbutils.utilities.repository;

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
import java.util.concurrent.CompletableFuture;
import java.util.stream.StreamSupport;

public class AsyncRepositoryImpl<T extends Entity<T>> implements AsyncRepository<T, Integer> {

    private final DatabaseConnectionManager connectionManager;
    private final Class<T> entityClass;
    private final String saveQuery;
    private final String updateQuery;
    private final String deleteQuery;
    private final String selectQuery;
    private final String selectAllQuery;
    private final String countQuery;
    private final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

    public AsyncRepositoryImpl(DatabaseConnectionManager connectionManager, Class<T> entityClass, String saveQuery, String updateQuery, String deleteQuery, String selectQuery, String selectAllQuery, String countQuery) {
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
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<T> save(T entity) {
        Objects.requireNonNull(entity, "Entity must not be null");

        return getConnection().thenCompose(connection -> existsById(entity.getId()).thenCompose(exists -> {
            try {
                connection.setAutoCommit(false);
                PreparedStatement statement;
                List<Object> columnValues = entity.getColumnValues();
                if (exists) {
                    statement = connection.prepareStatement(updateQuery);
                    setColumnValues(columnValues, statement);
                    statement.setObject(columnValues.size() + 1, entity.getId());
                } else {
                    statement = connection.prepareStatement(saveQuery);
                    setColumnValues(columnValues, statement);
                }
                statement.executeUpdate();
                connection.commit();
                return CompletableFuture.completedFuture(entity);
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                return CompletableFuture.completedFuture(null);
            }
        })).whenComplete((result, throwable) -> closeConnection());
    }

    @Override
    public CompletableFuture<Iterable<T>> saveAll(Iterable<T> entities) {
        return CompletableFuture.supplyAsync(() -> {
            Objects.requireNonNull(entities, "Entities must not be null");
            // TODO: commit only when necessary (save is used)
            return StreamSupport.stream(entities.spliterator(), true).map(this::save).map(CompletableFuture::join).toList();
        });
    }

    @Override
    public CompletableFuture<Optional<T>> findById(Integer id) {
        Objects.requireNonNull(id, "Id must not be null");

        return getConnection().thenCompose(connection -> CompletableFuture.supplyAsync(() -> {
            try {
                PreparedStatement statement = connection.prepareStatement(selectQuery);
                statement.setObject(1, id);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    T entity = entityClass.getDeclaredConstructor().newInstance().fromResultSet(resultSet);
                    return Optional.ofNullable(entity);
                } else {
                    return Optional.empty();
                }
            } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            } finally {
                closeConnection().join();
            }
        }));
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
        return CompletableFuture.allOf(StreamSupport.stream(ids.spliterator(), true).map(this::deleteById).toArray(CompletableFuture[]::new));
    }

    @Override
    public CompletableFuture<Void> deleteAll(Iterable<T> entities) {
        Objects.requireNonNull(entities, "Entities must not be null");

        return CompletableFuture.allOf(StreamSupport.stream(entities.spliterator(), true).map(this::delete).toArray(CompletableFuture[]::new));
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
