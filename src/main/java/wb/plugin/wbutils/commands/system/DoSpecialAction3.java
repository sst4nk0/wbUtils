// Команда для добычи руд

package wb.plugin.wbutils.commands.system;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.utilities.SoundDecay;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class DoSpecialAction3 implements CommandExecutor {

    private final HashMap<UUID, Long> perUserCooldowns;  // кулдауны на флуд

    public DoSpecialAction3(){
        this.perUserCooldowns = new HashMap<>();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) { return true; }
        if (args.length != 2) { return true; }
        if (perUserCooldowns.containsKey(player.getUniqueId())) {
            long timeElapsed = System.currentTimeMillis() - perUserCooldowns.get(player.getUniqueId());
            if (timeElapsed <= 1000) { return true; }
            else { runProcess(sender, args, player);}
            return true;
        }

        runProcess(sender, args, player);
        return true;
    }


    private boolean runProcess(@NotNull CommandSender sender, @NotNull String @NotNull [] args, Player player) {
            perUserCooldowns.put(player.getUniqueId(), System.currentTimeMillis());
            switch (args[0]) {
                case "copper_dl6mghd049" -> {
                    if (isNotAbleToMine(sender, args, player)) return true;

                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    Bukkit.dispatchCommand(console, String.format("rpg admin exp give %s mining 2", player.getName()));
                    final String pickaxeInHand = getPickaxeType(player);
                    mineOre(args[1], player, pickaxeInHand, "copper");
                }
                case "iron_gh5zdmgk4l" -> {
                    if (isNotAbleToMine(sender, args, player)) return true;

                    final String pickaxeInHand = getPickaxeType(player);
                    if (!pickaxeInHand.equals("Каменная")) { //(pickaxeInHand.equals("Железная") | pickaxeInHand.equals("Золотая"))
                        mineOre(args[1], player, pickaxeInHand, "iron");
                        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                        Bukkit.dispatchCommand(console, String.format("rpg admin exp give %s mining 5", player.getName()));
                    } else {
                        sender.sendMessage(ChatColor.GRAY + "的 Возьми в руку подходящую кирку или получи её у главного шахтёра.");
                    }
                }
                case "gold_p1zfvb6jg7" -> {
                    if (isNotAbleToMine(sender, args, player)) return true;

                    final String pickaxeInHand = getPickaxeType(player);
                    if (!pickaxeInHand.equals("Каменная")) { //(pickaxeInHand.equals("Железная") | pickaxeInHand.equals("Золотая"))
                        mineOre(args[1], player, pickaxeInHand, "gold");
                        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                        Bukkit.dispatchCommand(console, String.format("rpg admin exp give %s mining 10", player.getName()));
                    } else {
                        sender.sendMessage(ChatColor.GRAY + "的 Возьми в руку подходящую кирку или получи её у главного шахтёра.");
                    }
                }
                case "redspice_6g8bv31kju" -> {
                    if (isNotAbleToMine(sender, args, player)) return true;

                    final String pickaxeInHand = getPickaxeType(player);
                    if (pickaxeInHand.equals("Золотая")) {
                        mineOre(args[1], player, pickaxeInHand, "redspice");
                        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                        Bukkit.dispatchCommand(console, String.format("rpg admin exp give %s mining 20", player.getName()));
                    } else {
                        sender.sendMessage(ChatColor.GRAY + "的 Возьми в руку подходящую кирку или получи её у главного шахтёра.");
                    }
                }
            }
            return true;
    }

    private static boolean isNotAbleToMine(@NotNull CommandSender sender, @NotNull String @NotNull [] args, Player player) {
        if (!sender.hasPermission("wb.oreobtain." + args[1])) {
            String value = PlaceholderAPI.setPlaceholders(player, "%luckperms_expiry_time_wb.oreobtain." + args[1] + "%").replace("s", "");
            sender.sendMessage(ChatColor.GRAY + "的 Можно будет снова добыть через " + value + " секунд(ы).");
            return true;
        }
        if (!PlaceholderAPI.setPlaceholders(player, "%checkitem_inhand:main,namecontains:кирка%").equals("yes")) {
            sender.sendMessage(ChatColor.GRAY + "的 Возьми в руку подходящую кирку или получи её у главного шахтёра.");
            return true;
        }
        return false;
    }


    private static String getPickaxeType(Player player) {
        String pickaxeInHand = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
        pickaxeInHand = pickaxeInHand.substring(2,pickaxeInHand.length()-6);
        return pickaxeInHand;
    }


    private void mineOre(final String oreId, final Player player, final String pickaxeType, final String oreTypeObtained) {
        Random rand = new Random();
        //Location playerLocation = player.getLocation();
        int generatedNumber = rand.nextInt(0,100);
        byte luckPercent = 0;
        byte minedQuantity = 1;
        byte range = 18;
        String pickaxeName = pickaxeType.split(" ")[0];
        switch (pickaxeName) {
            case ("Каменная") -> luckPercent = 25;
            case ("Железная") -> luckPercent = 38;
            case ("Золотая") -> { luckPercent = 50; minedQuantity = 2; }
        }
        player.swingMainHand();
        if (generatedNumber < luckPercent) {
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            Bukkit.dispatchCommand(console, "lp user " + player.getName() + " permission settemp wb.oreobtain." +  oreId + " false 59s");
            Bukkit.dispatchCommand(console, String.format("si give resource_%s %s %s", oreTypeObtained, minedQuantity, player.getName()));
            for (Entity target : player.getWorld().getNearbyEntities(player.getLocation(), range, range, range)) {
                if (target instanceof Player playerToPlay) new SoundDecay(playerToPlay, player.getLocation(), "custom.pagania.pickaxe-dig3", range);
            }
        } else {
            player.sendMessage(ChatColor.GRAY + "的 Разбить породу не вышло, продолжай копать!");
            int soundVariation = rand.nextInt(0,2);
            if (soundVariation == 0) {
                for (Entity target : player.getWorld().getNearbyEntities(player.getLocation(), range, range, range)) {
                    if (target instanceof Player playerToPlay) new SoundDecay(playerToPlay, player.getLocation(), "custom.pagania.pickaxe-dig1", range);
                }
            }
            else {
                for (Entity target : player.getWorld().getNearbyEntities(player.getLocation(), range, range, range)) {
                    if (target instanceof Player playerToPlay) new SoundDecay(playerToPlay, player.getLocation(), "custom.pagania.pickaxe-dig2", range);
                }
            }
        }
    }


}
