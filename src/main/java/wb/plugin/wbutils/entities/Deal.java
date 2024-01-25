package wb.plugin.wbutils.entities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wb.plugin.wbutils.frameworks.repository.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public final class Deal extends Entity<Deal> {

    private final int dealId;
    private final @NotNull String owner;
    private final @NotNull String copperCoins;
    private final @NotNull String silverCoins;
    private final @NotNull String goldCoins;
    private final @NotNull String materials;

    public Deal() {
        super();
        dealId = 0;
        owner = "";
        copperCoins = "";
        silverCoins = "";
        goldCoins = "";
        materials = "";
    }

    public Deal(final int dealId, final @NotNull String owner, final @NotNull String copperCoins,
                final @NotNull String silverCoins, final @NotNull String goldCoins, final @NotNull String materials) {
        super();
        this.dealId = dealId;
        this.owner = owner;
        this.copperCoins = copperCoins;
        this.silverCoins = silverCoins;
        this.goldCoins = goldCoins;
        this.materials = materials;
    }

    @Override
    public @NotNull List<@NotNull Object> getColumnValues() {
        return List.of(owner, copperCoins, silverCoins, goldCoins, materials);
    }

    @Override
    public @NotNull Deal fromResultSet(final ResultSet resultSet) {
        try {
            final int getDealId = resultSet.getInt("id");
            if (getDealId == 0) {
                throw new IllegalArgumentException("Deal ID cannot be 0");
            }

            final String theOwner = Objects.requireNonNull(resultSet.getString("owner"));
            final String theCopperCoins = Objects.requireNonNull(resultSet.getString("coins_copper"));
            final String theSilverCoins = Objects.requireNonNull(resultSet.getString("coins_silver"));
            final String theGoldCoins = Objects.requireNonNull(resultSet.getString("coins_gold"));
            final String getMaterials = Objects.requireNonNull(resultSet.getString("materials"));
            return new Deal(getDealId, theOwner, theCopperCoins, theSilverCoins, theGoldCoins, getMaterials);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public int getId() {
        return dealId;
    }

    public @NotNull String getOwner() {
        return owner;
    }

    public @NotNull String getCopperCoins() {
        return copperCoins;
    }

    public @NotNull String getSilverCoins() {
        return silverCoins;
    }

    public @NotNull String getGoldCoins() {
        return goldCoins;
    }

    public @NotNull String getMaterials() {
        return materials;
    }

    @Override
    public boolean equals(final @Nullable Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        final Deal that = (Deal) obj;
        return this.dealId == that.dealId
                && Objects.equals(this.owner, that.owner)
                && Objects.equals(this.copperCoins, that.copperCoins)
                && Objects.equals(this.silverCoins, that.silverCoins)
                && Objects.equals(this.goldCoins, that.goldCoins)
                && Objects.equals(this.materials, that.materials);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dealId, owner, copperCoins, silverCoins, goldCoins, materials);
    }

    @Override
    public String toString() {
        return "Deal["
                + "id=" + dealId + ", "
                + "owner=" + owner + ", "
                + "coins_copper=" + copperCoins + ", "
                + "coins_silver=" + silverCoins + ", "
                + "coins_gold=" + goldCoins + ", "
                + "materials=" + materials + ']';
    }
}
