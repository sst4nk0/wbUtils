package wb.plugin.wbutils.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.entities.Deal;
import wb.plugin.wbutils.adapters.IDatabaseDeals;

public class SystemTakeItemsCommand implements CommandExecutor {

    private final IDatabaseDeals databaseDeals;

    public SystemTakeItemsCommand(final IDatabaseDeals databaseDeals) {
        super();
        this.databaseDeals = databaseDeals;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) return true;

        String PLACEHOLDER = "%itemedit_amount_{#}_{offhand}%";

        for (int i = 2; i < args.length; i++) {
            String placeholderToParse = PLACEHOLDER.replace("#", args[i]);
            Player player = Bukkit.getPlayer(args[0]);

            int amountInHand = Integer.parseInt(PlaceholderAPI.setPlaceholders(player, placeholderToParse));

            if (amountInHand > 0) {
                player.getInventory().setItemInOffHand(null);
                int dealId = Integer.parseInt(args[1]);
                Deal deal = databaseDeals.getDeal(dealId);
                int newQuantity = Integer.parseInt(deal.materials()) + amountInHand;
                Deal newDeal = new Deal(deal.id(), deal.owner(), deal.coins_copper(), deal.coins_silver(), deal.coins_gold(), Integer.toString(newQuantity));
                databaseDeals.setDeal(dealId, newDeal);
                return true;
            }
        }
        return true;
    }
}
