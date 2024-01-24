package wb.plugin.wbutils.adapters.repositories;

import org.jetbrains.annotations.Nullable;
import wb.plugin.wbutils.adapters.listeners.DealUpdateListener;
import wb.plugin.wbutils.entities.Deal;

public interface DealsRepository {

    void addDeal(final Deal deal);

    @Nullable Deal getDeal(final int dealId);

    void setDeal(final int dealId, final Deal deal);

    int getDealsQuantity();

    void addDealUpdateListener(DealUpdateListener listener);
}
