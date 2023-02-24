package wb.plugin.wbutils.deals;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandSystemDealRecount implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) return true;
        if (!sender.hasPermission("wbutils.dealrecount")) return true;
        if (args.length<1){
            System.out.println("[CONSOLE] [msg] [Ошибка ввода. Пример: /dealrecount <deal_id>]");
            return true;
        }

        int coins_copper = Integer.parseInt(FileDealsData.get().getString(args[0]+".coins_copper"));
        int coins_silver = Integer.parseInt(FileDealsData.get().getString(args[0]+".coins_silver"));
        int coins_gold = Integer.parseInt(FileDealsData.get().getString(args[0]+".coins_gold"));
        boolean changed = false;


        if (coins_copper >=64){
            while (coins_copper >= 64){
                coins_copper-=64;
                coins_silver++;
            }
            changed = true;
        }

        if (coins_silver >= 64){
            while (coins_silver >= 64){
                coins_silver -= 64;
                coins_gold++;
            }
            changed = true;
        }

        if (changed){
            FileDealsData.get().set(args[0]+".coins_copper", coins_copper);
            FileDealsData.get().set(args[0]+".coins_silver", coins_silver);
            FileDealsData.get().set(args[0]+".coins_gold", coins_gold);
        }

        return true;
    }
}
