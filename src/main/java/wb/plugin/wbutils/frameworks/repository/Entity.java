package wb.plugin.wbutils.frameworks.repository;

import java.sql.ResultSet;
import java.util.List;

public abstract class Entity<T> {

    protected Entity() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    public abstract int getId();

    public abstract List<Object> getColumnValues();

    public abstract T fromResultSet(ResultSet resultSet);
}
