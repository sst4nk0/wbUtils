package wb.plugin.wbutils.adapters;

import java.sql.Connection;
import java.sql.SQLException;

public interface ISqlActions {

    void initialize();

    void loadDealsInfo();

    void saveDealsInfo();

    Connection getConnection() throws SQLException;
}