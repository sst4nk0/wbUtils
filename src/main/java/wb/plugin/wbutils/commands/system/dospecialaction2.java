package wb.plugin.wbutils.commands.system;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.wbUtils;

public class dospecialaction2 implements CommandExecutor {

    wbUtils plugin;
    public dospecialaction2(wbUtils plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return true;
        if (args.length != 1) return true;

        switch (args[0]){
            case "merchantwood_tkg95mdxa0" -> {
                if (!sender.hasPermission("wb.talkpresent.4")) {
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    Bukkit.dispatchCommand(console, "lp user " + sender.getName() + " permission set wb.talkpresent.4");
                    Player p = (Player) sender;
                    Bukkit.dispatchCommand(console, "effect give " + sender.getName() + " minecraft:slowness 14 3 true");
                    sender.sendMessage(ChatColor.DARK_GREEN + "Купец: " + ChatColor.GREEN + "Чего ты пришёл? Не видно, что я не могу сейчас говорить?");
                    p.playSound(p.getLocation(), Sound.ENTITY_EVOKER_HURT, 1, 1);
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            sender.sendMessage(ChatColor.DARK_GREEN + "Купец: " + ChatColor.GREEN + "Грузим сейчас дерево на корабль для отпраления на новый континент, о котором еще никто не слышал.");
                            p.playSound(p.getLocation(), Sound.ENTITY_EVOKER_HURT, 1, 1);
                        }
                    }.runTaskLater(plugin, 60);
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            sender.sendMessage(ChatColor.DARK_GREEN + "Купец: " + ChatColor.GREEN + "Да, если у тебя будет лишняя деревяшка - приноси мне сразу. Куплю по хорошей цене!");
                            p.playSound(p.getLocation(), Sound.ENTITY_EVOKER_HURT, 1, 1);
                        }
                    }.runTaskLater(plugin, 120);
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            sender.sendMessage(ChatColor.DARK_GREEN + "Купец: " + ChatColor.GREEN + "Нравишься ты мне. Молчишь много. На вот, возьми от меня небольшой подарок...");
                            p.playSound(p.getLocation(), Sound.ENTITY_EVOKER_HURT, 1, 1);
                        }
                    }.runTaskLater(plugin, 180);
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            Bukkit.dispatchCommand(console, "dm open merchant_forest " + sender.getName());
                        }
                    }.runTaskLater(plugin, 250);
                    return true;
                }
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                Bukkit.dispatchCommand(console, "dm open merchant_forest " + sender.getName());
            }
            case "seakippar_fl6pqa1bcz" -> {
                if (!sender.hasPermission("wb.talkpresent.5")) {
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    Bukkit.dispatchCommand(console, "lp user " + sender.getName() + " permission set wb.talkpresent.5");
                    Player p = (Player) sender;
                    Bukkit.dispatchCommand(console, "effect give " + sender.getName() + " minecraft:slowness 14 3 true");
                    sender.sendMessage(ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Привет дружище! Привет мой друг! Рад тебя видеть, хоть и не видел тебя раньше.");
                    p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            sender.sendMessage(ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Ты знал что тебе крупно повезло меня встретить?");
                            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                        }
                    }.runTaskLater(plugin, 65);
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            sender.sendMessage(ChatColor.WHITE + sender.getName() + ": " + ChatColor.GRAY + "Ну и с чем мне повезло-то?");
                        }
                    }.runTaskLater(plugin, 100);
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            sender.sendMessage(ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Я могу довезти тебя в любую точку света! На незиведанные континеты, в тёплые страны на белые пески, либо же к Даннам и Норвегам на север...");
                            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                        }
                    }.runTaskLater(plugin, 135);
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                        }
                    }.runTaskLater(plugin, 145);
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            sender.sendMessage(ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Ты бы видел их воительниц!");
                            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 1, 1);
                        }
                    }.runTaskLater(plugin, 180);
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            sender.sendMessage(ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Я бы покрутил штурвал их драккара...");
                            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 1, 1);
                        }
                    }.runTaskLater(plugin, 205);
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            sender.sendMessage(ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Забыл сказать, бесплатно я тебя не повезу.");
                            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                        }
                    }.runTaskLater(plugin, 230);
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            Bukkit.dispatchCommand(console, "dm open seaskipper_0 " + sender.getName());
                        }
                    }.runTaskLater(plugin, 125);
                    return true;
                }
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                Bukkit.dispatchCommand(console, "dm open sea_skipper " + sender.getName());
            }
            default -> { return true; }
        }
        return true;
    }
}
