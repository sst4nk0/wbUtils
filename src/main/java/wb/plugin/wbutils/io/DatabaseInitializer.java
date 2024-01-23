package wb.plugin.wbutils.io;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseInitializer {

    private static final Logger LOGGER = Logger.getLogger(DatabaseInitializer.class.getName());
    private final DataSource ds;

    public DatabaseInitializer(final DataSource ds) {
        this.ds = ds;
    }

    public void initialize() {
        try (Connection connection = ds.getConnection()) {
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
        try (final PreparedStatement selectStmt = connection.prepareStatement(selectQuery); final ResultSet rs = selectStmt.executeQuery()) {
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
}