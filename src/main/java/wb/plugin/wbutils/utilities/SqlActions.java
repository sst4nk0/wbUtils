package wb.plugin.wbutils.utilities;

import wb.plugin.wbutils.WbUtils;
import wb.plugin.wbutils.deals.DatabaseDeals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlActions {

    public static final String URL = String.format("jdbc:mysql://%s/%s?allowMultiQueries=true",
            WbUtils.getInstance().getConfig().getString("db-connection.address"),
            WbUtils.getInstance().getConfig().getString("db-connection.database"));
    public static final String USERNAME = WbUtils.getInstance().getConfig().getString("db-connection.username");
    public static final String PASSWORD = WbUtils.getInstance().getConfig().getString("db-connection.password");

    private static String getAddNewDeal(final int i) {
        final String owner = DatabaseDeals.getOwner(i);
        final String coins_copper = DatabaseDeals.getCoinsCopper(i);
        final String coins_silver = DatabaseDeals.getCoinsSilver(i);
        final String coins_gold = DatabaseDeals.getCoinsGold(i);
        final String materials = DatabaseDeals.getMaterials(i);
        return String.format("UPDATE `wb_deals` SET `owner` = '%s', `coins_copper` = '%s', `coins_silver` = '%s', `coins_gold` = '%s', `materials` = '%s' WHERE `id` = '%s'", owner, coins_copper, coins_silver, coins_gold, materials, i);
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public void firstConnection() {
        final String createTableQuery = "CREATE TABLE IF NOT EXISTS wb_deals(id int auto_increment, owner varchar(16), coins_copper int, coins_silver int, coins_gold int, materials int, PRIMARY KEY (id))";
        final String selectQuery = "SELECT COUNT(*) FROM wb_deals";
        final String insertQuery = "INSERT INTO wb_deals(owner, coins_copper, coins_silver, coins_gold, materials) VALUES (?, ?, ?, ?, ?)";

        try (final Connection connection = getConnection();
             final PreparedStatement createTableStmt = connection.prepareStatement(createTableQuery);
             final PreparedStatement selectStmt = connection.prepareStatement(selectQuery);
             final PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {

            // Create table if not exists
            createTableStmt.execute();

            // Check if the table is empty and insert initial data
            try (final ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
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
        } catch (SQLException e) {
            System.out.println("Failed connection to database.");
            e.printStackTrace();
        }
    }

    public void saveDealsInfo() {
        try (final Connection connection = getConnection(); final Statement stmt = connection.createStatement()) {
            for (int i = 1; i < 26; i++) {
                final String addNewDeal = getAddNewDeal(i);
                stmt.addBatch(addNewDeal);
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            System.out.println("Failed connection to database.");
            e.printStackTrace();
        }
    }

    public void loadDealsInfo() {
        final String query = "SELECT * FROM wb_deals";
        try (final Connection connection = getConnection();
             final PreparedStatement stmt = connection.prepareStatement(query);
             final ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                DatabaseDeals.addDealInfo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
            }
        } catch (SQLException e) {
            System.out.println("Failed connection to database.");
            e.printStackTrace();
        }
    }
}
