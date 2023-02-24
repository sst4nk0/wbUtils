package wb.plugin.wbutils.deals;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.wbUtils;

public class PlaceholderDealInfo extends PlaceholderExpansion {

    private final wbUtils plugin;

    /**
     * Initialize a constructor.
     * @param inputPlugin inputPlugin
     */
    public PlaceholderDealInfo(final wbUtils inputPlugin) {
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
            String dealId = identifier.replace("dealowner_", "");
            return FileDealsData.get().getString(dealId + ".owner");
        } else if (identifier.startsWith("dealcopper_")) {
            String dealId = identifier.replace("dealcopper_", "");
            return FileDealsData.get().getString(dealId + ".coins_copper");
        } else if (identifier.startsWith("dealsilver_")) {
            String dealId = identifier.replace("dealsilver_", "");
            return FileDealsData.get().getString(dealId + ".coins_silver");
        } else if (identifier.startsWith("dealgold_")) {
            String dealId = identifier.replace("dealgold_", "");
            return FileDealsData.get().getString(dealId + ".coins_gold");
        } else if (identifier.startsWith("dealmaterials_")) {
            String dealId = identifier.replace("dealmaterials_", "");
            return FileDealsData.get().getString(dealId + ".materials");
        } else {
            return null;
        }
    }

}
