// Вызов PaydayGrant приведет к проведению пейдея на сервере

package wb.plugin.wbutils.usecases;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import wb.plugin.wbutils.adapters.IDatabaseDeals;
import wb.plugin.wbutils.adapters.ISqlActions;
import wb.plugin.wbutils.utilities.ColorPalette;
import wb.plugin.wbutils.utilities.TimeSyncReallife;

import java.util.HashMap;


public class PaydayGrant {

    private static final HashMap<String, TextComponent> warningsToSend = new HashMap<>(); // player(send to) + message(what to send)
    private static final HashMap<Integer, String> dealsToReset = new HashMap<>(); // dealId + Owner
    private final IDatabaseDeals databaseDeals;

    public PaydayGrant(final ISqlActions sqlActions, final IDatabaseDeals databaseDeals) {
        this.databaseDeals = databaseDeals;
        resetDealOwners();
        sqlActions.saveDealsInfo();
        sendNotifications();
    }

    /**
     * Removes an owner from inactive deals and prepares a new list of deals to reset after the next execution.
     */
    public void resetDealOwners() {
        // Обнуляение сделки
        final TextColor color = ColorPalette.JEWELZ_PURPLE;
        dealsToReset.forEach((key, value) -> {
            if (Integer.parseInt(databaseDeals.getMaterials(key)) < -7) {
                databaseDeals.setOwner(key, "-");
                databaseDeals.setMaterials(key, "16");
                warningsToSend.put(value, Component.text("我 Ваша сделка была разорвана.", color));
            }
        });

        // Очистка очереди на обнуление
        dealsToReset.clear();

        // Пополнение очереди на обнуление
        for (int i = 1; i <= databaseDeals.getDealsQuantity(); i++) {
            if (Integer.parseInt(databaseDeals.getMaterials(i)) < -7) {
                final String owner = databaseDeals.getOwner(i);
                dealsToReset.put(i, owner);
                warningsToSend.put(owner, Component.text("我 Ваша сделка вот-вот будет разорвана.", color));
            }
        }
    }

    /**
     * Broadcasts special payday notification to everyone online.
     */
    public void sendNotifications() {
        for (final Player playersOnline : Bukkit.getOnlinePlayers()) {
            playersOnline.sendMessage(Component.empty());
            new TimeSyncReallife(playersOnline);
            playersOnline.sendMessage(Component.text("   Спасибо за игру на нашем сервере!, ", NamedTextColor.YELLOW));
            playersOnline.sendMessage(Component.empty());

            // Отправлем всем спец сообщения из очереди
            warningsToSend.forEach((key, value) -> {
                final Player player = Bukkit.getPlayerExact(key);
                player.sendMessage(value);
                player.playSound(player.getLocation(), "custom.pagania.warning-1", 1, 1);
                player.sendMessage(Component.empty());
            });

            // Очистка очереди уведомлений
            warningsToSend.clear();
        }
    }
}
