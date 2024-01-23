package wb.plugin.wbutils.entities;

import wb.plugin.wbutils.utilities.repository.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public final class Deal implements Entity {
    private final int id;
    private final String owner;
    private final String coins_copper;
    private final String coins_silver;
    private final String coins_gold;
    private final String materials;

    public Deal() {
        id = 0;
        owner = "";
        coins_copper = "";
        coins_silver = "";
        coins_gold = "";
        materials = "";
    }

    public Deal(int id, String owner, String coins_copper, String coins_silver, String coins_gold,
                String materials) {
        this.id = id;
        this.owner = owner;
        this.coins_copper = coins_copper;
        this.coins_silver = coins_silver;
        this.coins_gold = coins_gold;
        this.materials = materials;
    }

    @Override
    public List<Object> getColumnValues() {
        return List.of(owner, coins_copper, coins_silver, coins_gold, materials);
    }

    @Override
    public Deal fromResultSet(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("id");
            String owner = resultSet.getString("owner");
            String coins_copper = resultSet.getString("coins_copper");
            String coins_silver = resultSet.getString("coins_silver");
            String coins_gold = resultSet.getString("coins_gold");
            String materials = resultSet.getString("materials");

            return new Deal(id, owner, coins_copper, coins_silver, coins_gold, materials);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getId() {
        return id;
    }

    public int id() {
        return id;
    }

    public String owner() {
        return owner;
    }

    public String coins_copper() {
        return coins_copper;
    }

    public String coins_silver() {
        return coins_silver;
    }

    public String coins_gold() {
        return coins_gold;
    }

    public String materials() {
        return materials;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Deal) obj;
        return this.id == that.id &&
                Objects.equals(this.owner, that.owner) &&
                Objects.equals(this.coins_copper, that.coins_copper) &&
                Objects.equals(this.coins_silver, that.coins_silver) &&
                Objects.equals(this.coins_gold, that.coins_gold) &&
                Objects.equals(this.materials, that.materials);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, coins_copper, coins_silver, coins_gold, materials);
    }

    @Override
    public String toString() {
        return "Deal[" +
                "id=" + id + ", " +
                "owner=" + owner + ", " +
                "coins_copper=" + coins_copper + ", " +
                "coins_silver=" + coins_silver + ", " +
                "coins_gold=" + coins_gold + ", " +
                "materials=" + materials + ']';
    }

}
