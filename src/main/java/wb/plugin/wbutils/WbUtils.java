package wb.plugin.wbutils;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import wb.plugin.wbutils.adapters.commands.ClearChatCommand;
import wb.plugin.wbutils.adapters.commands.PaydayCommand;
import wb.plugin.wbutils.adapters.commands.NpcInteractionsCommand;
import wb.plugin.wbutils.adapters.commands.MiningActionCommand;
import wb.plugin.wbutils.adapters.commands.WoodcuttingActionCommand;
import wb.plugin.wbutils.adapters.commands.PurchasePaymentCommand;
import wb.plugin.wbutils.adapters.commands.DealInfoCommand;
import wb.plugin.wbutils.adapters.commands.SystemDealBuyCommand;
import wb.plugin.wbutils.adapters.commands.SystemDealRecountCommand;
import wb.plugin.wbutils.adapters.commands.SystemTakeItemsCommand;
import wb.plugin.wbutils.adapters.listeners.JoinQuitListener;
import wb.plugin.wbutils.adapters.repositories.DealsRepositoryImpl;
import wb.plugin.wbutils.adapters.repositories.DealsRepository;
import wb.plugin.wbutils.adapters.placeholders.DealInfoPlaceholder;
import wb.plugin.wbutils.adapters.tabcompleters.DealInfoTabCompleter;
import wb.plugin.wbutils.frameworks.DatabaseConnectionManager;
import wb.plugin.wbutils.frameworks.DatabaseConnectionManagerImpl;
import wb.plugin.wbutils.usecases.ClearChatUseCase;
import wb.plugin.wbutils.usecases.DealInfoUseCase;
import wb.plugin.wbutils.usecases.DealManagementUseCase;
import wb.plugin.wbutils.usecases.MiningActionUseCase;
import wb.plugin.wbutils.usecases.NotificationService;
import wb.plugin.wbutils.usecases.PaydayUseCase;

import java.util.Objects;
import java.util.logging.Logger;

public final class WbUtils extends JavaPlugin {

    private static final Logger PLUGIN_LOGGER = Logger.getLogger(WbUtils.class.getName());
    private DatabaseConnectionManager dbConnectionManager;
    private DealsRepository dealsRepository;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new JoinQuitListener(), this);
        dbConnectionManager = new DatabaseConnectionManagerImpl(this);
        dealsRepository = new DealsRepositoryImpl(dbConnectionManager);
        new DealInfoPlaceholder(dealsRepository).register();
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
        registerCommand("dealinfo", new DealInfoCommand(new DealInfoUseCase(dealsRepository)));
        registerCommand("dealbuy", new SystemDealBuyCommand(dealsRepository));
        registerCommand("dealrecount", new SystemDealRecountCommand(dealsRepository));
        registerCommand("payday", new PaydayCommand(new PaydayUseCase(dealsRepository,
                new DealManagementUseCase(dealsRepository), new NotificationService())));
        registerCommand("clearchat", new ClearChatCommand(new ClearChatUseCase()));
        registerCommand("dospecialaction", new WoodcuttingActionCommand());
        registerCommand("dospecialaction2", new NpcInteractionsCommand(this));
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
        if (dbConnectionManager != null) {
            dbConnectionManager.closeAllConnectionsAsync().join();
        }

        /* вырезанный функционал
        FileDealsData.save();
        saveConfig(); */
    }
}
