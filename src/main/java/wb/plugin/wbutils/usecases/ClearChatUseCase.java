package wb.plugin.wbutils.usecases;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;

public class ClearChatUseCase {

    public void clearChatForPlayers(final @NotNull Collection<? extends Player> players, final @NotNull String prefix,
                                    final @NotNull Component name, final byte amount) {
        for (final Player player : players) {
            sendEmptyMessages(amount, player);
            final TextComponent message = Component.text(prefix)
                    .append(name)
                    .append(Component.text(" очистил чат!"))
                    .color(NamedTextColor.RED);
            player.sendMessage(message);
        }
    }

    private void sendEmptyMessages(final byte x, final Player player) {
        Collections.nCopies(x, Component.empty()).forEach(player::sendMessage);
    }
}
