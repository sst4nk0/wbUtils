package wb.plugin.wbutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.utilities.PaydayGrant;

public class ClearChat implements CommandExecutor {

    /**
     * Do something.
     * @param sender  sender
     * @param command command
     * @param label   label
     * @param args    args
     * @return some boolean
     */
    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String label, final @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasPermission("wbutils.clearchat")) { return true; }
            for (Player playersOnline : Bukkit.getOnlinePlayers()) {
                for (byte i = 0; i < 30; i++) {
                    playersOnline.sendMessage(" ");
                }
                playersOnline.sendMessage(ChatColor.RED + "我 Администратор " + player.getDisplayName() + " очистил чат!");
            }
            new PaydayGrant();
        } else {
            System.out.println("[CONSOLEADMIN] Chat has been cleared from the console.");
            for (Player playersOnline : Bukkit.getOnlinePlayers()) {
                for (byte i = 0; i < 25; i++) {
                    playersOnline.sendMessage(" ");
                }
                playersOnline.sendMessage(ChatColor.RED + "我" + " Технический администратор очистил чат!");
            }
        }
        return true;
    }

}
