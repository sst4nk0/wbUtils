package wb.plugin.wbutils.utilities.repository;

import java.sql.ResultSet;
import java.util.List;

public abstract class Entity<T> {

    public abstract List<Object> getColumnValues();

    public abstract T fromResultSet(ResultSet resultSet);

    public abstract int getId();
}
