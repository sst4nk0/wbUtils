package wb.plugin.wbutils.adapters;

import wb.plugin.wbutils.entities.Deal;

import java.util.ArrayList;
import java.util.List;

public class DatabaseDeals implements IDatabaseDeals {

    private final int DEALS_QUANTITY = 25;
    private final List<Deal> deals = new ArrayList<>(DEALS_QUANTITY);

    public void addDeal(Deal deal) {
        deals.add(deal);
    }

    public int getDealsQuantity() {
        return DEALS_QUANTITY;
    }

    public Deal getDeal(int dealId) {
        return deals.get(dealId - 1);
    }

    public void setDeal(int dealId, Deal deal) {
        deals.set(dealId - 1, deal);
    }

    public String getMaterials(int dealId) {
        return deals.get(dealId - 1).materials();
    }
}
