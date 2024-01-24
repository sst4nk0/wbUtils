package wb.plugin.wbutils.adapters.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.adapters.repositories.DealsRepository;
import wb.plugin.wbutils.entities.Deal;

public class SystemTakeItemsCommand implements CommandExecutor {

    private final DealsRepository databaseDeals;

    public SystemTakeItemsCommand(final DealsRepository databaseDeals) {
        super();
        this.databaseDeals = databaseDeals;
    }

    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command,
                             final @NotNull String label, final @NotNull String[] args) {
        if (sender instanceof Player) {
            return true;
        }

        final String placeholder = "%itemedit_amount_{#}_{offhand}%";
        for (int i = 2; i < args.length; i++) {
            final String placeholderToParse = placeholder.replace("#", args[i]);
            final Player player = Bukkit.getPlayer(args[0]);

            final int amountInHand = Integer.parseInt(PlaceholderAPI.setPlaceholders(player, placeholderToParse));
            if (amountInHand <= 0) {
                continue;
            }

            player.getInventory().setItemInOffHand(null);
            final int dealId = Integer.parseInt(args[1]);
            final Deal deal = databaseDeals.getDeal(dealId);
            final int newQuantity = Integer.parseInt(deal.getMaterials()) + amountInHand;
            final Deal newDeal = new Deal(deal.getId(), deal.getOwner(), deal.getCopperCoins(), deal.getSilverCoins(),
                    deal.getGoldCoins(), Integer.toString(newQuantity));
            databaseDeals.setDeal(dealId, newDeal);
            return true;
        }
        return true;
    }
}
