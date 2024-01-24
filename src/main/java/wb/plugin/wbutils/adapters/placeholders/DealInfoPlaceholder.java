package wb.plugin.wbutils.adapters.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wb.plugin.wbutils.adapters.repositories.DealsRepository;
import wb.plugin.wbutils.entities.Deal;

public class DealInfoPlaceholder extends PlaceholderExpansion {

    private final DealsRepository databaseDeals;

    public DealInfoPlaceholder(final DealsRepository databaseDeals) {
        this.databaseDeals = databaseDeals;
    }

    @Override
    public boolean persist() {
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
        return "0.2.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @Nullable String onRequest(final OfflinePlayer player, final @NotNull String params) {
        System.out.println("Identifier: " + params);
        if (params.startsWith("deal")) {
            final String[] identifierSplit = params.split("_");
            final String dealProperty = identifierSplit[0];
            final int dealId = Integer.parseInt(identifierSplit[1]);
            final Deal deal = databaseDeals.getDeal(dealId);
            if (deal == null) {
                return null;
            }

            return switch (dealProperty) {
                case "dealowner" -> deal.owner();
                case "dealcopper" -> deal.coins_copper();
                case "dealsilver" -> deal.coins_silver();
                case "dealgold" -> deal.coins_gold();
                case "dealmaterials" -> deal.materials();
                case "dealstatus" -> Integer.parseInt(deal.materials()) > -1 ? " " : "(сделка неустойчива)";
                default -> throw new IllegalStateException("Unexpected params: " + params);
            };
        }
        return null;
    }
}
