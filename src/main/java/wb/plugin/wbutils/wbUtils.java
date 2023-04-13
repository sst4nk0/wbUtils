package wb.plugin.wbutils;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import wb.plugin.wbutils.commands.Payday;
import wb.plugin.wbutils.commands.system.DoSpecialAction;
import wb.plugin.wbutils.commands.system.DoSpecialAction2;
import wb.plugin.wbutils.commands.system.DoSpecialAction3;
import wb.plugin.wbutils.commands.system.PurchasePayment;
import wb.plugin.wbutils.deals.*;
import wb.plugin.wbutils.commands.ClearChat;
import wb.plugin.wbutils.utilities.SqlActions;

//import wb.plugin.wbutils.events.JoinQuitEvent;

public final class wbUtils extends JavaPlugin implements Listener {

    private static wbUtils instance;

    @Override
    public void onEnable() {
        instance = this;

        new PlaceholderDealInfo(this).register(); // кулдаун

        //getServer().getPluginManager().registerEvents(new JoinQuitEvent(), this);

        //============ Прогрузка БД ====================================================================================
        SqlActions sqlactions = new SqlActions();
        sqlactions.firstConnection();
        sqlactions.loadDealsInfo();
        //==============================================================================================================


        //============ Комманды ========================================================================================
        //getCommand("setspawn").setExecutor(new setspawn(this));
        getCommand("dealinfo").setExecutor(new CommandDealInfo());
        getCommand("dealinfo").setTabCompleter(new TabCompleterDealInfo());
        getCommand("dealbuy").setExecutor(new CommandSystemDealBuy());
        getCommand("dealrecount").setExecutor(new CommandSystemDealRecount());
        getCommand("payday").setExecutor(new Payday());
        getCommand("clearchat").setExecutor(new ClearChat());
        getCommand("dospecialaction").setExecutor(new DoSpecialAction());
        getCommand("dospecialaction2").setExecutor(new DoSpecialAction2(this));
        getCommand("dospecialaction3").setExecutor(new DoSpecialAction3());
        getCommand("purchasepayment").setExecutor(new PurchasePayment());
        //==============================================================================================================


        //============ Конфиги =========================================================================================
        FileDealsData.setup();
        FileDealsData.get().options().copyDefaults(true);
        FileDealsData.save();

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        //==============================================================================================================
    }

    @Override
    public void onDisable() {
        SqlActions sqlactions = new SqlActions();
        sqlactions.saveDealsInfo();

        FileDealsData.save();
        saveConfig();
    }

    public static wbUtils getInstance() {
        return instance;
    }
}
