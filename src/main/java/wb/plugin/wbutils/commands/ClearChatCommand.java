package wb.plugin.wbutils.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class ClearChatCommand implements CommandExecutor {

    private static final Logger LOGGER = Logger.getLogger(ClearChatCommand.class.getName());

    private static void sendEmptyMessages(final byte x, final Player player) {
        Collections.nCopies(x, Component.empty()).forEach(player::sendMessage);
    }

    private static void sendMessageToAllPlayers(final Collection<? extends Player> players,
                                                final String prefix, final Component name, final byte amount) {
        for (final Player player : players) {
            sendEmptyMessages(amount, player);
            final TextComponent message = Component.text(prefix)
                    .append(name)
                    .append(Component.text(" очистил чат!"))
                    .color(NamedTextColor.RED);
            player.sendMessage(message);
        }
    }

    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command,
                             final @NotNull String label, final @NotNull String[] args) {
        List<? extends Player> onlinePlayers = List.copyOf(Bukkit.getOnlinePlayers());

        if (sender instanceof Player player) {
            if (!sender.hasPermission("wbutils.clearchat")) {
                return true;
            } else if (args.length > 0) {
                return false;
            }

            sendMessageToAllPlayers(onlinePlayers, "我 Администратор ", player.displayName(), (byte) 30);
        } else {
            sendMessageToAllPlayers(onlinePlayers, "我 Технический администратор", Component.empty(), (byte) 25);
            LOGGER.info("[CONSOLEADMIN] Chat has been cleared from the console.");
        }
        return true;
    }
}
