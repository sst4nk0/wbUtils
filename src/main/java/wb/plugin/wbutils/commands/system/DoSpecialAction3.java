package wb.plugin.wbutils.commands.system;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class DoSpecialAction3 implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) { return true; }
        if (args.length != 2) { return true; }

        switch (args[0]) {
            case "copper_dl6mghd049" -> {
                if (!sender.hasPermission("wb.oreobtain." + args[1])) {
                    String value = PlaceholderAPI.setPlaceholders(player, "%luckperms_expiry_time_wb.oreobtain." + args[1] + "%").replace("s", "");
                    sender.sendMessage(ChatColor.GRAY + "的 Можно будет снова добыть через " + value + " секунд(ы).");
                    return true;
                }
                String[] pickaxeInHand = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().substring(2).split(" ");
                if (pickaxeInHand[1].equals("кирка")) {
                    mineOre(args[1], player, pickaxeInHand[0], "copper");
                } else {
                    sender.sendMessage(ChatColor.GRAY + "的 Возьми в руку подходящую кирку или получи её у главного шахтёра.");
                }
            }
            case "iron_gh5zdmgk4l" -> {
                if (!sender.hasPermission("wb.oreobtain." + args[1])) {
                    String value = PlaceholderAPI.setPlaceholders(player, "%luckperms_expiry_time_wb.oreobtain." + args[1] + "%").replace("s", "");
                    sender.sendMessage(ChatColor.GRAY + "的 Можно будет снова добыть через " + value + " секунд(ы).");
                    return true;
                }
                String[] pickaxeInHand = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().substring(2).split(" ");
                if (pickaxeInHand[1].equals("кирка")) {
                    mineOre(args[1], player, pickaxeInHand[0], "iron");
                } else {
                    sender.sendMessage(ChatColor.GRAY + "的 Возьми в руку подходящую кирку или получи её у главного шахтёра.");
                }
            }
            case "gold_p1zfvb6jg7" -> {
                if (!sender.hasPermission("wb.oreobtain." + args[1])) {
                    String value = PlaceholderAPI.setPlaceholders(player, "%luckperms_expiry_time_wb.oreobtain." + args[1] + "%").replace("s", "");
                    sender.sendMessage(ChatColor.GRAY + "的 Можно будет снова добыть через " + value + " секунд(ы).");
                    return true;
                }
                String[] pickaxeInHand = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().substring(2).split(" ");
                if (pickaxeInHand[1].equals("кирка") & (pickaxeInHand[0].equals("Железная") | pickaxeInHand[0].equals("Золотая"))) {
                    mineOre(args[1], player, pickaxeInHand[0], "gold");
                } else {
                    sender.sendMessage(ChatColor.GRAY + "的 Возьми в руку подходящую кирку или получи её у главного шахтёра.");
                }
            }
            case "redspice_6g8bv31kju" -> {
                if (!sender.hasPermission("wb.oreobtain." + args[1])) {
                    String value = PlaceholderAPI.setPlaceholders(player, "%luckperms_expiry_time_wb.oreobtain." + args[1] + "%").replace("s", "");
                    sender.sendMessage(ChatColor.GRAY + "的 Можно будет снова добыть через " + value + " секунд(ы).");
                    return true;
                }
                String[] pickaxeInHand = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().substring(2).split(" ");
                if (pickaxeInHand[1].equals("кирка") & pickaxeInHand[0].equals("Золотая")) {
                    mineOre(args[1], player, pickaxeInHand[0], "redspice");
                } else {
                    sender.sendMessage(ChatColor.GRAY + "的 Возьми в руку подходящую кирку или получи её у главного шахтёра.");
                }
            }
        }
        return true;
    }


    private void mineOre(final String oreNumber, final Player player, final String pickaxeType, final String oreTypeObtained) {
        Random rand = new Random();
        Location playerLocation = player.getLocation();
        int generatedNumber = rand.nextInt(0,100);
        byte luckPercent = 0;
        byte minedQuantity = 1;
        String pickaxeName = pickaxeType.split(" ")[0];
        switch (pickaxeName) {
            case ("Каменная") -> luckPercent = 25;
            case ("Железная") -> luckPercent = 38;
            case ("Золотая") -> { luckPercent = 50; minedQuantity = 2; }
        }
        player.swingMainHand();
        if (generatedNumber < luckPercent) {
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            Bukkit.dispatchCommand(console, "lp user " + player.getName() + " permission settemp wb.oreobtain." +  oreNumber + " false 59s");
            Bukkit.dispatchCommand(console, String.format("si give resource_%s %s %s", oreTypeObtained, minedQuantity, player.getName()));
            playerLocation.getWorld().playSound(playerLocation, "custom.pagania.pickaxe-dig3", 1, 1);
        } else {
            player.sendMessage(ChatColor.GRAY + "的 Разбить породу не вышло, продолжай копать!");
            int soundVariation = rand.nextInt(0,2);
            if (soundVariation == 0) { playerLocation.getWorld().playSound(playerLocation, "custom.pagania.pickaxe-dig1", 1, 1); }
            else { playerLocation.getWorld().playSound(playerLocation, "custom.pagania.pickaxe-dig2", 1, 1); }
        }
    }


}
