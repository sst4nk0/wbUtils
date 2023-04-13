package wb.plugin.wbutils.utilities;

import wb.plugin.wbutils.deals.DatabaseDeals;
import wb.plugin.wbutils.wbUtils;

import java.sql.*;

public class SqlActions{

    public String url = String.format("jdbc:mysql://%s/%s?allowMultiQueries=true",
            wbUtils.getInstance().getConfig().getString("db-connection.address"),
            wbUtils.getInstance().getConfig().getString("db-connection.database"));
    public String username = wbUtils.getInstance().getConfig().getString("db-connection.username");
    public String password = wbUtils.getInstance().getConfig().getString("db-connection.password");
    public Connection connection = null;




    public void firstConnection() {
        PreparedStatement stmt = null;
        ResultSet rs;
        String query = "CREATE TABLE IF NOT EXISTS wb_deals(id int auto_increment, owner varchar(16), coins_copper int, coins_silver int, coins_gold int, materials int, PRIMARY KEY (id))";

        try{
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to database successfully.");
            try {
                stmt = connection.prepareStatement(query);
                stmt.execute();

                rs = stmt.executeQuery("SELECT * FROM wb_deals");
                int dealsQuantity = 0;
                while( rs.next() ) {
                    dealsQuantity++;
                }

                if (dealsQuantity == 0) /*Первоначальная настройка*/ {
                    String addNewDeal = "INSERT INTO wb_deals(`id`, `owner`, `coins_copper`, `coins_silver`, `coins_gold`, `materials`) VALUES (NULL, '-', '0', '0', '0', '0')";
                    for (int i=0; i<27; i++) { stmt.addBatch(addNewDeal); }
                    stmt.executeBatch();
                }
            } catch (SQLException e) {}
            stmt.close();
            connection.close();
        }catch(SQLException e) {
            System.out.println("Failed connection to database.");
            e.printStackTrace();
        }
    }

    public void saveDealsInfo() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            Statement stmt = connection.createStatement();
            try{
                for (int i=1; i<26; i++) {
                    String owner = DatabaseDeals.getOwner(i);
                    String coins_copper = DatabaseDeals.getCoinsCopper(i);
                    String coins_silver = DatabaseDeals.getCoinsSilver(i);
                    String coins_gold = DatabaseDeals.getCoinsGold(i);
                    String materials = DatabaseDeals.getMaterials(i);
                    String addNewDeal = String.format("UPDATE `wb_deals` SET `owner` = '%s', `coins_copper` = '%s', `coins_silver` = '%s', `coins_gold` = '%s', `materials` = '%s' WHERE `id` = '%s'", owner, coins_copper, coins_silver, coins_gold, materials, i);
                    stmt.addBatch(addNewDeal);
                }
                stmt.executeBatch();
                stmt.close();
                connection.close();
            }catch(SQLException e) {
                System.out.println("Failed connection to database.");
                e.printStackTrace();
            }
        } catch(SQLException e) {
            System.out.println("Failed connection to database.");
            e.printStackTrace();
        }
    }

    public void loadDealsInfo() {
        PreparedStatement stmt = null;
        ResultSet rs;
        String query = "SELECT * FROM wb_deals";
        try{
            connection = DriverManager.getConnection(url, username, password);
            try {
                stmt = connection.prepareStatement(query);
                rs = stmt.executeQuery();
                while( rs.next() ) {
                    DatabaseDeals.addDealInfo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                }
            } catch (SQLException e) {}
            stmt.close();
            connection.close();
        } catch(SQLException e) {
            System.out.println("Failed connection to database.");
            e.printStackTrace();
        }
    }
}
