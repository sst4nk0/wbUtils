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
import wb.plugin.wbutils.adapters.DatabaseDeals;
import wb.plugin.wbutils.adapters.IDatabaseDeals;
import wb.plugin.wbutils.commands.DealInfoPlaceholder;
import wb.plugin.wbutils.commands.DealInfoTabCompleter;
import wb.plugin.wbutils.adapters.ISqlActions;
import wb.plugin.wbutils.adapters.SqlActions;

import java.sql.SQLException;
import java.util.Objects;

public final class WbUtils extends JavaPlugin implements Listener {

    private ISqlActions sqlActions;
    private IDatabaseDeals databaseDeals;

    @Override
    public void onEnable() {
        databaseDeals = new DatabaseDeals();

        new DealInfoPlaceholder(this, databaseDeals).register();

        initializeDatabase();
        registerCommands();
        loadConfiguration();

        /* вырезанный функционал
        getServer().getPluginManager().registerEvents(new JoinQuitEvent(), this);
        getCommand("setspawn").setExecutor(new setspawn(this));
        FileDealsData.setup();
        FileDealsData.get().options().copyDefaults(true);
        FileDealsData.save(); */
    }

    private void initializeDatabase() {
        sqlActions = new SqlActions(this, databaseDeals);
        sqlActions.initialize();
        sqlActions.loadDealsInfo();
    }

    private void registerCommands() {
        registerCommand("dealinfo", new DealInfoCommand(databaseDeals));
        registerCommand("dealbuy", new SystemDealBuyCommand(databaseDeals));
        registerCommand("dealrecount", new SystemDealRecountCommand(databaseDeals));
        registerCommand("payday", new PaydayCommand(sqlActions, databaseDeals));
        registerCommand("clearchat", new ClearChatCommand());
        registerCommand("dospecialaction", new WoodcuttingActionCommand());
        registerCommand("dospecialaction2", new DoSpecialAction2Command(this));
        registerCommand("dospecialaction3", new MiningActionCommand());
        registerCommand("dealtakeitems", new SystemTakeItemsCommand(databaseDeals));
        registerCommand("purchasepayment", new PurchasePaymentCommand());

        registerTabCompleter("dealinfo", new DealInfoTabCompleter(databaseDeals));
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
        sqlActions.saveDealsInfo();
        try {
            sqlActions.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /* вырезанный функционал
        FileDealsData.save();
        saveConfig(); */
    }
}
