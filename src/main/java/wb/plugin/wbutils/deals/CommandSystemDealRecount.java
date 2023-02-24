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

        int coinsCopper = Integer.parseInt(Objects.requireNonNull(FileDealsData.get().getString(args[0] + ".coinsCopper")));
        int coinsSilver = Integer.parseInt(Objects.requireNonNull(FileDealsData.get().getString(args[0] + ".coinsSilver")));
        int coinsGold = Integer.parseInt(Objects.requireNonNull(FileDealsData.get().getString(args[0] + ".coinsGold")));
        boolean changed = false;

        final int maxStackSize = 64;

        if (coinsCopper >= maxStackSize) {
            while (coinsCopper >= maxStackSize) {
                coinsCopper -= maxStackSize;
                coinsSilver++;
            }
            changed = true;
        }

        if (coinsSilver >= maxStackSize) {
            while (coinsSilver >= maxStackSize) {
                coinsSilver -= maxStackSize;
                coinsGold++;
            }
            changed = true;
        }

        if (changed) {
            FileDealsData.get().set(args[0] + ".coinsCopper", coinsCopper);
            FileDealsData.get().set(args[0] + ".coinsSilver", coinsSilver);
            FileDealsData.get().set(args[0] + ".coinsGold", coinsGold);
        }

        return true;
    }

}
