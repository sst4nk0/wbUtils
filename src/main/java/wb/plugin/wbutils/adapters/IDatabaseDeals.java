package wb.plugin.wbutils.adapters;

import wb.plugin.wbutils.entities.Deal;

public interface IDatabaseDeals {

    void addDeal(final Deal deal);

    Deal getDeal(final int dealId);

    void setDeal(final int dealId, final Deal deal);

    int getDealsQuantity();
}
