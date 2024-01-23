package wb.plugin.wbutils.frameworks;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;

public class DataSourceConfigurator {

    private final FileConfiguration pluginConfig;

    public DataSourceConfigurator(final FileConfiguration pluginConfig) {
        this.pluginConfig = pluginConfig;
    }

    @NotNull
    public DataSource getDataSource() {
        final HikariConfig config = getHikariBasicConfig();
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
    private HikariConfig getHikariBasicConfig() {
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
}