package wb.plugin.wbutils;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import wb.plugin.wbutils.commands.Payday;
import wb.plugin.wbutils.commands.system.*;
import wb.plugin.wbutils.deals.*;
import wb.plugin.wbutils.commands.ClearChat;
import wb.plugin.wbutils.utilities.SqlActions;

public final class WbUtils extends JavaPlugin implements Listener {

    private static WbUtils instance;

    @Override
    public void onEnable() {
        instance = this;

        new PlaceholderDealInfo(this).register();

        //============ Прогрузка БД ====================================================================================
        SqlActions sqlactions = new SqlActions();
        sqlactions.firstConnection();
        sqlactions.loadDealsInfo();
        //==============================================================================================================


        //============ Комманды ========================================================================================
        getCommand("dealinfo").setExecutor(new CommandDealInfo());
        getCommand("dealinfo").setTabCompleter(new TabCompleterDealInfo());
        getCommand("dealbuy").setExecutor(new CommandSystemDealBuy());
        getCommand("dealrecount").setExecutor(new CommandSystemDealRecount());
        getCommand("payday").setExecutor(new Payday());
        getCommand("clearchat").setExecutor(new ClearChat());
        getCommand("dospecialaction").setExecutor(new DoSpecialAction());
        getCommand("dospecialaction2").setExecutor(new DoSpecialAction2(this));
        getCommand("dospecialaction3").setExecutor(new DoSpecialAction3());
        getCommand("dealtakeitems").setExecutor(new CommandSystemTakeItems());
        getCommand("purchasepayment").setExecutor(new PurchasePayment());
        //==============================================================================================================


        //============ Конфиги =========================================================================================
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        //==============================================================================================================


        /* вырезанный функционал
        getServer().getPluginManager().registerEvents(new JoinQuitEvent(), this);
        getCommand("setspawn").setExecutor(new setspawn(this));
        FileDealsData.setup();
        FileDealsData.get().options().copyDefaults(true);
        FileDealsData.save(); */
    }

    @Override
    public void onDisable() {
        SqlActions sqlactions = new SqlActions();
        sqlactions.saveDealsInfo();


        /* вырезанный функционал
        FileDealsData.save();
        saveConfig(); */
    }

    public static WbUtils getInstance() {
        return instance;
    }
}
