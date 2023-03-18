package wb.plugin.wbutils;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import wb.plugin.wbutils.commands.system.DoSpecialAction;
import wb.plugin.wbutils.commands.system.DoSpecialAction2;
import wb.plugin.wbutils.commands.system.DoSpecialAction3;
import wb.plugin.wbutils.commands.system.PurchasePayment;
import wb.plugin.wbutils.deals.*;
import wb.plugin.wbutils.commands.ClearChat;
//import wb.plugin.wbutils.events.JoinQuitEvent;

public final class wbUtils extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {

        new PlaceholderDealInfo(this).register();

        //getServer().getPluginManager().registerEvents(new JoinQuitEvent(), this);
        //getCommand("setspawn").setExecutor(new setspawn(this));
        getCommand("dealinfo").setExecutor(new CommandDealInfo());
        getCommand("dealinfo").setTabCompleter(new TabCompleterDealInfo());
        getCommand("dealbuy").setExecutor(new CommandSystemDealBuy());
        getCommand("dealrecount").setExecutor(new CommandSystemDealRecount());
        getCommand("clearchat").setExecutor(new ClearChat());
        getCommand("dospecialaction").setExecutor(new DoSpecialAction());
        getCommand("dospecialaction2").setExecutor(new DoSpecialAction2(this));
        getCommand("dospecialaction").setExecutor(new DoSpecialAction3());
        getCommand("purchasepayment").setExecutor(new PurchasePayment());


        FileDealsData.setup();
        FileDealsData.get().options().copyDefaults(true);
        FileDealsData.save();

        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        FileDealsData.save();
        saveConfig();
    }
}
