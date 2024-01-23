package wb.plugin.wbutils.adapters;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.entities.Deal;

public class DealInfoPlaceholder extends PlaceholderExpansion {

    private final JavaPlugin plugin;
    private final DealsRepository databaseDeals;

    /**
     * Initialize a constructor.
     */
    public DealInfoPlaceholder(final JavaPlugin plugin, final DealsRepository databaseDeals) {
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
            Deal deal = databaseDeals.getDeal(dealId);
            return deal.owner();
        } else if (identifier.startsWith("dealcopper_")) {
            int dealId = Integer.parseInt(identifier.replace("dealcopper_", ""));
            Deal deal = databaseDeals.getDeal(dealId);
            return deal.coins_copper();
        } else if (identifier.startsWith("dealsilver_")) {
            int dealId = Integer.parseInt(identifier.replace("dealsilver_", ""));
            Deal deal = databaseDeals.getDeal(dealId);
            return deal.coins_silver();
        } else if (identifier.startsWith("dealgold_")) {
            int dealId = Integer.parseInt(identifier.replace("dealgold_", ""));
            Deal deal = databaseDeals.getDeal(dealId);
            return deal.coins_gold();
        } else if (identifier.startsWith("dealmaterials_")) {
            int dealId = Integer.parseInt(identifier.replace("dealmaterials_", ""));
            Deal deal = databaseDeals.getDeal(dealId);
            return deal.materials();
        } else if (identifier.startsWith("dealstatus_")) {
            int dealId = Integer.parseInt(identifier.replace("dealstatus_", ""));
            Deal deal = databaseDeals.getDeal(dealId);
            int dealMaterials = Integer.parseInt(deal.materials());
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
