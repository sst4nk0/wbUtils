package wb.plugin.wbutils.usecases;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import wb.plugin.wbutils.entities.Deal;
import wb.plugin.wbutils.utilities.ColorPalette;
import wb.plugin.wbutils.utilities.TimeSyncRealLife;

import java.util.Set;

public class NotificationService {

    public void sendNotifications(Set<Deal> resetDeals, Set<Deal> endangeredDeals) {
        final TextComponent messageToSend = TimeSyncRealLife.getMessageLocalTime(); // Sync every payday
        for (final Player playerOnline : Bukkit.getOnlinePlayers()) {
            playerOnline.sendMessage(Component.empty());
            playerOnline.sendMessage(messageToSend);
            playerOnline.sendMessage(Component.text("   Спасибо за игру на нашем сервере!, ", NamedTextColor.YELLOW));
            playerOnline.sendMessage(Component.empty());
        }

        for (Deal deal : resetDeals) {
            final Player player = Bukkit.getPlayerExact(deal.owner());
            if (player == null) {
                continue;
            }
            player.sendMessage(Component.text("我 Ваша сделка была разорвана.", ColorPalette.JEWELZ_PURPLE));
            player.playSound(player.getLocation(), "custom.pagania.warning-1", 1, 1);
            player.sendMessage(Component.empty());
        }

        for (Deal deal : endangeredDeals) {
            final Player player = Bukkit.getPlayerExact(deal.owner());
            if (player == null) {
                continue;
            }
            player.sendMessage(Component.text("我 Ваша сделка вот-вот будет разорвана.", ColorPalette.JEWELZ_PURPLE));
            player.playSound(player.getLocation(), "custom.pagania.warning-1", 1, 1);
            player.sendMessage(Component.empty());
        }
    }
}
