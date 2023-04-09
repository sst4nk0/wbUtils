package wb.plugin.wbutils.deals;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandSystemDealBuy implements CommandExecutor {

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
        if (sender instanceof Player) { return true; }
        Player target = Bukkit.getServer().getPlayerExact(args[1]);
        System.out.println("[CONSOLE] [DEAL] [BUY] [number:" + args[0] + "] [to:" + args[1] + "] [from:" + FileDealsData.get().getString(args[0] + ".owner") + "]");
        if (target != null) { target.sendMessage(ChatColor.YELLOW + "我 Сделка №" + args[0] + " теперь ваша!"); }
        FileDealsData.get().set(args[0] + ".owner", args[1]);
        FileDealsData.get().set(args[0] + ".coins_gold", 0);
        FileDealsData.get().set(args[0] + ".coins_silver", 0);
        FileDealsData.get().set(args[0] + ".coins_copper", 0);
        FileDealsData.get().set(args[0] + ".materials", 16);
        return true;
    }

}
