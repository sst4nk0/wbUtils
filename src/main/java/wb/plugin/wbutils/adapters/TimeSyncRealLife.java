package wb.plugin.wbutils.adapters;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.zone.ZoneRules;

public final class TimeSyncRealLife {

    private static final long FULL_MOON_EPOCH_UTC;
    private static final int SECONDS_IN_DAY;
    private static final double DAYS_IN_MOON_CYCLE = 29.5d;
    private static final double STEP = DAYS_IN_MOON_CYCLE / 8d;
    private static final int MINECRAFT_DAY_LENGTH = 24_000;
    private static final int MINECRAFT_DAY_START_OFFSET = 18_000;
    private static final double HOURS_IN_DAY = 24d;
    private static final double MINUTES_IN_HOUR = 60d;
    private static final double SECONDS_IN_MINUTE = 60d;

    static {
        final LocalDateTime fullMoonDate = LocalDateTime.of(2024, 1, 25, 17, 54);
        FULL_MOON_EPOCH_UTC = fullMoonDate.toEpochSecond(ZoneOffset.UTC);
        SECONDS_IN_DAY = (int) (HOURS_IN_DAY * MINUTES_IN_HOUR * SECONDS_IN_MINUTE);
    }

    private TimeSyncRealLife() {
        throw new IllegalStateException("Utility class");
    }

    public static @NotNull TextComponent getMessage(final @NotNull LocalDateTime currentTime) {
        final LocalDateTime time = currentTime.truncatedTo(ChronoUnit.SECONDS);
        final String formattedTime = time.format(DateTimeFormatter.ofPattern("HH:mm"));
        return Component.text(String.format("的 Серверное время: %s", formattedTime), NamedTextColor.YELLOW);
    }

    private static @NotNull String getCommand(final @NotNull LocalDateTime currentTime) {
        final int convertedTime = convertTime(currentTime);
        final @NotNull String command = "time set " + convertedTime;
        return command;
    }

    public static void synchronizeWith(final @NotNull LocalDateTime currentTime,
                                       final @NotNull ConsoleCommandSender console) {
        final String command = getCommand(currentTime);
        Bukkit.dispatchCommand(console, command);
    }

    public static int convertTime(final @NotNull LocalDateTime currentTime) {
        final @NotNull LocalDateTime fullMoon = adjustFullMoonTime(getFullMoonTime(currentTime), currentTime);
        final double differenceDays = calculateDifferenceDays(currentTime, fullMoon);
        final byte moonPhase = calculateMoonPhase(differenceDays);
        final long epochSecond = currentTime.toEpochSecond(ZoneOffset.UTC);
        final int minecraftTime = calculateMinecraftTime(epochSecond);
        return minecraftTime + MINECRAFT_DAY_LENGTH * moonPhase;
    }

    private static @NotNull LocalDateTime getFullMoonTime(final @NotNull LocalDateTime currentTime) {
        final ZoneId defaultZone = ZoneId.systemDefault();
        final ZoneRules zoneRules = defaultZone.getRules();
        final ZoneOffset zoneOffset = zoneRules.getOffset(currentTime);
        return LocalDateTime.ofEpochSecond(FULL_MOON_EPOCH_UTC, 0, zoneOffset);
    }

    private static @NotNull LocalDateTime adjustFullMoonTime(final @NotNull LocalDateTime fullMoon,
                                                             final @NotNull LocalDateTime currentTime) {
        LocalDateTime adjustedFullMoon = fullMoon;
        while (adjustedFullMoon.isBefore(currentTime)) {
            adjustedFullMoon = adjustedFullMoon.plusDays(29);
            adjustedFullMoon = adjustedFullMoon.plusHours(12);
        }
        return adjustedFullMoon;
    }

    private static double calculateDifferenceDays(final @NotNull LocalDateTime currentTime,
                                                  final @NotNull LocalDateTime fullMoon) {
        final Duration duration = Duration.between(currentTime, fullMoon);
        final long durationSeconds = duration.toSeconds();
        return durationSeconds / (double) SECONDS_IN_DAY;
    }

    private static byte calculateMoonPhase(final double differenceDays) {
        final double adjustedDifferenceDays = STEP / 2d + differenceDays;
        return (byte) (Math.floor(adjustedDifferenceDays / STEP) % 8);
    }

    private static int calculateMinecraftTime(final long epochSecond) {
        final int secondsPastMidnight = (int) (epochSecond % SECONDS_IN_DAY);
        final int minecraftTime = (secondsPastMidnight * MINECRAFT_DAY_LENGTH)
                / SECONDS_IN_DAY + MINECRAFT_DAY_START_OFFSET;
        return minecraftTime % MINECRAFT_DAY_LENGTH;
    }
}
