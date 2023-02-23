package wb.plugin.wbutils;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import wb.plugin.wbutils.commands.system.dospecialaction;
import wb.plugin.wbutils.commands.system.dospecialaction2;
import wb.plugin.wbutils.deals.*;
import wb.plugin.wbutils.commands.clearchat;
//import wb.plugin.wbutils.events.JoinQuitEvent;

public final class wbUtils extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {

        new placeholder_dealinfo(this).register();

        //getServer().getPluginManager().registerEvents(new JoinQuitEvent(), this);
        //getCommand("setspawn").setExecutor(new setspawn(this));
        getCommand("dealinfo").setExecutor(new command_dealinfo());
        getCommand("dealinfo").setTabCompleter(new tabcompleter_dealinfo());
        getCommand("dealbuy").setExecutor(new command_sys_dealbuy());
        getCommand("dealrecount").setExecutor(new command_sys_dealrecount());
        getCommand("clearchat").setExecutor(new clearchat());
        getCommand("dospecialaction").setExecutor(new dospecialaction());
        getCommand("dospecialaction2").setExecutor(new dospecialaction2(this));


        file_dealsdata.setup();
        file_dealsdata.get().options().copyDefaults(true);
        file_dealsdata.save();

        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        file_dealsdata.save();
        saveConfig();
    }
}
