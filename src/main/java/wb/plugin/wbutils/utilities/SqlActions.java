package wb.plugin.wbutils.utilities;

import wb.plugin.wbutils.wbUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlActions{

    public String url = String.format("jdbc:mysql://%s/%s?allowMultiQueries=true",
            wbUtils.getInstance().getConfig().getString("db-connection.address"),
            wbUtils.getInstance().getConfig().getString("db-connection.database"));
    public String username = wbUtils.getInstance().getConfig().getString("db-connection.username");
    public String password = wbUtils.getInstance().getConfig().getString("db-connection.password");
    public Connection connection = null;


    public SqlActions firstConnection() {
        try{
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to database successfully.");
            //============ Первоначальная настройка
            try{
                String setupTable = "CREATE TABLE IF NOT EXISTS wb_deals(id int auto_increment, owner char, coins_copper int, coins_silver int, coins_gold int, materials int, PRIMARY KEY (id)); " +
                        "INSERT INTO wb_deals(`id`, `owner`, `coins_copper`, `coins_silver`, `coins_gold`, `materials`) VALUES (NULL, '-', '0', '0', '0', '0')";
                connection.createStatement().execute(setupTable);
                System.out.println("First setup is complited.");
            }catch (SQLException e) {}
            //============ // ` '
            connection.close();
        }catch(SQLException e){
            System.out.println("Failed connection to database.");
            e.printStackTrace();
        }
        return null;
    }
    public SqlActions loadDealStats() {
        try{
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to database successfully.");
            //============ Первоначальная настройка
            try{
                String setupTable = "CREATE TABLE IF NOT EXISTS wb_deals(id int auto_increment, owner char, coins_copper int, coins_silver int, coins_gold int, materials int, PRIMARY KEY (id)); " +
                        "INSERT INTO wb_deals(`id`, `owner`, `coins_copper`, `coins_silver`, `coins_gold`, `materials`) VALUES (NULL, '-', '0', '0', '0', '0')";
                connection.createStatement().execute(setupTable);
                System.out.println("First setup is complited.");
            }catch (SQLException e) {}
            //============ // ` '
            connection.close();
        }catch(SQLException e){
            System.out.println("Failed connection to database.");
            e.printStackTrace();
        }
        return null;
    }
}
