package wb.plugin.wbutils.utilities;

import wb.plugin.wbutils.wbUtils;

import java.sql.*;

public class SqlActions{

    public String url = String.format("jdbc:mysql://%s/%s?allowMultiQueries=true",
            wbUtils.getInstance().getConfig().getString("db-connection.address"),
            wbUtils.getInstance().getConfig().getString("db-connection.database"));
    public String username = wbUtils.getInstance().getConfig().getString("db-connection.username");
    public String password = wbUtils.getInstance().getConfig().getString("db-connection.password");
    public Connection connection = null;


    public SqlActions firstConnection() {
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
                while( rs.next() ) { dealsQuantity++; }

                if (dealsQuantity == 0) /*Первоначальная настройка*/ {
                    String addDeal1 = "INSERT INTO wb_deals(`id`, `owner`, `coins_copper`, `coins_silver`, `coins_gold`, `materials`) VALUES (NULL, '-', '0', '0', '0', '0')";
                    stmt.addBatch(addDeal1);
                    stmt.executeBatch();
                }
            } catch (SQLException e) {}
            stmt.close();
            connection.close();
        }catch(SQLException e){
            System.out.println("Failed connection to database.");
            e.printStackTrace();
        }
        return null;
    }
    public void loadDealInfo() {
        ResultSet rs = null;
        try{
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM wb_deals");
        }catch (SQLException e) {}

    }
}
