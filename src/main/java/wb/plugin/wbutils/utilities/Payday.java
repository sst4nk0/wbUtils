package wb.plugin.wbutils.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Payday extends BukkitRunnable {

    public Payday() {
        run();
    }

    @Override
    public void run() {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        LocalDateTime timeNow = LocalDateTime.now();
        DateTimeFormatter clockFormat = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter hoursFormat = DateTimeFormatter.ofPattern("HH");
        int hours = Integer.parseInt(hoursFormat.format(timeNow));

        for (Player playersOnline : Bukkit.getOnlinePlayers()) {
            for (byte i = 0; i < 30; i++) {
                playersOnline.sendMessage(" ");
            }
            playersOnline.sendMessage(ChatColor.YELLOW + String.format("我 Серверное время - %s", clockFormat.format(timeNow)));
            playersOnline.sendMessage(ChatColor.YELLOW + "我 Спасибо за игру на нашем сервере!");
            playersOnline.sendMessage(" ");
        }
    }
}
