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

        String zeroStat = "0";
        DatabaseDeals.setOwner(Integer.parseInt(args[0]), args[1]);
        DatabaseDeals.setCoinsCopper(Integer.parseInt(args[0]), zeroStat);
        DatabaseDeals.setCoinsSilver(Integer.parseInt(args[0]), zeroStat);
        DatabaseDeals.setCoinsGold(Integer.parseInt(args[0]), zeroStat);
        DatabaseDeals.setMaterials(Integer.parseInt(args[0]), "16");

        Player target = Bukkit.getServer().getPlayerExact(args[1]);
        System.out.println("[CONSOLE] [DEAL] [BUY] [number:" + args[0] + "] [to:" + args[1] + "] [from:" + DatabaseDeals.getOwner(Integer.parseInt(args[0])) + "]");
        if (target != null) { target.sendMessage(ChatColor.YELLOW + "我 Сделка №" + args[0] + " теперь ваша!"); }

        return true;
    }

}
