package wb.plugin.wbutils.deals;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class CommandSystemDealRecount implements CommandExecutor {

    private final HashMap<String, Long> perUserCooldowns;  // кулдауны на флуд
    private final IDatabaseDeals databaseDeals;

    public CommandSystemDealRecount(final IDatabaseDeals databaseDeals) {
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

        int coins_copper = Integer.parseInt(databaseDeals.getCoinsCopper(Integer.parseInt(args[0])));
        int coins_silver = Integer.parseInt(databaseDeals.getCoinsSilver(Integer.parseInt(args[0])));
        int coins_gold = Integer.parseInt(databaseDeals.getCoinsGold(Integer.parseInt(args[0])));
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
            databaseDeals.setCoinsCopper(Integer.parseInt(args[0]), Integer.toString(coins_copper));
            databaseDeals.setCoinsSilver(Integer.parseInt(args[0]), Integer.toString(coins_silver));
            databaseDeals.setCoinsGold(Integer.parseInt(args[0]), Integer.toString(coins_gold));
            player.sendMessage(ChatColor.DARK_GREEN + "Кладовщик: " + ChatColor.GREEN + "Всё, пересчитал. Надо будет отдохнуть сегодня за кружечкой светлого нефильтрованного.");
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
        }
    }

}
