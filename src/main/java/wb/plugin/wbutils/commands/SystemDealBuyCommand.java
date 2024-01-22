package wb.plugin.wbutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.entities.Deal;
import wb.plugin.wbutils.adapters.IDatabaseDeals;

public class SystemDealBuyCommand implements CommandExecutor {

    private final IDatabaseDeals databaseDeals;

    public SystemDealBuyCommand(final IDatabaseDeals databaseDeals) {
        super();
        this.databaseDeals = databaseDeals;
    }

    /**
     * Do something.
     *
     * @param sender  sender
     * @param command command
     * @param label   label
     * @param args    args
     * @return some boolean
     */
    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command,
                             final @NotNull String label, final @NotNull String[] args) {
        if (sender instanceof Player) {
            return true;
        }

        final String zeroStat = "0";
        String dealIdString = args[0];
        int dealId = Integer.parseInt(dealIdString);
        String owner = args[1];
        Deal deal = new Deal(dealId, owner, zeroStat, zeroStat, zeroStat, "16");
        databaseDeals.setDeal(dealId, deal);

        Player target = Bukkit.getServer().getPlayerExact(owner);
        System.out.println("[CONSOLE] [DEAL] [BUY] [number:" + dealIdString + "] [to:" + owner + "] [from:" + deal.owner() + "]");
        if (target != null) {
            target.sendMessage(ChatColor.YELLOW + "我 Сделка №" + dealIdString + " теперь ваша!");
        }

        return true;
    }

}
