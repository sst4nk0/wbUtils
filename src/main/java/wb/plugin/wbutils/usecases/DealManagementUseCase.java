package wb.plugin.wbutils.usecases;

import wb.plugin.wbutils.adapters.repositories.DealsRepository;
import wb.plugin.wbutils.entities.Deal;

import java.util.HashSet;
import java.util.Set;

public class DealManagementUseCase {

    private final DealsRepository databaseDeals;

    public DealManagementUseCase(final DealsRepository databaseDeals) {
        this.databaseDeals = databaseDeals;
    }

    public Set<Deal> resetDealOwners() {
        Set<Deal> resetDeals = new HashSet<>();

        for (int dealId = 1; dealId <= databaseDeals.getDealsQuantity(); dealId++) {
            Deal deal = databaseDeals.getDeal(dealId);
            if (Integer.parseInt(deal.getMaterials()) < -7) {
                Deal newDeal = new Deal(deal.getId(), "-", deal.getCopperCoins(), deal.getSilverCoins(), deal.getGoldCoins(), "16");
                databaseDeals.setDeal(dealId, newDeal);
                resetDeals.add(newDeal);
            }
        }

        return resetDeals;
    }

    public Set<Deal> prepareDealNotifications() {
        Set<Deal> endangeredDeals = new HashSet<>();

        for (int dealId = 1; dealId <= databaseDeals.getDealsQuantity(); dealId++) {
            Deal deal = databaseDeals.getDeal(dealId);
            if (Integer.parseInt(deal.getMaterials()) < -7) {
                endangeredDeals.add(deal);
            }
        }

        return endangeredDeals;
    }
}
