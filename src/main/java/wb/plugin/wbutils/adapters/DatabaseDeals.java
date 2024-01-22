package wb.plugin.wbutils.adapters;

import wb.plugin.wbutils.entities.Deal;

import java.util.ArrayList;
import java.util.List;

public class DatabaseDeals implements IDatabaseDeals {

    private static final int DEALS_QUANTITY = 25;
    private final List<Deal> deals = new ArrayList<>(DEALS_QUANTITY);

    public void addDeal(final Deal deal) {
        deals.add(deal);
    }

    public Deal getDeal(final int dealId) {
        return deals.get(dealId - 1);
    }

    public void setDeal(final int dealId, final Deal deal) {
        deals.set(dealId - 1, deal);
    }

    public int getDealsQuantity() {
        return DEALS_QUANTITY;
    }
}
