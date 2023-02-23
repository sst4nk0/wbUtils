package wb.plugin.wbutils.deals;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class command_sys_dealbuy implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) return true;
        Player target = Bukkit.getServer().getPlayerExact(args[1]);
        System.out.println("[CONSOLE] [DEAL] [BUY] [number:" + args[0] + "] [to:" + args[1] + "] [from:" + file_dealsdata.get().getString(args[0]+".owner") + "]");
        if (target != null) target.sendMessage(ChatColor.YELLOW + "我 Сделка №" + args[0] + " теперь ваша!");
        file_dealsdata.get().set(args[0]+".owner", args[1]);
        file_dealsdata.get().set(args[0]+".coins_gold", 0);
        file_dealsdata.get().set(args[0]+".coins_silver", 0);
        file_dealsdata.get().set(args[0]+".coins_copper", 0);
        file_dealsdata.get().set(args[0]+".materials", 4);
        return true;
    }
}
