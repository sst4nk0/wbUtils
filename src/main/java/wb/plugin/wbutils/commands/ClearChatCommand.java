package wb.plugin.wbutils.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import wb.plugin.wbutils.usecases.ClearChatUseCase;

import java.util.List;
import java.util.logging.Logger;

public class ClearChatCommand implements CommandExecutor {

    private static final Logger LOGGER = Logger.getLogger(ClearChatCommand.class.getName());
    private final ClearChatUseCase clearChatUseCase;

    public ClearChatCommand(final ClearChatUseCase clearChatUseCase) {
        this.clearChatUseCase = clearChatUseCase;
    }

    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command,
                             final @NotNull String label, final @NotNull String[] args) {
        final @NotNull List<? extends Player> onlinePlayers = List.copyOf(Bukkit.getOnlinePlayers());

        if (sender instanceof Player player) {
            if (!sender.hasPermission("wbutils.clearchat")) {
                return true;
            } else if (args.length > 0) {
                return false;
            }

            clearChatUseCase.clearChatForPlayers(onlinePlayers, "我 Администратор ", player.displayName(), (byte) 30);
        } else {
            clearChatUseCase.clearChatForPlayers(onlinePlayers,
                    "我 Технический администратор", Component.empty(), (byte) 25);
            LOGGER.info("[CONSOLEADMIN] Chat has been cleared from the console.");
        }
        return true;
    }
}
