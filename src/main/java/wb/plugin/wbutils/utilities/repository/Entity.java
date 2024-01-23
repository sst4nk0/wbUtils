package wb.plugin.wbutils.utilities.repository;

import java.sql.ResultSet;
import java.util.List;

public interface Entity {

    List<Object> getColumnValues();

    Entity fromResultSet(ResultSet resultSet);

    int getId();
}
