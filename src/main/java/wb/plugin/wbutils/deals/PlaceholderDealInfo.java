package wb.plugin.wbutils.deals;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.WbUtils;

public class PlaceholderDealInfo extends PlaceholderExpansion {

    private final WbUtils plugin;

    /**
     * Initialize a constructor.
     * @param inputPlugin inputPlugin
     */
    public PlaceholderDealInfo(final WbUtils inputPlugin) {
        this.plugin = inputPlugin;
    }

    /**
     * Do something.
     * @return some boolean.
     */
    public boolean persist() { return true; }

    /**
     * Do something.
     * @return some string
     */
    @Override
    public @NotNull String getIdentifier() {
        return "wbutils";
    }

    /**
     * Do something.
     * @return some string
     */
    @Override
    public @NotNull String getAuthor() {
        return "sst4nk0";
    }

    /**
     * Do something.
     * @return some string
     */
    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    /**
     * Do something.
     * @return some boolean
     */
    @Override
    public boolean canRegister() { return true; }

    /**
     * Do something.
     * @param player     player
     * @param identifier identifier
     * @return some String
     */
    @Override
    public String onPlaceholderRequest(final Player player, final String identifier) {
        if (identifier.startsWith("dealowner_")) {
            int dealId = Integer.parseInt(identifier.replace("dealowner_", ""));
            return DatabaseDeals.getOwner(dealId);
        } else if (identifier.startsWith("dealcopper_")) {
            int dealId = Integer.parseInt(identifier.replace("dealcopper_", ""));
            return DatabaseDeals.getCoinsCopper(dealId);
        } else if (identifier.startsWith("dealsilver_")) {
            int dealId = Integer.parseInt(identifier.replace("dealsilver_", ""));
            return DatabaseDeals.getCoinsSilver(dealId);
        } else if (identifier.startsWith("dealgold_")) {
            int dealId = Integer.parseInt(identifier.replace("dealgold_", ""));
            return DatabaseDeals.getCoinsGold(dealId);
        } else if (identifier.startsWith("dealmaterials_")) {
            int dealId = Integer.parseInt(identifier.replace("dealmaterials_", ""));
            return DatabaseDeals.getMaterials(dealId);
        } else if (identifier.startsWith("dealstatus_")) {
            int dealId = Integer.parseInt(identifier.replace("dealstatus_", ""));
            int dealMaterials = Integer.parseInt(DatabaseDeals.getMaterials(dealId));
            if (dealMaterials > -1) { return " "; }
            else { return "(сделка неустойчива)"; }
        } else {
            return null;
        }
    }

}
