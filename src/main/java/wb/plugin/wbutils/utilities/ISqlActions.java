package wb.plugin.wbutils.utilities;

import java.sql.Connection;
import java.sql.SQLException;

public interface ISqlActions {

    void initialize();

    void loadDealsInfo();

    void saveDealsInfo();

    Connection getConnection() throws SQLException;
}