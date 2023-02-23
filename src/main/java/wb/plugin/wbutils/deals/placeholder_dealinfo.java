package wb.plugin.wbutils.deals;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.wbUtils;

public class placeholder_dealinfo extends PlaceholderExpansion {

    private final wbUtils plugin;

    public placeholder_dealinfo(wbUtils plugin) {
        this.plugin = plugin;
    }

    public boolean persist(){
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "wbutils";
    }

    @Override
    public @NotNull String getAuthor() {
        return "sst4nk0";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player p, String identifier) {
        if (identifier.startsWith("dealowner_")) {
            String deal_id = identifier.replace("dealowner_","");
            return file_dealsdata.get().getString(deal_id+".owner");
        }
        if (identifier.startsWith("dealcopper_")) {
            String deal_id = identifier.replace("dealcopper_","");
            return file_dealsdata.get().getString(deal_id+".coins_copper");
        }
        if (identifier.startsWith("dealsilver_")) {
            String deal_id = identifier.replace("dealsilver_","");
            return file_dealsdata.get().getString(deal_id+".coins_silver");
        }
        if (identifier.startsWith("dealgold_")) {
            String deal_id = identifier.replace("dealgold_","");
            return file_dealsdata.get().getString(deal_id+".coins_gold");
        }
        if (identifier.startsWith("dealmaterials_")) {
            String deal_id = identifier.replace("dealowner_","");
            return file_dealsdata.get().getString(deal_id+".materials");
        }
        return null;
    }
}
