package wb.plugin.wbutils.adapters.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.adapters.repositories.DealsRepository;
import wb.plugin.wbutils.entities.Deal;

import java.util.logging.Logger;

public class SystemDealBuyCommand implements CommandExecutor {

    private static final Logger LOGGER = Logger.getLogger(SystemDealBuyCommand.class.getName());
    private final DealsRepository databaseDeals;

    public SystemDealBuyCommand(final DealsRepository databaseDeals) {
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
        final String dealIdString = args[0];
        final int dealId = Integer.parseInt(dealIdString);
        final String owner = args[1];
        final Deal deal = new Deal(dealId, owner, zeroStat, zeroStat, zeroStat, "16");
        databaseDeals.setDeal(dealId, deal);

        final Player target = Bukkit.getServer().getPlayerExact(owner);
        LOGGER.info(() -> "[CONSOLE] [DEAL] [BUY] [number:" + dealId + "] [to:" + owner + "] [from:" + deal.owner() + ']');
        if (target != null) {
            final String content = "我 Сделка №" + dealIdString + " теперь ваша!";
            target.sendMessage(Component.text(content, NamedTextColor.YELLOW));
        }

        return true;
    }
}
