package wb.plugin.wbutils.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.deals.IDatabaseDeals;
import wb.plugin.wbutils.utilities.ISqlActions;
import wb.plugin.wbutils.utilities.PaydayGrant;

public class Payday implements CommandExecutor {

    private final ISqlActions sqlActions;
    private final IDatabaseDeals databaseDeals;

    public Payday(final ISqlActions sqlActions, final IDatabaseDeals databaseDeals) {
        this.sqlActions = sqlActions;
        this.databaseDeals = databaseDeals;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.GRAY + "的 Ошибка ввода. Пример: /payday <multiplier>");
            return true;
        }
        if (sender instanceof Player player) {
            if (!player.hasPermission("wbutils.payday")) {
                return true;
            }

            new PaydayGrant(sqlActions, databaseDeals);
            System.out.println("[" + sender.getName() + "] [CMD] [PAYDAY] [multiplier:" + args[0] + "]");
        } else {
            new PaydayGrant(sqlActions, databaseDeals);
        }
        return true;
    }
}
