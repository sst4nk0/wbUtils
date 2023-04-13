package wb.plugin.wbutils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.utilities.PaydayGrant;

public class Payday implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasPermission("wbutils.payday")) { return true; }
            new PaydayGrant();
            return true;
        }else{
            new PaydayGrant();
            return true;
        }
    }
}
