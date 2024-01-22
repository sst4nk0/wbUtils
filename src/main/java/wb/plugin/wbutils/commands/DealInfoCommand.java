package wb.plugin.wbutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.adapters.IDatabaseDeals;

public class DealInfoCommand implements CommandExecutor {

    private final IDatabaseDeals databaseDeals;

    public DealInfoCommand(final IDatabaseDeals databaseDeals) {
        super();
        this.databaseDeals = databaseDeals;
    }

    /**
     * Do something.
     * @param sender  sender
     * @param command command
     * @param label   label
     * @param args    args
     * @return some boolean
     */
    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String label, final @NotNull String[] args) {
        if (sender instanceof Player) {
            if (!sender.hasPermission("wbutils.dealinfo")) return true;
            if (args.length != 3) {
                sender.sendMessage(ChatColor.GRAY + "的 Ошибка ввода. Пример: /dealinfo <deal_id> <stat> <value>");
                return true;
            }
            args[0] = args[0].replaceAll("\\D+","");
            int dealId = Integer.parseInt(args[0]);
            String argValue = args[2].replaceAll("[^\\d-]", "");
            if (argValue.equals("-")) argValue = "0";
            if (dealId <= 0 || dealId > databaseDeals.getDealsQuantity()) return true;

            switch (args[1]) {
                case "owner" -> {
                    Player target = Bukkit.getServer().getPlayerExact(args[2]);

                    System.out.println("[" + sender.getName() + "] [DEAL] [SET] [number:" + args[0] + "] [to:" + args[2] + "] [from:" + databaseDeals.getOwner(Integer.parseInt(args[0])) + "]");
                    databaseDeals.setOwner(dealId, args[2]);
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
                    System.out.println("[" + sender.getName() + "] [DEAL] [STATS] [number:" + args[0] + "] [" + args[1] + ":" + args[2] + "] [previous:" + databaseDeals.getCoinsGold(Integer.parseInt(args[0])) + "]");
                    adminChangedDeal(sender, args);
                    databaseDeals.setCoinsGold(dealId, argValue);
                }
                case "coins_silver" -> {
                    System.out.println("[" + sender.getName() + "] [DEAL] [STATS] [number:" + args[0] + "] [" + args[1] + ":" + args[2] + "] [previous:" + databaseDeals.getCoinsSilver(Integer.parseInt(args[0])) + "]");
                    adminChangedDeal(sender, args);
                    databaseDeals.setCoinsSilver(dealId, argValue);
                }
                case "coins_copper" -> {
                    System.out.println("[" + sender.getName() + "] [DEAL] [STATS] [number:" + args[0] + "] [" + args[1] + ":" + args[2] + "] [previous:" + databaseDeals.getCoinsCopper(Integer.parseInt(args[0])) + "]");
                    adminChangedDeal(sender, args);
                    databaseDeals.setCoinsCopper(dealId, argValue);
                }
                case "materials" -> {
                    System.out.println("[" + sender.getName() + "] [DEAL] [STATS] [number:" + args[0] + "] [" + args[1] + ":" + args[2] + "] [previous:" + databaseDeals.getMaterials(Integer.parseInt(args[0])) + "]");
                    adminChangedDeal(sender, args);
                    databaseDeals.setMaterials(dealId, argValue);
                }
                default -> sender.sendMessage(ChatColor.GRAY + "的 Ошибка ввода. Пример: /dealinfo <deal_id> <stat> <value>");
            }
        } else {
            if (args.length < 3) {
                System.out.println("[CONSOLE] [MSG] [Ошибка ввода. Пример: /dealinfo <deal_id> <stat> <value>]");
                return true;
            }
            int dealId = Integer.parseInt(args[0].replaceAll("\\D+",""));
            String argValue = args[2].replaceAll("\\D+","");
            if (dealId <= 0 || dealId > databaseDeals.getDealsQuantity()) return true;

            switch (args[1]) {
                case "owner" -> {
                    Player target = Bukkit.getServer().getPlayerExact(args[2]);
                    System.out.println("[" + sender.getName() + "] [DEAL] [SET] [number:" + args[0] + "] [to:" + args[2] + "] [from:" + databaseDeals.getOwner(Integer.parseInt(args[0])) + "]");
                    if (target != null) { target.sendMessage(ChatColor.YELLOW + "我 Сделка №" + args[0] + " теперь ваша!"); }
                    databaseDeals.setOwner(dealId, args[2]);
                }
                case "coins_gold" -> {
                    System.out.println("[" + sender.getName() + "] [DEAL] [STATS] [number:" + args[0] + "] [" + args[1] + ":" + args[2] + "] [previous:" + databaseDeals.getCoinsGold(Integer.parseInt(args[0])) + "]");
                    databaseDeals.setCoinsGold(dealId, argValue);
                }
                case "coins_silver" -> {
                    System.out.println("[" + sender.getName() + "] [DEAL] [STATS] [number:" + args[0] + "] [" + args[1] + ":" + args[2] + "] [previous:" + databaseDeals.getCoinsSilver(Integer.parseInt(args[0])) + "]");
                    databaseDeals.setCoinsSilver(dealId, argValue);
                }
                case "coins_copper" -> {
                    System.out.println("[" + sender.getName() + "] [DEAL] [STATS] [number:" + args[0] + "] [" + args[1] + ":" + args[2] + "] [previous:" + databaseDeals.getCoinsCopper(Integer.parseInt(args[0])) + "]");
                    databaseDeals.setCoinsCopper(dealId, argValue);
                }
                case "materials" -> {
                    System.out.println("[" + sender.getName() + "] [DEAL] [STATS] [number:" + args[0] + "] [" + args[1] + ":" + args[2] + "] [previous:" + databaseDeals.getMaterials(Integer.parseInt(args[0])) + "]");
                    databaseDeals.setMaterials(dealId, argValue);
                }
                default -> System.out.println("[CONSOLE] [msg] [Ошибка ввода. Пример: /dealinfo <deal_id> <stat> <value>]");
            }
        }
        return true;
    }

    /**
     * Send info that admin has changed a deal.
     * @param sender sender
     * @param args   args
     */
    private void adminChangedDeal(final CommandSender sender, final String[] args) {
        for (Player playersOnline : Bukkit.getOnlinePlayers()) {
            playersOnline.sendMessage(ChatColor.RED + "我 Администратор " + sender.getName() + " изменил статистику сделки №" + args[0]);
        }
    }

}
