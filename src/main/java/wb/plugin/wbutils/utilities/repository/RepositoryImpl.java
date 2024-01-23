package wb.plugin.wbutils.utilities.repository;

import wb.plugin.wbutils.io.DatabaseConnectionManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class RepositoryImpl<T extends Entity> implements Repository<T, Integer> {

    private final DatabaseConnectionManager connectionManager;
    private final Class<T> entityClass;
    private final String saveQuery;
    private final String updateQuery;
    private final String deleteQuery;
    private final String selectQuery;
    private final String selectAllQuery;
    private final String countQuery;

    public RepositoryImpl(DatabaseConnectionManager connectionManager, Class<T> entityClass, String saveQuery, String updateQuery, String deleteQuery, String selectQuery, String selectAllQuery, String countQuery) {
        this.connectionManager = connectionManager;
        this.entityClass = entityClass;
        this.saveQuery = saveQuery;
        this.updateQuery = updateQuery;
        this.deleteQuery = deleteQuery;
        this.selectQuery = selectQuery;
        this.selectAllQuery = selectAllQuery;
        this.countQuery = countQuery;
    }

    @Override
    public T save(T entity) {
        Objects.requireNonNull(entity, "Entity must not be null");

        final Connection connection = connectionManager.getConnectionAsync().join();
        try {
            connection.setAutoCommit(false);
            if (existsById(entity.getId())) {
                System.out.println("Exists");
                // Update existing entity
                PreparedStatement statement = connection.prepareStatement(updateQuery);
                List<Object> columnValues = entity.getColumnValues();
                for (int i = 0; i < columnValues.size(); i++) {
                    System.out.println(columnValues.get(i));
                    statement.setObject(i + 1, columnValues.get(i));
                }
                statement.setObject(columnValues.size() + 1, entity.getId()); // Set id for WHERE clause
                statement.executeUpdate();
            } else {
                // Save new entity
                PreparedStatement statement = connection.prepareStatement(saveQuery);
                List<Object> columnValues = entity.getColumnValues();
                for (int i = 0; i < columnValues.size(); i++) {
                    statement.setObject(i + 1, columnValues.get(i));
                }
                statement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            connectionManager.closeConnectionAsync(connection);
        }

        return entity;
    }

    @Override
    public List<T> saveAll(Iterable<T> entities) {

        Objects.requireNonNull(entities, "Entity must not be null");

        List<T> result = new ArrayList<>();
        for (T entity : entities) {
            result.add(save(entity));
        }

        return result;
    }

    @Override
    public Optional<T> findById(Integer id) {
        Objects.requireNonNull(id, "Id must not be null");
        final Connection connection = connectionManager.getConnectionAsync().join();
        try {
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.setObject(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                T entity = (T) entityClass.getDeclaredConstructor().newInstance().fromResultSet(resultSet);
                return Optional.ofNullable(entity);
            } else {
                return Optional.empty();
            }
        } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        } finally {
            connectionManager.closeConnectionAsync(connection);
        }
    }

    @Override
    public boolean existsById(Integer id) {
        Objects.requireNonNull(id, "Id must not be null");
        // TODO: select one

        final Connection connection = connectionManager.getConnectionAsync().join();
        try {
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.setObject(1, id);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionManager.closeConnectionAsync(connection);
        }
    }

    @Override
    public Iterable<T> findAll() {
        final Connection connection = connectionManager.getConnectionAsync().join();
        List<T> entities = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(selectAllQuery);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                T entity = (T) entityClass.getDeclaredConstructor().newInstance().fromResultSet(resultSet);
                entities.add(entity);
            }
        } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        } finally {
            connectionManager.closeConnectionAsync(connection);
        }
        return entities;
    }

    @Override
    public Iterable<T> findAllById(Iterable<Integer> ids) {
        Objects.requireNonNull(ids, "Ids must not be null");

        final Connection connection = connectionManager.getConnectionAsync().join();
        List<T> entities = new ArrayList<>();
        try {
            for (Integer id : ids) {
                Objects.requireNonNull(id, "Id must not be null");
                // Select all with id
                PreparedStatement statement = connection.prepareStatement(selectQuery);
                statement.setObject(1, id);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    // TODO: unhardcode
                    T entity = (T) entityClass.getDeclaredConstructor().newInstance().fromResultSet(resultSet);
                    entities.add(entity);
                }
            }
        } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        } finally {
            connectionManager.closeConnectionAsync(connection);
        }
        return entities;
    }

    @Override
    public long count() {
        final Connection connection = connectionManager.getConnectionAsync().join();
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
        } finally {
            connectionManager.closeConnectionAsync(connection);
        }
    }

    @Override
    public void deleteById(Integer id) {
        Objects.requireNonNull(id, "Id must not be null");

        final Connection connection = connectionManager.getConnectionAsync().join();
        try {
            PreparedStatement statement = connection.prepareStatement(deleteQuery);
            statement.setObject(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionManager.closeConnectionAsync(connection);
        }
    }

    @Override
    public void delete(T entity) {
        Objects.requireNonNull(entity, "Entity must not be null");

        Integer id = entity.getId();
        deleteById(id);
    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> ids) {
        Objects.requireNonNull(ids, "Ids must not be null");

        final Connection connection = connectionManager.getConnectionAsync().join();
        try {
            for (Integer id : ids) {
                Objects.requireNonNull(id, "Id must not be null");

                PreparedStatement statement = connection.prepareStatement(deleteQuery);
                statement.setObject(1, id);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionManager.closeConnectionAsync(connection);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        Objects.requireNonNull(entities, "Entities must not be null");

        for (T entity : entities) {
            delete(entity);
        }
    }

    @Override
    public void deleteAll() {
        final Connection connection = connectionManager.getConnectionAsync().join();
        try {
            PreparedStatement statement = connection.prepareStatement(deleteQuery);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionManager.closeConnectionAsync(connection);
        }
    }
}
