package wb.plugin.wbutils.deals;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandDealInfo implements CommandExecutor {
    /**
     * Do something.
     *
     * @param sender  sender
     * @param command command
     * @param label   label
     * @param args    args
     * @return some boolean
     */
    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String label, final @NotNull String[] args) {
        if (sender instanceof Player) {
            if (!sender.hasPermission("wbutils.dealinfo")) { return true; }
            if (args.length != 3) {
                sender.sendMessage(ChatColor.GRAY + "的 Ошибка ввода. Пример: /dealinfo <deal_id> <stat> <value>");
                return true;
            }
            switch (args[1]) {
                case "owner" -> {
                    Player target = Bukkit.getServer().getPlayerExact(args[2]);
                    System.out.println("[" + sender.getName() + "] [DEAL] [SET] [number:" + args[0] + "] [to:" + args[2] + "] [from:" + FileDealsData.get().getString(args[0] + ".owner") + "]");
                    FileDealsData.get().set(args[0] + ".owner", args[2]);
                    if (args[2].equals("-")) {
                        for (Player playersOnline : Bukkit.getOnlinePlayers()) {
                            playersOnline.sendMessage(ChatColor.RED + "我 Администратор " + sender.getName() + " обнулил сделку №" + args[0]);
                        }
                        return true;
                    }
                    for (Player playersOnline : Bukkit.getOnlinePlayers()) {
                        playersOnline.sendMessage(ChatColor.RED + "我 Администратор " + sender.getName() + " дал " + args[2] + " сделку №" + args[0]);
                    }
                    if (target != null) {
                        target.sendMessage(ChatColor.YELLOW + "我 Сделка №" + args[0] + " теперь ваша!");
                    }
                }
                case "coins_gold" -> {
                    System.out.println("[" + sender.getName() + "] [DEAL] [STATS] [number:" + args[0] + "] [" + args[1] + ":" + args[2] + "] [previous:" + FileDealsData.get().getString(args[0] + ".coins_gold") + "]");
                    for (Player playersOnline : Bukkit.getOnlinePlayers()) { playersOnline.sendMessage(ChatColor.RED + "我 Администратор " + sender.getName() + " изменил статистику сделки №" + args[0]); }
                    FileDealsData.get().set(args[0] + ".coins_gold", args[2]);
                }
                case "coins_silver" -> {
                    System.out.println("[" + sender.getName() + "] [DEAL] [STATS] [number:" + args[0] + "] [" + args[1] + ":" + args[2] + "] [previous:" + FileDealsData.get().getString(args[0] + ".coins_silver") + "]");
                    for (Player playersOnline : Bukkit.getOnlinePlayers()) { playersOnline.sendMessage(ChatColor.RED + "我 Администратор " + sender.getName() + " изменил статистику сделки №" + args[0]); }
                    FileDealsData.get().set(args[0] + ".coins_silver", args[2]);
                }
                case "coins_copper" -> {
                    System.out.println("[" + sender.getName() + "] [DEAL] [STATS] [number:" + args[0] + "] [" + args[1] + ":" + args[2] + "] [previous:" + FileDealsData.get().getString(args[0] + ".coins_copper") + "]");
                    for (Player playersOnline : Bukkit.getOnlinePlayers()) { playersOnline.sendMessage(ChatColor.RED + "我 Администратор " + sender.getName() + " изменил статистику сделки №" + args[0]); }
                    FileDealsData.get().set(args[0] + ".coins_copper", args[2]);
                }
                case "materials" -> {
                    System.out.println("[" + sender.getName() + "] [DEAL] [STATS] [number:" + args[0] + "] [" + args[1] + ":" + args[2] + "] [previous:" + FileDealsData.get().getString(args[0] + ".materials") + "]");
                    for (Player playersOnline : Bukkit.getOnlinePlayers()) { playersOnline.sendMessage(ChatColor.RED + "我 Администратор " + sender.getName() + " изменил статистику сделки №" + args[0]); }
                    FileDealsData.get().set(args[0] + ".materials", args[2]);
                }
                default -> sender.sendMessage(ChatColor.GRAY + "的 Ошибка ввода. Пример: /dealinfo <deal_id> <stat> <value>");
            }
        } else {
            if (args.length < 3) {
                System.out.println("[CONSOLE] [msg] [Ошибка ввода. Пример: /dealinfo <deal_id> <stat> <value>]");
                return true;
            }
            switch (args[1]) {
                case "owner" -> {
                    Player target = Bukkit.getServer().getPlayerExact(args[2]);
                    System.out.println("[" + sender.getName() + "] [DEAL] [SET] [number:" + args[0] + "] [to:" + args[2] + "] [from:" + FileDealsData.get().getString(args[0] + ".owner") + "]");
                    if (target != null) { target.sendMessage(ChatColor.YELLOW + "我 Сделка №" + args[0] + " теперь ваша!"); }
                    FileDealsData.get().set(args[0] + ".owner", args[2]);
                }
                case "coins_gold" -> {
                    System.out.println("[" + sender.getName() + "] [DEAL] [STATS] [number:" + args[0] + "] [" + args[1] + ":" + args[2] + "] [previous:" + FileDealsData.get().getString(args[0] + ".coins_gold") + "]");
                    FileDealsData.get().set(args[0] + ".coins_gold", args[2]);
                }
                case "coins_silver" -> {
                    System.out.println("[" + sender.getName() + "] [DEAL] [STATS] [number:" + args[0] + "] [" + args[1] + ":" + args[2] + "] [previous:" + FileDealsData.get().getString(args[0] + ".coins_silver") + "]");
                    FileDealsData.get().set(args[0] + ".coins_silver", args[2]);
                }
                case "coins_copper" -> {
                    System.out.println("[" + sender.getName() + "] [DEAL] [STATS] [number:" + args[0] + "] [" + args[1] + ":" + args[2] + "] [previous:" + FileDealsData.get().getString(args[0] + ".coins_copper") + "]");
                    FileDealsData.get().set(args[0] + ".coins_copper", args[2]);
                }
                default -> System.out.println("[CONSOLE] [msg] [Ошибка ввода. Пример: /dealinfo <deal_id> <stat> <value>]");
            }
        }
        return true;
    }
}
