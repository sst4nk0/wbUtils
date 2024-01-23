package wb.plugin.wbutils.adapters.repositories;

import wb.plugin.wbutils.entities.Deal;

public interface DealsRepository {

    void addDeal(final Deal deal);

    Deal getDeal(final int dealId);

    void setDeal(final int dealId, final Deal deal);

    int getDealsQuantity();

    void loadDealsInfo();

    void saveDealsInfo();
}
