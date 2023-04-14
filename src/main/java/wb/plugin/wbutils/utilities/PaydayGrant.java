// Вызов PaydayGrant приведет к проведению пейдея на сервере

package wb.plugin.wbutils.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import wb.plugin.wbutils.deals.DatabaseDeals;

import java.util.HashMap;


public class PaydayGrant {

    private static final HashMap<String, String> warningsToSend = new HashMap<>();; // player(send to) + message(what to send)
    private static final HashMap<Integer, String> dealsToReset = new HashMap<>(); // dealId + Owner



    public PaydayGrant() {
        resetDealOwners();
        new SqlActions().saveDealsInfo();
        sendNotification();
    }



    /**
     * Removes owner from inactive deals.
     */
    public void resetDealOwners() {

        // Обнуляем сделки
        dealsToReset.forEach((key, value) -> {
            DatabaseDeals.setOwner(key, "-");
            DatabaseDeals.setMaterials(key, "16");
            warningsToSend.put(value, ChatColor.LIGHT_PURPLE + "我 Ваша сделка была разорвана.");
        } );

        // Очистка очереди на обнуление
        dealsToReset.clear();

        // Пополнение очереди на обнуление
        for (int i = 1; i <= DatabaseDeals.getDealsQuantity(); i++) {
            if (Integer.parseInt(DatabaseDeals.getMaterials(i)) <= -8) {
                dealsToReset.put(i, DatabaseDeals.getOwner(i));
                warningsToSend.put(DatabaseDeals.getOwner(i), ChatColor.LIGHT_PURPLE + "我 Ваша сделка вот-вот будет разорвана.");
            }
        }
    }


    /**
     * Broadcasts special payday notification to everyone online.
     */
    public void sendNotification() {
        String SPACE_STRING = "";

        for (Player playersOnline : Bukkit.getOnlinePlayers()) {
            playersOnline.sendMessage(SPACE_STRING);
            new TimeSyncReallife(playersOnline);
            playersOnline.sendMessage(ChatColor.YELLOW + "   Спасибо за игру на нашем сервере!");
            playersOnline.sendMessage(SPACE_STRING);

            // Отправлем всем спец сообщения из очереди
            warningsToSend.forEach((key, value) -> {
               Player player = Bukkit.getPlayerExact(key);
               player.sendMessage(value);
               player.playSound(player.getLocation(), "custom.pagania.warning-1", 1, 1);
               player.sendMessage(SPACE_STRING);
            } );

            // Очистка очереди уведомлений
            warningsToSend.clear();
        }
    }
}
