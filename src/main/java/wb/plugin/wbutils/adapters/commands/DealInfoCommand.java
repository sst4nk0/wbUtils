package wb.plugin.wbutils.adapters.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.entities.Deal;
import wb.plugin.wbutils.adapters.repositories.DealsRepository;

import java.util.logging.Logger;

public class DealInfoCommand implements CommandExecutor {

    private static final Logger LOGGER = Logger.getLogger(DealInfoCommand.class.getName());
    private final DealsRepository databaseDeals;

    public DealInfoCommand(final DealsRepository databaseDeals) {
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
            final String playerName = args[2];
            args[0] = args[0].replaceAll("\\D+","");
            int dealId = Integer.parseInt(args[0]);
            String argValue = playerName.replaceAll("[^\\d-]", "");
            if (argValue.equals("-")) argValue = "0";
            if (dealId <= 0 || dealId > databaseDeals.getDealsQuantity()) return true;

            Deal deal = databaseDeals.getDeal(dealId);
            switch (args[1]) {
                case "owner" -> {
                    Player target = Bukkit.getServer().getPlayerExact(playerName);

                    LOGGER.info("[" + sender.getName() + "] [DEAL] [SET] [number:" + args[0] + "] [to:" + playerName + "] [from:" + deal.owner() + "]");
                    Deal newDeal = new Deal(deal.id(), playerName, deal.coins_copper(), deal.coins_silver(), deal.coins_gold(), deal.materials());
                    databaseDeals.setDeal(dealId, newDeal);
                    if (playerName.equals("-")) {
                        for (Player playersOnline : Bukkit.getOnlinePlayers()) {
                            playersOnline.sendMessage(ChatColor.RED + "我 Администратор " + sender.getName() + " обнулил сделку №" + args[0]);
                        }
                        return true;
                    }
                    for (Player playersOnline : Bukkit.getOnlinePlayers()) {
                        playersOnline.sendMessage(ChatColor.RED + "我 Администратор " + sender.getName() + " дал " + playerName + " сделку №" + args[0]);
                    }
                    if (target != null) {
                        target.sendMessage(ChatColor.YELLOW + "我 Сделка №" + args[0] + " теперь ваша!");
                    }
                }
                case "coins_gold" -> {
                    LOGGER.info("[" + sender.getName() + "] [DEAL] [STATS] [number:" + args[0] + "] [" + args[1] + ":" + playerName + "] [previous:" + deal.coins_gold() + "]");
                    adminChangedDeal(sender, args);
                    Deal newDeal = new Deal(deal.id(), deal.owner(), deal.coins_copper(), deal.coins_silver(), argValue, deal.materials());
                    databaseDeals.setDeal(dealId, newDeal);
                }
                case "coins_silver" -> {
                    LOGGER.info("[" + sender.getName() + "] [DEAL] [STATS] [number:" + args[0] + "] [" + args[1] + ":" + playerName + "] [previous:" + deal.coins_silver() + "]");
                    adminChangedDeal(sender, args);
                    Deal newDeal = new Deal(deal.id(), deal.owner(), deal.coins_copper(), argValue, deal.coins_gold(), deal.materials());
                    databaseDeals.setDeal(dealId, newDeal);
                }
                case "coins_copper" -> {
                    LOGGER.info("[" + sender.getName() + "] [DEAL] [STATS] [number:" + args[0] + "] [" + args[1] + ":" + playerName + "] [previous:" + deal.coins_copper() + "]");
                    adminChangedDeal(sender, args);
                    Deal newDeal = new Deal(deal.id(), deal.owner(), argValue, deal.coins_silver(), deal.coins_gold(), deal.materials());
                    databaseDeals.setDeal(dealId, newDeal);
                }
                case "materials" -> {
                    LOGGER.info("[" + sender.getName() + "] [DEAL] [STATS] [number:" + args[0] + "] [" + args[1] + ":" + playerName + "] [previous:" + deal.materials() + "]");
                    adminChangedDeal(sender, args);
                    Deal newDeal = new Deal(deal.id(), deal.owner(), deal.coins_copper(), deal.coins_silver(), deal.coins_gold(), argValue);
                    databaseDeals.setDeal(dealId, newDeal);
                }
                default -> sender.sendMessage(ChatColor.GRAY + "的 Ошибка ввода. Пример: /dealinfo <deal_id> <stat> <value>");
            }
        } else {
            if (args.length < 3) {
                LOGGER.info("[CONSOLE] [MSG] [Ошибка ввода. Пример: /dealinfo <deal_id> <stat> <value>]");
                return true;
            }
            final String playerName = args[2];
            int dealId = Integer.parseInt(args[0].replaceAll("\\D+",""));
            String argValue = playerName.replaceAll("\\D+","");
            if (dealId <= 0 || dealId > databaseDeals.getDealsQuantity()) return true;
            Deal deal = databaseDeals.getDeal(dealId);

            switch (args[1]) {
                case "owner" -> {
                    Player target = Bukkit.getServer().getPlayerExact(playerName);
                    LOGGER.info("[" + sender.getName() + "] [DEAL] [SET] [number:" + args[0] + "] [to:" + playerName + "] [from:" + deal.owner() + "]");
                    if (target != null) { target.sendMessage(ChatColor.YELLOW + "我 Сделка №" + args[0] + " теперь ваша!"); }
                    Deal newDeal = new Deal(deal.id(), playerName, deal.coins_copper(), deal.coins_silver(), deal.coins_gold(), deal.materials());
                    databaseDeals.setDeal(dealId, newDeal);
                }
                case "coins_gold" -> {
                    LOGGER.info("[" + sender.getName() + "] [DEAL] [STATS] [number:" + args[0] + "] [" + args[1] + ":" + playerName + "] [previous:" + deal.coins_gold() + "]");
                    Deal newDeal = new Deal(deal.id(), deal.owner(), deal.coins_copper(), deal.coins_silver(), argValue, deal.materials());
                    databaseDeals.setDeal(dealId, newDeal);
                }
                case "coins_silver" -> {
                    LOGGER.info("[" + sender.getName() + "] [DEAL] [STATS] [number:" + args[0] + "] [" + args[1] + ":" + playerName + "] [previous:" + deal.coins_silver() + "]");
                    Deal newDeal = new Deal(deal.id(), deal.owner(), deal.coins_copper(), argValue, deal.coins_gold(), deal.materials());
                    databaseDeals.setDeal(dealId, newDeal);
                }
                case "coins_copper" -> {
                    LOGGER.info("[" + sender.getName() + "] [DEAL] [STATS] [number:" + args[0] + "] [" + args[1] + ":" + playerName + "] [previous:" + deal.coins_copper() + "]");
                    Deal newDeal = new Deal(deal.id(), deal.owner(), argValue, deal.coins_silver(), deal.coins_gold(), deal.materials());
                    databaseDeals.setDeal(dealId, newDeal);
                }
                case "materials" -> {
                    LOGGER.info("[" + sender.getName() + "] [DEAL] [STATS] [number:" + args[0] + "] [" + args[1] + ":" + playerName + "] [previous:" + deal.materials() + "]");
                    Deal newDeal = new Deal(deal.id(), deal.owner(), deal.coins_copper(), deal.coins_silver(), deal.coins_gold(), argValue);
                    databaseDeals.setDeal(dealId, newDeal);
                }
                default -> LOGGER.info("[CONSOLE] [msg] [Ошибка ввода. Пример: /dealinfo <deal_id> <stat> <value>]");
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
