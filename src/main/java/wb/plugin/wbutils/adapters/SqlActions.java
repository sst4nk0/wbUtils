package wb.plugin.wbutils.adapters;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.entities.Deal;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlActions implements ISqlActions {

    private static final Logger LOGGER = Logger.getLogger(SqlActions.class.getName());
    private final IDatabaseDeals databaseDeals;
    private final DataSource ds;

    public SqlActions(final JavaPlugin plugin, final IDatabaseDeals databaseDeals) {
        this.databaseDeals = databaseDeals;
        ds = getDataSource(plugin.getConfig());
    }

    @NotNull
    private static HikariDataSource getDataSource(final FileConfiguration pluginConfig) {
        final HikariConfig config = getHikariBasicConfig(pluginConfig);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        return new HikariDataSource(config);
    }

    @NotNull
    private static HikariConfig getHikariBasicConfig(final FileConfiguration pluginConfig) {
        final HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setDriverClassName("org.mariadb.jdbc.Driver");

        final String database = pluginConfig.getString("db-connection.database");
        final String address = pluginConfig.getString("db-connection.address");
        final String url = String.format("jdbc:mariadb://%s/%s", address, database);
        hikariConfig.setJdbcUrl(url);

        final String username = pluginConfig.getString("db-connection.username");
        hikariConfig.setUsername(username);

        final String password = pluginConfig.getString("db-connection.password");
        hikariConfig.setPassword(password);
        return hikariConfig;
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public void closeConnection() throws SQLException {
        ds.getConnection().close();
    }

    public void initialize() {
        try (final Connection connection = getConnection()) {
            createTableIfNotExists(connection);
            if (isTableEmpty(connection)) {
                insertInitialData(connection);
            }
        } catch (final SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize database", e);
        }
    }

    private void createTableIfNotExists(final Connection connection) throws SQLException {
        final String createTableQuery = "CREATE TABLE IF NOT EXISTS wb_deals(id int auto_increment, owner varchar(16), coins_copper int, coins_silver int, coins_gold int, materials int, PRIMARY KEY (id))";
        try (final PreparedStatement createTableStmt = connection.prepareStatement(createTableQuery)) {
            createTableStmt.execute();
        }
    }

    private boolean isTableEmpty(final Connection connection) throws SQLException {
        final String selectQuery = "SELECT COUNT(*) FROM wb_deals";
        try (final PreparedStatement selectStmt = connection.prepareStatement(selectQuery);
             final ResultSet rs = selectStmt.executeQuery()) {
            return rs.next() && rs.getInt(1) == 0;
        }
    }

    private void insertInitialData(final Connection connection) throws SQLException {
        final String insertQuery = "INSERT INTO wb_deals(owner, coins_copper, coins_silver, coins_gold, materials) VALUES (?, ?, ?, ?, ?)";
        try (final PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
            insertStmt.setString(1, "-");
            insertStmt.setInt(2, 0);
            insertStmt.setInt(3, 0);
            insertStmt.setInt(4, 0);
            insertStmt.setInt(5, 16);

            for (int i = 0; i < 27; i++) {
                insertStmt.addBatch();
            }
            insertStmt.executeBatch();
        }
    }

    public void saveDealsInfo() {
        final String updateQuery = "UPDATE `wb_deals` SET `owner` = ?, `coins_copper` = ?, `coins_silver` = ?, `coins_gold` = ?, `materials` = ? WHERE `id` = ?";

        try (final Connection connection = getConnection();
             final PreparedStatement stmt = connection.prepareStatement(updateQuery)) {

            for (int i = 1; i < databaseDeals.getDealsQuantity() + 1; i++) {
                Deal deal = databaseDeals.getDeal(i);
                stmt.setString(1, deal.owner());
                stmt.setString(2, deal.coins_copper());
                stmt.setString(3, deal.coins_silver());
                stmt.setString(4, deal.coins_gold());
                stmt.setString(5, deal.materials());
                stmt.setInt(6, i);
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (final SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to save deals info", e);
        }
    }

    public void loadDealsInfo() {
        final String query = "SELECT * FROM wb_deals";
        try (final Connection connection = getConnection();
             final PreparedStatement stmt = connection.prepareStatement(query);
             final ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Deal deal = new Deal(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                databaseDeals.addDeal(deal);
            }
        } catch (final SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to load deals info", e);
        }
    }
}
