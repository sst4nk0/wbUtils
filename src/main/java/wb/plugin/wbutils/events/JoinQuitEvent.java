package wb.plugin.wbutils.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Random;


public class JoinQuitEvent implements Listener {
    private final Random random = new Random();

    /**
     * Do something.
     *
     * @param jEvent parameter
     */
    @EventHandler
    public void onPlayerJoin(final @NotNull PlayerJoinEvent jEvent) {
        if (jEvent.getPlayer().hasPlayedBefore()) {
            switch (random.nextInt(0, 7)) {
                case 0 -> jEvent.setJoinMessage("加 " + ChatColor.YELLOW + jEvent.getPlayer().getDisplayName() + " почтил нас своим присутствием.");
                case 1 -> jEvent.setJoinMessage("加 " + ChatColor.YELLOW + jEvent.getPlayer().getDisplayName() + " решил заглянуть к нам.");
                case 2 -> jEvent.setJoinMessage("加 " + ChatColor.YELLOW + jEvent.getPlayer().getDisplayName() + " в деле, всем молчать.");
                case 3 -> jEvent.setJoinMessage("加 " + ChatColor.YELLOW + jEvent.getPlayer().getDisplayName() + " среди нас.");
                case 4 -> jEvent.setJoinMessage("加 " + ChatColor.YELLOW + jEvent.getPlayer().getDisplayName() + " врывается в игру!");
                case 5 -> jEvent.setJoinMessage("加 " + ChatColor.YELLOW + jEvent.getPlayer().getDisplayName() + " незаметно проник к нам...");
                case 6 -> jEvent.setJoinMessage("加 " + ChatColor.YELLOW + jEvent.getPlayer().getDisplayName() + " хочет пообщаться!");
            }
        } else {
            jEvent.setJoinMessage("加 " + ChatColor.YELLOW + jEvent.getPlayer().getDisplayName() + " впервые тут. Приветствуем!");
        }
    }

    /**
     * Do something.
     *
     * @param qEvent parameter
     */
    @EventHandler
    public void onPlayerLeave(final @NotNull PlayerQuitEvent qEvent) {
        switch (random.nextInt(0, 7)) {
            case 0 -> qEvent.setQuitMessage("放 " + ChatColor.YELLOW + qEvent.getPlayer().getDisplayName() + " устал от нашей компании.");
            case 1 -> qEvent.setQuitMessage("放 " + ChatColor.YELLOW + qEvent.getPlayer().getDisplayName() + " решил сделать перерыв.");
            case 2 -> qEvent.setQuitMessage("放 " + ChatColor.YELLOW + qEvent.getPlayer().getDisplayName() + " засиделся.");
            case 3 -> qEvent.setQuitMessage("放 " + ChatColor.YELLOW + qEvent.getPlayer().getDisplayName() + " обрадовал нас своим уходом!");
            case 4 -> qEvent.setQuitMessage("放 " + ChatColor.YELLOW + qEvent.getPlayer().getDisplayName() + " ушёл по-английски...");
            case 5 -> qEvent.setQuitMessage("放 " + ChatColor.YELLOW + qEvent.getPlayer().getDisplayName() + " отправился в реальность.");
            case 6 -> qEvent.setQuitMessage("放 " + ChatColor.YELLOW + qEvent.getPlayer().getDisplayName() + " нашёл кнопку отключения...");
        }
    }
}
