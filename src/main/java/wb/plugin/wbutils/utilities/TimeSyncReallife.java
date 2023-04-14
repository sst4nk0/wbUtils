// Вызов TimeSyncReallife синхронизирует положение солнца в реальной жизни и игре

package wb.plugin.wbutils.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeSyncReallife{

    public TimeSyncReallife(Player playersToNotify) {
        run(playersToNotify);
    }

    public void run(Player playersToNotify) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        LocalDateTime timeNow = LocalDateTime.now();
        DateTimeFormatter clockFormat = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter hoursFormat = DateTimeFormatter.ofPattern("HH");
        int hours = Integer.parseInt(hoursFormat.format(timeNow));

        switch (hours) {
            case 0 -> Bukkit.dispatchCommand(console, "time set 18000");
            case 1 -> Bukkit.dispatchCommand(console, "time set 19000");
            case 2 -> Bukkit.dispatchCommand(console, "time set 20000");
            case 3 -> Bukkit.dispatchCommand(console, "time set 21000");
            case 4 -> Bukkit.dispatchCommand(console, "time set 22000");
            case 5 -> Bukkit.dispatchCommand(console, "time set 23000");
            case 6 -> Bukkit.dispatchCommand(console, "time set 0");
            case 7 -> Bukkit.dispatchCommand(console, "time set 1000");
            case 8 -> Bukkit.dispatchCommand(console, "time set 2000");
            case 9 -> Bukkit.dispatchCommand(console, "time set 3000");
            case 10 -> Bukkit.dispatchCommand(console, "time set 4000");
            case 11 -> Bukkit.dispatchCommand(console, "time set 5000");
            case 12 -> Bukkit.dispatchCommand(console, "time set 6000");
            case 13 -> Bukkit.dispatchCommand(console, "time set 7000");
            case 14 -> Bukkit.dispatchCommand(console, "time set 8000");
            case 15 -> Bukkit.dispatchCommand(console, "time set 9000");
            case 16 -> Bukkit.dispatchCommand(console, "time set 10000");
            case 17 -> Bukkit.dispatchCommand(console, "time set 11000");
            case 18 -> Bukkit.dispatchCommand(console, "time set 12000");
            case 19 -> Bukkit.dispatchCommand(console, "time set 13000");
            case 20 -> Bukkit.dispatchCommand(console, "time set 14000");
            case 21 -> Bukkit.dispatchCommand(console, "time set 15000");
            case 22 -> Bukkit.dispatchCommand(console, "time set 16000");
            case 23 -> Bukkit.dispatchCommand(console, "time set 17000");
        }
        playersToNotify.sendMessage(ChatColor.YELLOW + String.format("的 Серверное время - %s", clockFormat.format(timeNow)));
    }
}
