package wb.plugin.wbutils.commands.system;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class DoSpecialAction implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;
        if (args.length != 2) return true;

        switch (args[0]){
            case "oak_ehmyzz2lgq" -> {
                if (!sender.hasPermission("wb.woodobtain." + args[1] )) {
                    String value = PlaceholderAPI.setPlaceholders(player,"%luckperms_expiry_time_wb.woodobtain." + args[1] + "%").replace("s", "");
                    sender.sendMessage(ChatColor.GRAY + "的 Можно будет снова срубить через " + value + " секунд(ы).");
                    return true;
                }
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                Bukkit.dispatchCommand(console, "dm open job_oak " + sender.getName());
                Bukkit.dispatchCommand(console, "lp user " + sender.getName() + " permission settemp wb.woodobtain."+ args[1] +" false 59s");
            }
            case "spruce_yy974jutou" -> {
                if (!sender.hasPermission("wb.woodobtain." + args[1] )) {
                    String value = PlaceholderAPI.setPlaceholders(player,"%luckperms_expiry_time_wb.woodobtain." + args[1] + "%").replace("s", "");
                    sender.sendMessage(ChatColor.GRAY + "的 Можно будет снова срубить через " + value + " секунд(ы).");
                    return true;
                }
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                Bukkit.dispatchCommand(console, "dm open job_spruce " + sender.getName());
                Bukkit.dispatchCommand(console, "lp user " + sender.getName() + " permission settemp wb.woodobtain."+ args[1] +" false 59s");
            }
            case "birch_t899c168nx" -> {
                if (!sender.hasPermission("wb.woodobtain." + args[1] )) {
                    String value = PlaceholderAPI.setPlaceholders(player,"%luckperms_expiry_time_wb.woodobtain." + args[1] + "%").replace("s", "");
                    sender.sendMessage(ChatColor.GRAY + "的 Можно будет снова срубить через " + value + " секунд(ы).");
                    return true;
                }
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                Bukkit.dispatchCommand(console, "dm open job_birch " + sender.getName());
                Bukkit.dispatchCommand(console, "lp user " + sender.getName() + " permission settemp wb.woodobtain."+ args[1] +" false 59s");
            }
            default -> { return true; }
        }
        return true;
    }
}
