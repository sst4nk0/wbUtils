package wb.plugin.wbutils.usecases;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.entities.OreType;
import wb.plugin.wbutils.entities.PickaxeType;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class MiningActionUseCase {

    private static final String PERMISSION_PREFIX = "wb.oreobtain.";
    private static final String LP_COMMAND_FORMAT = "lp user %s permission settemp %s false %ds";
    private static final String SI_GIVE_FORMAT = "si give resource_%s %s %s";
    private static final String GIVE_MINING_EXP_FORMAT = "rpg admin exp give %s mining %s";
    private static final byte PERMISSION_EXPIRY_TIME = 59;

    private static boolean isOnCooldown(final @NotNull Player player, final @NotNull String permissionBody) {
        final String permission = PERMISSION_PREFIX + permissionBody;
        return !player.hasPermission(permission);
    }

    private static boolean hasPickaxeInHand(final Player player) {
        return PlaceholderAPI.setPlaceholders(player, "%checkitem_inhand:main,namecontains:кирка%").equals("yes");
    }

    private static @NotNull Optional<PickaxeType> getPickaxeType(@NotNull Player player) {
        final @NotNull Component pickaxeDisplayName = getPickaxeDisplayName(player);
        final @NotNull String pickaxeName = PlainTextComponentSerializer.plainText().serialize(pickaxeDisplayName);
        System.out.println(pickaxeName);
        final @NotNull String pickaxeTypeName = pickaxeName.substring(0, pickaxeName.length() - 6);
        System.out.println(pickaxeTypeName);
        final @NotNull Optional<PickaxeType> pickaxeType = PickaxeType.fromTypeName(pickaxeTypeName);
        return pickaxeType;
    }

    private static @NotNull Component getPickaxeDisplayName(@NotNull Player player) {
        final ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
        if (itemMeta.hasDisplayName()) {
            return Objects.requireNonNull(itemMeta.displayName(), "Display name is null (unexpected).");
        }
        throw new IllegalStateException("Unexpected state.");
    }

    private static boolean isPickaxeStrongEnough(final PickaxeType pickaxeType, final OreType oreType) {
        final byte pickaxeTypeMiningStrength = pickaxeType.getMiningStrength();
        final byte oreMiningStrength = oreType.getMiningStrength();
        return pickaxeTypeMiningStrength >= oreMiningStrength;
    }

    private static boolean isLucky(final PickaxeType pickaxeType) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        final int generatedNumber = random.nextInt(1, 101);
        final byte luckPercent = pickaxeType.getLuckPercent();
        return luckPercent >= generatedNumber;
    }

    private static void mineOre(final @NotNull Player player, final @NotNull PickaxeType pickaxeType,
                                final @NotNull OreType oreType, final String permissionBody) {
        player.swingMainHand();

        final @NotNull String playerName = player.getName();
        final @NotNull ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

        setCooldownPermission(playerName, permissionBody, console);
        giveMinedResource(playerName, pickaxeType, oreType, console);
        giveMiningExp(playerName, oreType, console);
    }

    private static void setCooldownPermission(@NotNull String playerName, String permissionBody,
                                              @NotNull ConsoleCommandSender console) {
        final String permission = PERMISSION_PREFIX + permissionBody;
        final String lpCommand = String.format(LP_COMMAND_FORMAT, playerName, permission, PERMISSION_EXPIRY_TIME);
        Bukkit.dispatchCommand(console, lpCommand);
    }

    private static void giveMinedResource(@NotNull String playerName, @NotNull PickaxeType pickaxeType,
                                          @NotNull OreType oreType, @NotNull ConsoleCommandSender console) {
        final byte mineQuantity = pickaxeType.getMineQuantity();
        final @NotNull String oreName = oreType.getOreName();
        final String siGiveCommand = String.format(SI_GIVE_FORMAT, oreName, mineQuantity, playerName);
        Bukkit.dispatchCommand(console, siGiveCommand);
    }

    private static void giveMiningExp(final @NotNull String playerName, final @NotNull OreType oreType,
                                      final @NotNull ConsoleCommandSender console) {
        final byte oreTypeExp = oreType.getExp();
        final @NotNull String giveExpCommand = String.format(GIVE_MINING_EXP_FORMAT, playerName, oreTypeExp);
        Bukkit.dispatchCommand(console, giveExpCommand);
    }

    public @NotNull MiningActionResponse executeAction(final String oreId, final String permissionBody,
                                                       @NotNull final Player player) {
        if (isOnCooldown(player, permissionBody)) {
            return MiningActionResponse.ON_COOLDOWN;
        } else if (!hasPickaxeInHand(player)) {
            return MiningActionResponse.NO_PICKAXE;
        }

        final Optional<PickaxeType> pickaxeTypeOptional = getPickaxeType(player);
        if (pickaxeTypeOptional.isEmpty()) {
            return MiningActionResponse.INVALID_PICKAXE;
        }

        final @NotNull PickaxeType pickaxeType = pickaxeTypeOptional.get();
        final @NotNull OreType oreType = OreType.fromOreId(oreId).orElseThrow(IllegalStateException::new);

        if (!isPickaxeStrongEnough(pickaxeType, oreType)) {
            return MiningActionResponse.INVALID_PICKAXE;
        } else if (!isLucky(pickaxeType)) {
            return MiningActionResponse.PITY;
        }

        mineOre(player, pickaxeType, oreType, permissionBody);
        return MiningActionResponse.SUCCESS;
    }
}
