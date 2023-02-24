package wb.plugin.wbutils.deals;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CommandSystemDealRecount implements CommandExecutor {

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
        if (args.length < 1) {
            System.out.println("[CONSOLE] [msg] [Ошибка ввода. Пример: /dealrecount <deal_id>]");
            return true;
        }

        int coins_copper = Integer.parseInt(Objects.requireNonNull(FileDealsData.get().getString(args[0] + ".coins_copper")));
        int coins_silver = Integer.parseInt(Objects.requireNonNull(FileDealsData.get().getString(args[0] + ".coins_silver")));
        int coins_gold = Integer.parseInt(Objects.requireNonNull(FileDealsData.get().getString(args[0] + ".coins_gold")));
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
            FileDealsData.get().set(args[0] + ".coins_copper", coins_copper);
            FileDealsData.get().set(args[0] + ".coins_silver", coins_silver);
            FileDealsData.get().set(args[0] + ".coins_gold", coins_gold);
        }

        return true;
    }

}
