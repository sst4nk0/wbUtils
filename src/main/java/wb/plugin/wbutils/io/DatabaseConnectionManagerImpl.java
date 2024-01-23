package wb.plugin.wbutils.io;

import org.bukkit.plugin.java.JavaPlugin;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnectionManagerImpl implements DatabaseConnectionManager {

    private static final Logger LOGGER = Logger.getLogger(DatabaseConnectionManagerImpl.class.getName());
    private final DataSource ds;

    public DatabaseConnectionManagerImpl(final JavaPlugin plugin) {
        final DataSourceConfigurator configurator = new DataSourceConfigurator(plugin.getConfig());
        ds = configurator.getDataSource();
        DatabaseInitializer initializer = new DatabaseInitializer(ds);
        initializer.initialize();
    }

    public CompletableFuture<Connection> getConnectionAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return ds.getConnection();
            } catch (final SQLException e) {
                LOGGER.log(Level.SEVERE, "Error getting connection", e);
                return null;
            }
        });
    }

    public CompletableFuture<Void> closeConnectionAsync(final Connection connection) {
        return CompletableFuture.runAsync(() -> {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (final SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing connection", e);
            }
        });
    }
}
