package wb.plugin.wbutils;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import wb.plugin.wbutils.commands.ClearChat;
import wb.plugin.wbutils.commands.Payday;
import wb.plugin.wbutils.commands.system.DoSpecialAction2;
import wb.plugin.wbutils.commands.system.mining.MiningActionHandler;
import wb.plugin.wbutils.commands.system.woodcutting.WoodcuttingActionHandler;
import wb.plugin.wbutils.commands.system.PurchasePayment;
import wb.plugin.wbutils.deals.CommandDealInfo;
import wb.plugin.wbutils.deals.CommandSystemDealBuy;
import wb.plugin.wbutils.deals.CommandSystemDealRecount;
import wb.plugin.wbutils.deals.CommandSystemTakeItems;
import wb.plugin.wbutils.deals.DatabaseDeals;
import wb.plugin.wbutils.deals.IDatabaseDeals;
import wb.plugin.wbutils.deals.PlaceholderDealInfo;
import wb.plugin.wbutils.deals.TabCompleterDealInfo;
import wb.plugin.wbutils.utilities.ISqlActions;
import wb.plugin.wbutils.utilities.SqlActions;

import java.sql.SQLException;
import java.util.Objects;

public final class WbUtils extends JavaPlugin implements Listener {

    private ISqlActions sqlActions;
    private IDatabaseDeals databaseDeals;

    @Override
    public void onEnable() {
        databaseDeals = new DatabaseDeals();

        new PlaceholderDealInfo(this, databaseDeals).register();

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
        registerCommand("dealinfo", new CommandDealInfo(databaseDeals));
        registerCommand("dealbuy", new CommandSystemDealBuy(databaseDeals));
        registerCommand("dealrecount", new CommandSystemDealRecount(databaseDeals));
        registerCommand("payday", new Payday(sqlActions, databaseDeals));
        registerCommand("clearchat", new ClearChat());
        registerCommand("dospecialaction", new WoodcuttingActionHandler());
        registerCommand("dospecialaction2", new DoSpecialAction2(this));
        registerCommand("dospecialaction3", new MiningActionHandler());
        registerCommand("dealtakeitems", new CommandSystemTakeItems(databaseDeals));
        registerCommand("purchasepayment", new PurchasePayment());

        registerTabCompleter("dealinfo", new TabCompleterDealInfo(databaseDeals));
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
