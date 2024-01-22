package wb.plugin.wbutils.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wb.plugin.wbutils.usecases.MiningActionResponse;
import wb.plugin.wbutils.usecases.MiningActionUseCase;
import wb.plugin.wbutils.utilities.SoundDecay;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;

public class MiningActionCommand implements CommandExecutor {

    /**
     * Flood cooldown for mining command.
     */
    private static final ConcurrentMap<UUID, Long> USER_CMD_COOLDOWN = new ConcurrentHashMap<>();
    private static final int COOLDOWN_DURATION_NS = 1_000_000_000;
    private static final String PERMISSION_PREFIX = "wb.oreobtain.";
    private static final String EXPIRY_TIME_FORMAT = "%%luckperms_expiry_time_%s%%";

    private final MiningActionUseCase miningActionUseCase;

    public MiningActionCommand(final MiningActionUseCase miningActionUseCase) {
        this.miningActionUseCase = miningActionUseCase;
    }

    private static boolean hasCooldownElapsed(final UUID playerId) {
        if (USER_CMD_COOLDOWN.containsKey(playerId)) {
            long timeElapsed = System.nanoTime() - USER_CMD_COOLDOWN.get(playerId);
            return timeElapsed >= COOLDOWN_DURATION_NS;
        }
        return true;
    }

    private static void handleResponse(final @NotNull Player player, final @NotNull MiningActionResponse status,
                                       final String permissionBody) {
        final @Nullable String content;
        switch (status) {
            case ON_COOLDOWN -> content = handleOnCooldown(player, permissionBody);
            case NO_PICKAXE -> content = handleNoPickaxe();
            case INVALID_PICKAXE -> content = handleInvalidPickaxe();
            case PITY -> content = handlePity(player);
            case SUCCESS -> content = handleSuccess(player);
            default -> throw new IllegalStateException("Unexpected value: " + status);
        }

        if (content != null) {
            final TextComponent message = Component.text(content, NamedTextColor.GRAY);
            player.sendMessage(message);
        }
    }

    @NotNull
    private static String handleOnCooldown(@NotNull Player player, String permissionBody) {
        final String permission = PERMISSION_PREFIX + permissionBody;
        final String permissionExpiryTime = String.format(EXPIRY_TIME_FORMAT, permission);
        final String time = PlaceholderAPI.setPlaceholders(player, permissionExpiryTime);
        final String seconds = time.replace("s", "");
        return "的 Можно будет снова добыть через " + seconds + " секунд(ы).";
    }

    @NotNull
    private static String handleNoPickaxe() {
        return "的 Возьми в руку кирку или получи её у главного шахтёра.";
    }

    @NotNull
    private static String handleInvalidPickaxe() {
        return "的 Возьми в руку подходящую кирку или получи её у главного шахтёра.";
    }

    @NotNull
    private static String handlePity(@NotNull Player player) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        final String sound = "custom.pagania.pickaxe-dig" + random.nextInt(1, 3);
        playSound(player, sound);
        return "的 Разбить породу не вышло, продолжай копать!";
    }

    @Nullable
    private static String handleSuccess(@NotNull Player player) {
        playSound(player, "custom.pagania.pickaxe-dig3");
        return null;
    }

    private static void playSound(final @NotNull Player player, final @NotNull String sound) {
        final Location playerLocation = player.getLocation();
        final byte range = 18;
        final World world = playerLocation.getWorld();
        final Collection<Entity> nearbyEntities = world.getNearbyEntities(playerLocation, range, range, range);
        for (final Entity target : nearbyEntities) {
            if (target instanceof Player playerToPlay) {
                new SoundDecay(playerToPlay, playerLocation, sound, range);
            }
        }
    }

    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command,
                             final @NotNull String label, final @NotNull String[] args) {
        if (!(sender instanceof Player player) || args.length != 2) {
            return true;
        }
        final UUID playerId = player.getUniqueId();
        if (!hasCooldownElapsed(playerId)) {
            return true;
        }

        USER_CMD_COOLDOWN.put(playerId, System.nanoTime());

        final String oreId = args[0];
        final String permissionBody = args[1];
        final MiningActionResponse response = miningActionUseCase.executeAction(oreId, permissionBody, player);
        handleResponse(player, response, permissionBody);

        return true;
    }
}
