package wb.plugin.wbutils.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.adapters.IDatabaseDeals;
import wb.plugin.wbutils.adapters.ISqlActions;
import wb.plugin.wbutils.usecases.PaydayGrant;

import java.util.logging.Logger;

public class PaydayCommand implements CommandExecutor {

    private static final Logger LOGGER = Logger.getLogger(PaydayCommand.class.getName());
    private final ISqlActions sqlActions;
    private final IDatabaseDeals databaseDeals;

    public PaydayCommand(final ISqlActions sqlActions, final IDatabaseDeals databaseDeals) {
        this.sqlActions = sqlActions;
        this.databaseDeals = databaseDeals;
    }

    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command,
                             final @NotNull String label, final @NotNull String[] args) {
        if (args.length != 1) {
            final String content = "的 Ошибка ввода. Пример: /payday <multiplier>";
            sender.sendMessage(Component.text(content, NamedTextColor.GRAY));
            return false;
        }

        if (sender instanceof Player player) {
            if (!player.hasPermission("wbutils.payday")) {
                return true;
            }

            new PaydayGrant(sqlActions, databaseDeals);
            LOGGER.info(() -> "[" + sender.getName() + "] [CMD] [PAYDAY] [multiplier:" + args[0] + "]");
        } else {
            new PaydayGrant(sqlActions, databaseDeals);
        }
        return true;
    }
}
