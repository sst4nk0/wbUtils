package wb.plugin.wbutils.deals;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class PlaceholderDealInfo extends PlaceholderExpansion {

    private final JavaPlugin plugin;
    private final IDatabaseDeals databaseDeals;

    /**
     * Initialize a constructor.
     */
    public PlaceholderDealInfo(final JavaPlugin plugin, final IDatabaseDeals databaseDeals) {
        this.plugin = plugin;
        this.databaseDeals = databaseDeals;
    }

    /**
     * Do something.
     *
     * @return some boolean.
     */
    @Override
    public boolean persist() {
        return true;
    }

    /**
     * Do something.
     *
     * @return some string
     */
    @Override
    public @NotNull String getIdentifier() {
        return "wbutils";
    }

    /**
     * Do something.
     *
     * @return some string
     */
    @Override
    public @NotNull String getAuthor() {
        return "sst4nk0";
    }

    /**
     * Do something.
     *
     * @return some string
     */
    @Override
    public @NotNull String getVersion() {
        return "0.2.0";
    }

    /**
     * Do something.
     *
     * @return some boolean
     */
    @Override
    public boolean canRegister() {
        return true;
    }

    /**
     * Do something.
     *
     * @param player     player
     * @param identifier identifier
     * @return some String
     */
    @Override
    public String onPlaceholderRequest(final Player player, final String identifier) {
        if (identifier.startsWith("dealowner_")) {
            int dealId = Integer.parseInt(identifier.replace("dealowner_", ""));
            return databaseDeals.getOwner(dealId);
        } else if (identifier.startsWith("dealcopper_")) {
            int dealId = Integer.parseInt(identifier.replace("dealcopper_", ""));
            return databaseDeals.getCoinsCopper(dealId);
        } else if (identifier.startsWith("dealsilver_")) {
            int dealId = Integer.parseInt(identifier.replace("dealsilver_", ""));
            return databaseDeals.getCoinsSilver(dealId);
        } else if (identifier.startsWith("dealgold_")) {
            int dealId = Integer.parseInt(identifier.replace("dealgold_", ""));
            return databaseDeals.getCoinsGold(dealId);
        } else if (identifier.startsWith("dealmaterials_")) {
            int dealId = Integer.parseInt(identifier.replace("dealmaterials_", ""));
            return databaseDeals.getMaterials(dealId);
        } else if (identifier.startsWith("dealstatus_")) {
            int dealId = Integer.parseInt(identifier.replace("dealstatus_", ""));
            int dealMaterials = Integer.parseInt(databaseDeals.getMaterials(dealId));
            if (dealMaterials > -1) {
                return " ";
            } else {
                return "(сделка неустойчива)";
            }
        } else {
            return null;
        }
    }
}
