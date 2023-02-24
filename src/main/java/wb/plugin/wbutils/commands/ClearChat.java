package wb.plugin.wbutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClearChat implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission("wbutils.clearchat")){
                for (Player playersOnline : Bukkit.getOnlinePlayers()) {
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(" ");
                    playersOnline.sendMessage(ChatColor.RED + "我 Администратор " + player.getDisplayName() + " очистил чат!");
                }
            }
        } else {
            System.out.println("[CONSOLEADMIN] Chat has been cleared from the console.");
            for (Player playersOnline : Bukkit.getOnlinePlayers()) {
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(" ");
                playersOnline.sendMessage(ChatColor.RED + "我" + " Технический администратор очистил чат!");
            }
        }
        return true;
    }
}
