package wb.plugin.wbutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.entities.Deal;
import wb.plugin.wbutils.adapters.IDatabaseDeals;

import java.util.HashMap;

public class SystemDealRecountCommand implements CommandExecutor {

    private final HashMap<String, Long> perUserCooldowns;  // кулдауны на флуд
    private final IDatabaseDeals databaseDeals;

    public SystemDealRecountCommand(final IDatabaseDeals databaseDeals) {
        this.perUserCooldowns = new HashMap<>();
        this.databaseDeals = databaseDeals;
    }

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
        if (!sender.hasPermission("wbutils.dealrecount")) { return true; }
        if (args.length < 2) {
            System.out.println("[CONSOLE] [MSG] [Ошибка ввода. Пример: /dealrecount <deal_id> <sender_name>]");
            return true;
        }
        if (perUserCooldowns.containsKey(args[1])) {
            long timeElapsed = System.currentTimeMillis() - perUserCooldowns.get(args[1]);
            if (timeElapsed <= 5000) { return true; }
            else { runProcess(args); }
            return true;
        }

        runProcess(args);
        return true;
    }

    private void runProcess(@NotNull String @NotNull [] args) {
        perUserCooldowns.put(args[1], System.currentTimeMillis());

        String dealIdString = args[0];
        int dealId = Integer.parseInt(dealIdString);
        Deal deal = databaseDeals.getDeal(dealId);
        int coins_copper = Integer.parseInt(deal.coins_copper());
        int coins_silver = Integer.parseInt(deal.coins_silver());
        int coins_gold = Integer.parseInt(deal.coins_gold());
        boolean changed = false;

        final int maxStackSize = 64;

        if (coins_copper >= maxStackSize) {
            while (coins_copper >= maxStackSize) {
                coins_copper -= maxStackSize;
                coins_silver++;
            }
            changed = true;
        }

        if (coins_silver >= maxStackSize) {
            while (coins_silver >= maxStackSize) {
                coins_silver -= maxStackSize;
                coins_gold++;
            }
            changed = true;
        }

        if (changed) {
            Player player = Bukkit.getPlayerExact(args[1]);
            Deal newDeal = new Deal(deal.id(), deal.owner(), Integer.toString(coins_copper), Integer.toString(coins_silver), Integer.toString(coins_gold), deal.materials());
            databaseDeals.setDeal(dealId, newDeal);
            player.sendMessage(ChatColor.DARK_GREEN + "Кладовщик: " + ChatColor.GREEN + "Всё, пересчитал. Надо будет отдохнуть сегодня за кружечкой светлого нефильтрованного.");
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
        }
    }

}
