package wb.plugin.wbutils.utilities;

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

public class TimeSyncRealLife {

    private static final long FULL_MOON_EPOCH_UTC = LocalDateTime.of(2023, 9, 29, 7, 50, 0).toEpochSecond(ZoneOffset.UTC);
    private static final double DAYS_IN_MOON_CYCLE = 29.5d;
    private static final double STEP = DAYS_IN_MOON_CYCLE / 8d;
    private static final int SECONDS_IN_DAY = 86400;
    private static final int MINECRAFT_DAY_LENGTH = 24000;
    private static final int MINECRAFT_DAY_START_OFFSET = 18000;

    public static @NotNull TextComponent getMessageLocalTime() {
        final @NotNull LocalDateTime currentTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        return getMessage(currentTime);
    }

    public static @NotNull TextComponent getMessage(final @NotNull LocalDateTime currentTime) {
        final @NotNull ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        final int convertedTime = convertTime(currentTime);
        final @NotNull String command = "time set " + convertedTime;
        Bukkit.dispatchCommand(console, command);
        final String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        return Component.text(String.format("的 Серверное время: %s", formattedTime), NamedTextColor.YELLOW);
    }

    public static int convertTime(final @NotNull LocalDateTime currentTime) {
        final @NotNull LocalDateTime fullMoon = adjustFullMoonTime(getFullMoonTime(currentTime), currentTime);
        final double differenceDays = calculateDifferenceDays(currentTime, fullMoon);
        final int moonPhase = calculateMoonPhase(differenceDays);
        final long epochSecond = currentTime.toEpochSecond(ZoneOffset.UTC);
        final int minecraftTime = calculateMinecraftTime(epochSecond);
        return minecraftTime + MINECRAFT_DAY_LENGTH * moonPhase;
    }

    private static @NotNull LocalDateTime getFullMoonTime(final @NotNull LocalDateTime currentTime) {
        final ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(currentTime);
        return LocalDateTime.ofEpochSecond(FULL_MOON_EPOCH_UTC, 0, zoneOffset);
    }

    private static @NotNull LocalDateTime adjustFullMoonTime(final @NotNull LocalDateTime fullMoon, final @NotNull LocalDateTime currentTime) {
        LocalDateTime adjustedFullMoon = fullMoon;
        while (adjustedFullMoon.isBefore(currentTime)) {
            adjustedFullMoon = adjustedFullMoon.plusDays(29).plusHours(12);
        }
        return adjustedFullMoon;
    }

    private static double calculateDifferenceDays(final @NotNull LocalDateTime currentTime, final @NotNull LocalDateTime fullMoon) {
        final long difference = Duration.between(currentTime, fullMoon).toSeconds();
        return difference / 24d / 60d / 60d;
    }

    private static int calculateMoonPhase(final double differenceDays) {
        int phase = 0;
        for (double i = STEP; i <= DAYS_IN_MOON_CYCLE; i += STEP) {
            if (differenceDays < i) {
                break;
            }
            phase++;
        }
        return phase;
    }

    private static int calculateMinecraftTime(final long epochSecond) {
        final int secondsPastMidnight = (int) (epochSecond % SECONDS_IN_DAY);
        final int minecraftTime = (secondsPastMidnight * MINECRAFT_DAY_LENGTH) / SECONDS_IN_DAY + MINECRAFT_DAY_START_OFFSET;
        return minecraftTime % MINECRAFT_DAY_LENGTH;
    }
}
