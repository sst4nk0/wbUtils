package wb.plugin.wbutils;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import wb.plugin.wbutils.commands.ClearChatCommand;
import wb.plugin.wbutils.commands.PaydayCommand;
import wb.plugin.wbutils.commands.DoSpecialAction2Command;
import wb.plugin.wbutils.commands.MiningActionCommand;
import wb.plugin.wbutils.commands.WoodcuttingActionCommand;
import wb.plugin.wbutils.commands.PurchasePaymentCommand;
import wb.plugin.wbutils.commands.DealInfoCommand;
import wb.plugin.wbutils.commands.SystemDealBuyCommand;
import wb.plugin.wbutils.commands.SystemDealRecountCommand;
import wb.plugin.wbutils.commands.SystemTakeItemsCommand;
import wb.plugin.wbutils.adapters.DealsRepositoryImpl;
import wb.plugin.wbutils.adapters.DealsRepository;
import wb.plugin.wbutils.adapters.DealInfoPlaceholder;
import wb.plugin.wbutils.commands.DealInfoTabCompleter;
import wb.plugin.wbutils.adapters.database.DatabaseConnectionManager;
import wb.plugin.wbutils.adapters.database.DatabaseConnectionManagerImpl;
import wb.plugin.wbutils.usecases.ClearChatUseCase;
import wb.plugin.wbutils.usecases.DealInfoUseCase;
import wb.plugin.wbutils.usecases.DealManagementUseCase;
import wb.plugin.wbutils.usecases.MiningActionUseCase;
import wb.plugin.wbutils.usecases.NotificationService;
import wb.plugin.wbutils.usecases.PaydayUseCase;

import java.util.Objects;
import java.util.logging.Logger;

public final class WbUtils extends JavaPlugin implements Listener {

    private static final Logger PLUGIN_LOGGER = Logger.getLogger(WbUtils.class.getName());
    private DatabaseConnectionManager dbConnectionManager;
    private DealsRepository dealsRepository;

    @Override
    public void onEnable() {
        dbConnectionManager = new DatabaseConnectionManagerImpl(this);
        dealsRepository = new DealsRepositoryImpl(dbConnectionManager);
        new DealInfoPlaceholder(this, dealsRepository).register();
        registerCommands();
        loadConfiguration();

        /* вырезанный функционал
        getServer().getPluginManager().registerEvents(new JoinQuitEvent(), this);
        getCommand("setspawn").setExecutor(new setspawn(this));
        FileDealsData.setup();
        FileDealsData.get().options().copyDefaults(true);
        FileDealsData.save(); */
    }

    private void registerCommands() {
        registerCommand("dealinfo", new DealInfoCommand(dealsRepository));
        registerCommand("dealbuy", new SystemDealBuyCommand(dealsRepository));
        registerCommand("dealrecount", new SystemDealRecountCommand(dealsRepository));
        registerCommand("payday", new PaydayCommand(new PaydayUseCase(dealsRepository,
                new DealManagementUseCase(dealsRepository), new NotificationService())));
        registerCommand("clearchat", new ClearChatCommand(new ClearChatUseCase()));
        registerCommand("dospecialaction", new WoodcuttingActionCommand());
        registerCommand("dospecialaction2", new DoSpecialAction2Command(this));
        registerCommand("dospecialaction3", new MiningActionCommand(new MiningActionUseCase()));
        registerCommand("dealtakeitems", new SystemTakeItemsCommand(dealsRepository));
        registerCommand("purchasepayment", new PurchasePaymentCommand());

        registerTabCompleter("dealinfo", new DealInfoTabCompleter(new DealInfoUseCase(dealsRepository)));
    }

    private void registerCommand(final String name, final CommandExecutor executor) {
        Objects.requireNonNull(getCommand(name)).setExecutor(executor);
    }

    private void registerTabCompleter(final String name, final TabCompleter executor) {
        Objects.requireNonNull(getCommand(name)).setTabCompleter(executor);
    }

    private void loadConfiguration() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        if (dealsRepository != null) {
            dealsRepository.saveDealsInfo();
        }
        if (dbConnectionManager != null) {
            dbConnectionManager.closeAllConnectionsAsync().join();
        }

        /* вырезанный функционал
        FileDealsData.save();
        saveConfig(); */
    }
}
