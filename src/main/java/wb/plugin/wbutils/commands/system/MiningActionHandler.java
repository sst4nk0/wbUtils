package wb.plugin.wbutils.commands.system;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.utilities.SoundDecay;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MiningActionHandler implements CommandExecutor {

    /**
     * Flood cooldown for mining command.
     */
    private static final ConcurrentMap<UUID, Long> USER_COOLDOWN = new ConcurrentHashMap<>();
    private static final String PERMISSION_PREFIX = "wb.oreobtain.";
    private static final String LP_COMMAND_FORMAT = "lp user %s permission settemp %s false %ds";
    private static final String SI_GIVE_FORMAT = "si give resource_%s %s %s";
    private static final String GIVE_MINING_EXP_FORMAT = "rpg admin exp give %s mining %s";
    private static final int COOLDOWN_DURATION_NS = 1_000_000_000;
    private static final byte PERMISSION_EXPIRY_TIME = 59;
    private static final Random random = new Random();

    private static boolean hasCooldownElapsed(final UUID playerId) {
        if (USER_COOLDOWN.containsKey(playerId)) {
            long timeElapsed = System.nanoTime() - USER_COOLDOWN.get(playerId);
            return timeElapsed > COOLDOWN_DURATION_NS;
        }
        return true;
    }

    private static void runProcess(final @NotNull CommandSender sender, final @NotNull String @NotNull [] args,
                                   final Player player) {
        final String oreId = args[0];
        final OreType oreType = OreType.fromOreId(oreId);
        final String permissionBody = args[1];
        if (!isValidOreAndAbleToMine(sender, oreType, permissionBody, player)) {
            return;
        }

        final String pickaxeName = getPickaxeDisplayName(player);
        final PickaxeType pickaxeType = getValidPickaxeType(sender, pickaxeName);
        if (pickaxeType == null || !isPickaxeStrongEnough(sender, pickaxeType, oreType)) {
            return;
        }

        final String oreName = oreType.getOreName();
        mineOre(permissionBody, player, pickaxeType, oreName);
        giveMiningExp(player, oreType);
    }

    private static boolean isValidOreAndAbleToMine(final @NotNull CommandSender sender, final OreType oreType,
                                                   final String permissionBody, final Player player) {
        return oreType != null && isAbleToMine(sender, permissionBody, player);
    }

    private static boolean isAbleToMine(final @NotNull CommandSender sender, final @NotNull String permissionBody,
                                        final Player player) {
        return hasPermission(sender, permissionBody, player) && hasPickaxeInHand(sender, player);
    }

    private static boolean hasPermission(final @NotNull CommandSender sender, final @NotNull String permissionBody,
                                         final Player player) {
        final String permission = PERMISSION_PREFIX + permissionBody;
        if (sender.hasPermission(permission)) {
            return true;
        }

        final String permissionExpiryTime = "%luckperms_expiry_time_" + permission + '%';
        final String time = PlaceholderAPI.setPlaceholders(player, permissionExpiryTime);
        final String seconds = time.replace("s", "");
        final String message = "的 Можно будет снова добыть через " + seconds + " секунд(ы).";
        sender.sendMessage(Component.text(message, NamedTextColor.GRAY));
        return false;
    }

    private static boolean hasPickaxeInHand(final @NotNull CommandSender sender, final Player player) {
        if (!PlaceholderAPI.setPlaceholders(player, "%checkitem_inhand:main,namecontains:кирка%").equals("yes")) {
            final String message = "的 Возьми в руку подходящую кирку или получи её у главного шахтёра.";
            sender.sendMessage(Component.text(message, NamedTextColor.GRAY));
            return false;
        }
        return true;
    }

    private static String getPickaxeDisplayName(final Player player) {
        String pickaxeInHand = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
        return pickaxeInHand.substring(2, pickaxeInHand.length() - 6);
    }

    private static PickaxeType getValidPickaxeType(final @NotNull CommandSender sender, final String pickaxe) {
        final PickaxeType pickaxeType = PickaxeType.fromDisplayName(pickaxe);
        if (pickaxeType == null) {
            final String message = "的 Возьми в руку подходящую кирку или получи её у главного шахтёра.";
            sender.sendMessage(Component.text(message, NamedTextColor.GRAY));
        }
        return pickaxeType;
    }

    private static boolean isPickaxeStrongEnough(final @NotNull CommandSender sender, final PickaxeType pickaxeType,
                                                 final OreType oreType) {
        if (pickaxeType.getMiningStrength() < oreType.getStrength()) {
            final String message = "的 Возьми в руку подходящую кирку или получи её у главного шахтёра.";
            sender.sendMessage(Component.text(message, NamedTextColor.GRAY));
            return false;
        }
        return true;
    }

    private static void mineOre(final String permissionBody, final Player player, final PickaxeType pickaxeType,
                                final String oreName) {
        player.swingMainHand();
        final Location playerLocation = player.getLocation();
        if (isLucky(pickaxeType)) {
            executeCommands(permissionBody, player, pickaxeType, oreName);
            playSound(playerLocation, "custom.pagania.pickaxe-dig3");
        } else {
            final String message = "的 Разбить породу не вышло, продолжай копать!";
            player.sendMessage(Component.text(message, NamedTextColor.GRAY));
            playSound(playerLocation, "custom.pagania.pickaxe-dig" + random.nextInt(1, 3));
        }
    }

    private static boolean isLucky(final PickaxeType pickaxeType) {
        final int generatedNumber = random.nextInt(0, 100);
        final byte luckPercent = pickaxeType.getLuckPercent();
        return generatedNumber < luckPercent;
    }

    private static void executeCommands(final String permissionBody, final Player player,
                                        final PickaxeType pickaxeType, final String oreName) {
        final ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        final String playerName = player.getName();
        final String permission = PERMISSION_PREFIX + permissionBody;

        final String lpCommand = String.format(LP_COMMAND_FORMAT, playerName, permission, PERMISSION_EXPIRY_TIME);
        Bukkit.dispatchCommand(console, lpCommand);

        final String siGiveCommand = String.format(SI_GIVE_FORMAT, oreName, pickaxeType.getMineQuantity(), playerName);
        Bukkit.dispatchCommand(console, siGiveCommand);
    }

    private static void playSound(final Location playerLocation, final String sound) {
        final byte range = 18;
        for (final Entity target : playerLocation.getWorld().getNearbyEntities(playerLocation, range, range, range)) {
            if (target instanceof Player playerToPlay) new SoundDecay(playerToPlay, playerLocation, sound, range);
        }
    }

    private static void giveMiningExp(final Player player, final OreType oreType) {
        final String playerName = player.getName();
        final byte oreTypeExp = oreType.getExp();

        final ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        final String giveExpCommand = String.format(GIVE_MINING_EXP_FORMAT, playerName, oreTypeExp);
        Bukkit.dispatchCommand(console, giveExpCommand);
    }

    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command,
                             final @NotNull String label, final @NotNull String[] args) {
        if (!(sender instanceof Player player) || args.length != 2) {
            return true;
        }

        final UUID playerId = player.getUniqueId();
        if (hasCooldownElapsed(playerId)) {
            runProcess(sender, args, player);
            USER_COOLDOWN.put(playerId, System.nanoTime());
        }

        return true;
    }
}
