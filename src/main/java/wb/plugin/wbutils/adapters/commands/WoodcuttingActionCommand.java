package wb.plugin.wbutils.adapters.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.entities.WoodType;

public class WoodcuttingActionCommand implements CommandExecutor {

    private static final String PERMISSION_PREFIX = "wb.woodobtain.";
    private static final String DM_COMMAND_FORMAT = "dm open %s %s";
    private static final String LP_COMMAND_FORMAT = "lp user %s permission settemp %s false %ds";
    private static final byte PERMISSION_EXPIRY_TIME = 59;

    private static void sendErrorMessageToPlayer(final Player player, final String permission) {
        final String permissionExpiryTime = "%luckperms_expiry_time_" + permission + '%';
        final String time = PlaceholderAPI.setPlaceholders(player, permissionExpiryTime);

        final String seconds = time.replace("s", "");
        final String message = "的 Можно будет снова срубить через " + seconds + " секунд(ы).";
        player.sendMessage(Component.text(message, NamedTextColor.GRAY));
    }

    private static void executeCommands(final Player player, final String job, final String permission) {
        final ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        final String playerName = player.getName();

        final String dmCommand = String.format(DM_COMMAND_FORMAT, job, playerName);
        Bukkit.dispatchCommand(console, dmCommand);

        final String lpCommand = String.format(LP_COMMAND_FORMAT, playerName, permission, PERMISSION_EXPIRY_TIME);
        Bukkit.dispatchCommand(console, lpCommand);
    }

    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command,
                             final @NotNull String label, final @NotNull String[] args) {
        if (!(sender instanceof Player player) || args.length != 2) {
            return true;
        }

        final String woodId = args[0];
        final WoodType wood = WoodType.fromWoodId(woodId);
        final String job = wood.getWoodJobName();
        final String permissionBody = args[1];
        handleWoodcuttingCommand(player, permissionBody, job);
        return true;
    }

    private void handleWoodcuttingCommand(final Player player, final String permissionBody, final String job) {
        final String permission = PERMISSION_PREFIX + permissionBody;
        if (player.hasPermission(permission)) {
            executeCommands(player, job, permission);
        } else {
            sendErrorMessageToPlayer(player, permission);
        }
    }
}
