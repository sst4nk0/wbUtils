package wb.plugin.wbutils.adapters.listeners;

import wb.plugin.wbutils.entities.Deal;

public interface DealUpdateListener {
    void onDealUpdated(int dealId, Deal deal);
}
