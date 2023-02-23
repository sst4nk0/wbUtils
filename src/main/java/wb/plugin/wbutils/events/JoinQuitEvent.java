package wb.plugin.wbutils.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Random;


public class JoinQuitEvent implements Listener {
    Random rand = new Random();

    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent jevent){
        if(jevent.getPlayer().hasPlayedBefore()){
            switch (rand.nextInt(0, 7)) {
                case 0 -> jevent.setJoinMessage("加 " + ChatColor.YELLOW + jevent.getPlayer().getDisplayName() + " почтил нас своим присутствием.");
                case 1 -> jevent.setJoinMessage("加 " + ChatColor.YELLOW + jevent.getPlayer().getDisplayName() + " решил заглянуть к нам.");
                case 2 -> jevent.setJoinMessage("加 " + ChatColor.YELLOW + jevent.getPlayer().getDisplayName() + " в деле, всем молчать.");
                case 3 -> jevent.setJoinMessage("加 " + ChatColor.YELLOW + jevent.getPlayer().getDisplayName() + " среди нас.");
                case 4 -> jevent.setJoinMessage("加 " + ChatColor.YELLOW + jevent.getPlayer().getDisplayName() + " врывается в игру!");
                case 5 -> jevent.setJoinMessage("加 " + ChatColor.YELLOW + jevent.getPlayer().getDisplayName() + " незаметно проник к нам...");
                case 6 -> jevent.setJoinMessage("加 " + ChatColor.YELLOW + jevent.getPlayer().getDisplayName() + " хочет пообщаться!");
            }
        }
        else{
            jevent.setJoinMessage("加 " + ChatColor.YELLOW + jevent.getPlayer().getDisplayName() + " впервые тут. Приветствуем!");
        }
    }
    @EventHandler
    public void onPlayerLeave(@NotNull PlayerQuitEvent qevent){
        switch (rand.nextInt(0, 7)) {
            case 0 -> qevent.setQuitMessage("放 " + ChatColor.YELLOW + qevent.getPlayer().getDisplayName() + " устал от нашей компании.");
            case 1 -> qevent.setQuitMessage("放 " + ChatColor.YELLOW + qevent.getPlayer().getDisplayName() + " решил сделать перерыв.");
            case 2 -> qevent.setQuitMessage("放 " + ChatColor.YELLOW + qevent.getPlayer().getDisplayName() + " засиделся.");
            case 3 -> qevent.setQuitMessage("放 " + ChatColor.YELLOW + qevent.getPlayer().getDisplayName() + " обрадовал нас своим уходом!");
            case 4 -> qevent.setQuitMessage("放 " + ChatColor.YELLOW + qevent.getPlayer().getDisplayName() + " ушёл по-английски...");
            case 5 -> qevent.setQuitMessage("放 " + ChatColor.YELLOW + qevent.getPlayer().getDisplayName() + " отправился в реальность.");
            case 6 -> qevent.setQuitMessage("放 " + ChatColor.YELLOW + qevent.getPlayer().getDisplayName() + " нашёл кнопку отключения...");
        }
    }
}
