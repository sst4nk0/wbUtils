package wb.plugin.wbutils.deals;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandSystemTakeItems implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) return true;

        String placeholder = "%itemedit_amount_{#}_{offhand}%";

        for (int i = 2; i < args.length; i++){
            String placeholderToParse = placeholder.replace("#", args[i]);
            Player player = Bukkit.getPlayer(args[0]);

            int amountInHand = Integer.parseInt(PlaceholderAPI.setPlaceholders(player, placeholderToParse));

            if (amountInHand > 0) {
                player.getInventory().setItemInOffHand(null);
                int dealId = Integer.parseInt(args[1]);
                int newQuantity = Integer.parseInt(DatabaseDeals.getMaterials(dealId))+amountInHand;
                DatabaseDeals.setMaterials(dealId, newQuantity);
                return true;
            }
        }
        return true;
    }
}
