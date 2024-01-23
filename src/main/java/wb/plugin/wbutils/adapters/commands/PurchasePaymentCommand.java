package wb.plugin.wbutils.adapters.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PurchasePaymentCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) return true;
        Player targetPlayer = Bukkit.getServer().getPlayerExact(args[0]);
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        switch (args[2]) {
            case "copper" -> {
                int copperCoinsAmount = Integer.parseInt(PlaceholderAPI.setPlaceholders(targetPlayer, "%itemedit_amount_{currency_copper_coin}_{inventory}%"));
                if (copperCoinsAmount == 0 | copperCoinsAmount < Integer.parseInt(args[1])) {
                    String silverCoinsAmount = PlaceholderAPI.setPlaceholders(targetPlayer, "%itemedit_amount_{currency_silver_coin}_{inventory}%");
                    if (silverCoinsAmount.equals("0")) {
                        String goldCoinsAmount = PlaceholderAPI.setPlaceholders(targetPlayer, "%itemedit_amount_{currency_gold_coin}_{inventory}%");
                        if (goldCoinsAmount.equals("0")) { return true; }
                        Bukkit.dispatchCommand(console, "si take currency_gold_coin 1 " + args[0] + " true");
                        Bukkit.dispatchCommand(console, "si give currency_silver_coin 63 " + args[0] + " true");
                        int copperChange = 64 - Integer.parseInt(args[1]);
                        Bukkit.dispatchCommand(console, "si give currency_copper_coin " + copperChange + " " + args[0] + " true");
                        return true;
                    }
                    Bukkit.dispatchCommand(console, "si take currency_silver_coin 1 " + args[0] + " true");
                    int copperChange = 64 - Integer.parseInt(args[1]);
                    Bukkit.dispatchCommand(console, "si give currency_copper_coin " + copperChange + " " + args[0] + " true");
                    return true;
                }
                Bukkit.dispatchCommand(console, "si take currency_copper_coin " + args[1] + " " + args[0] + " true");
                return true;
            }
            case "silver" -> {
                int silverCoinsAmount = Integer.parseInt(PlaceholderAPI.setPlaceholders(targetPlayer, "%itemedit_amount_{currency_silver_coin}_{inventory}%"));
                if (silverCoinsAmount == 0 | silverCoinsAmount < Integer.parseInt(args[1])) {
                    String goldCoinsAmount = PlaceholderAPI.setPlaceholders(targetPlayer, "%itemedit_amount_{currency_gold_coin}_{inventory}%");
                    if (goldCoinsAmount.equals("0")) { return true; }
                    Bukkit.dispatchCommand(console, "si take currency_gold_coin 1 " + args[0] + " true");
                    int silverChange = 64 - Integer.parseInt(args[1]);
                    Bukkit.dispatchCommand(console, "si give currency_silver_coin " + silverChange + " " + args[0] + " true");
                    return true;
                }
                Bukkit.dispatchCommand(console, "si take currency_silver_coin " + args[1] + " " + args[0] + " true");
                return true;
            }
            case "gold" -> {
                int goldCoinsAmount = Integer.parseInt(PlaceholderAPI.setPlaceholders(targetPlayer, "%itemedit_amount_{currency_gold_coin}_{inventory}%"));
                if (goldCoinsAmount < Integer.parseInt(args[1])) { return true; }
                Bukkit.dispatchCommand(console, "si take currency_gold_coin " + args[1] + " " + args[0] + " true");
                return true;
            }
            default -> { return true; }
        }
    }
}
