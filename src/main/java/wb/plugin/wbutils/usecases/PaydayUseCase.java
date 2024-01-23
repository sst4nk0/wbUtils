package wb.plugin.wbutils.usecases;

import wb.plugin.wbutils.adapters.DealsRepository;
import wb.plugin.wbutils.entities.Deal;

import java.util.Set;

public class PaydayUseCase {

    private final DealsRepository databaseDeals;
    private final DealManagementUseCase dealResetService;
    private final NotificationService notificationService;

    public PaydayUseCase(final DealsRepository databaseDeals, final DealManagementUseCase dealResetService,
                         final NotificationService notificationService) {
        this.databaseDeals = databaseDeals;
        this.dealResetService = dealResetService;
        this.notificationService = notificationService;
    }

    public void execute() {
        Set<Deal> resetDeals = dealResetService.resetDealOwners();
        Set<Deal> endangeredDeals = dealResetService.prepareDealNotifications();
        databaseDeals.saveDealsInfo();
        notificationService.sendNotifications(resetDeals, endangeredDeals);
    }
}
