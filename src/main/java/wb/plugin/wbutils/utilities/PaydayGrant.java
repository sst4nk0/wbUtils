// Вызов PaydayGrant приведет к проведению пейдея на сервере

package wb.plugin.wbutils.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import wb.plugin.wbutils.deals.DatabaseDeals;


public class PaydayGrant extends BukkitRunnable {

    public PaydayGrant() {
        run();
    }

    @Override
    public void run() {
        SqlActions sqlactions = new SqlActions();
        sqlactions.saveDealsInfo();

        for (Player playersOnline : Bukkit.getOnlinePlayers()) {
            playersOnline.sendMessage(" ");
            new TimeSyncReallife(playersOnline);
            playersOnline.sendMessage(ChatColor.YELLOW + "我 Спасибо за игру на нашем сервере!");
            playersOnline.sendMessage(" ");
        }
    }
}
