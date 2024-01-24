package wb.plugin.wbutils.adapters.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wb.plugin.wbutils.adapters.listeners.DealUpdateListener;
import wb.plugin.wbutils.adapters.repositories.DealsRepository;
import wb.plugin.wbutils.entities.Deal;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

public class DealInfoPlaceholder extends PlaceholderExpansion implements DealUpdateListener {

    private static final ConcurrentMap<String, Function<Deal, String>> DEAL_PROPERTIES = new ConcurrentHashMap<>();

    static {
        DEAL_PROPERTIES.put("dealowner", Deal::getOwner);
        DEAL_PROPERTIES.put("dealcopper", Deal::getCopperCoins);
        DEAL_PROPERTIES.put("dealsilver", Deal::getSilverCoins);
        DEAL_PROPERTIES.put("dealgold", Deal::getGoldCoins);
        DEAL_PROPERTIES.put("dealmaterials", Deal::getMaterials);
        DEAL_PROPERTIES.put("dealstatus", deal -> Integer.parseInt(deal.getMaterials()) > -1 ? " " : "(сделка неустойчива)");
    }

    private final DealsRepository databaseDeals;
    private final ConcurrentMap<Integer, Deal> dealCache;

    public DealInfoPlaceholder(final DealsRepository databaseDeals) {
        this.databaseDeals = databaseDeals;
        this.dealCache = new ConcurrentHashMap<>();
        this.databaseDeals.addDealUpdateListener(this);
    }

    @Override
    public void onDealUpdated(int dealId, Deal deal) {
        dealCache.put(dealId, deal);
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
        if (!params.startsWith("deal")) {
            return null;
        }

        final String[] identifierSplit = params.split("_");
        final String dealProperty = identifierSplit[0];
        final int dealId = Integer.parseInt(identifierSplit[1]);

        Deal deal = dealCache.get(dealId);
        if (deal == null) {
            deal = databaseDeals.getDeal(dealId);
            if (deal != null) {
                dealCache.put(dealId, deal);
            } else {
                return null;
            }
        }

        final Function<Deal, String> dealFunction = DEAL_PROPERTIES.get(dealProperty);
        if (dealFunction != null) {
            return dealFunction.apply(deal);
        } else {
            throw new IllegalArgumentException("Unknown deal property: " + dealProperty);
        }
    }
}
