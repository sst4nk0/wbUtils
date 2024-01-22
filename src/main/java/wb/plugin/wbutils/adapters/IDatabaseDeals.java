package wb.plugin.wbutils.adapters;

import wb.plugin.wbutils.entities.Deal;

public interface IDatabaseDeals {

    void setDeal(int dealId, Deal deal);

    Deal getDeal(int dealId);

    int getDealsQuantity();

    void addDeal(Deal deal);
}
