package wb.plugin.wbutils.adapters.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.usecases.DealInfoUseCase;

import java.util.logging.Logger;

public class DealInfoCommand implements CommandExecutor {

    private static final Logger LOGGER = Logger.getLogger(DealInfoCommand.class.getName());
    private final DealInfoUseCase dealInfoUseCase;

    public DealInfoCommand(final DealInfoUseCase dealInfoUseCase) {
        this.dealInfoUseCase = dealInfoUseCase;
    }

    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String label, final @NotNull String[] args) {
        final String senderName = sender.getName();
        final DealInfoResult result;
        if (sender instanceof Player) {
            if (!sender.hasPermission("wbutils.dealinfo")) {
                return true;
            }
            if (args.length != 3) {
                sender.sendMessage(Component.text("Ошибка ввода. Пример: /dealinfo <deal_id> <stat> <value>", NamedTextColor.GRAY));
                return true;
            }
        } else {
            if (args.length < 3) {
                LOGGER.info("[CONSOLE] [MSG] [Ошибка ввода. Пример: /dealinfo <deal_id> <stat> <value>]");
                return true;
            }
        }

        result = dealInfoUseCase.execute(senderName, args);
        final String dealId = args[0];
        final String playerName = args[2];
        final String contentDealModified = "我 Администратор " + senderName + " изменил статистику сделки №" + dealId;
        switch (result) {
            case INVALID_INPUT -> {
                sender.sendMessage(Component.text("Ошибка ввода.", NamedTextColor.GRAY));
                return true;
            }
            case DEAL_NOT_FOUND -> {
                sender.sendMessage(Component.text("Сделка не найдена.", NamedTextColor.GRAY));
                return true;
            }
            case OWNER_CHANGED -> {
                sender.sendMessage(Component.text("Владелец сделки изменен.", NamedTextColor.GRAY));

                final Player target = Bukkit.getServer().getPlayerExact(playerName);
                final String broadcastContent = "我 Администратор " + senderName + " дал " + playerName + " сделку №" + dealId;
                Bukkit.broadcast(Component.text(broadcastContent, NamedTextColor.RED));
                if (target != null) {
                    final String content = "我 Сделка №" + dealId + " теперь ваша!";
                    target.sendMessage(Component.text(content, NamedTextColor.YELLOW));
                }
                return true;
            }
            case GOLD_COINS_CHANGED -> {
                sender.sendMessage(Component.text("Количество золотых монет изменено.", NamedTextColor.GRAY));

                Bukkit.broadcast(Component.text(contentDealModified, NamedTextColor.RED));
                return true;
            }
            case SILVER_COINS_CHANGED -> {
                sender.sendMessage(Component.text("Количество серебряных монет изменено.", NamedTextColor.GRAY));

                Bukkit.broadcast(Component.text(contentDealModified, NamedTextColor.RED));
                return true;
            }
            case COPPER_COINS_CHANGED -> {
                sender.sendMessage(Component.text("Количество медных монет изменено.", NamedTextColor.GRAY));

                Bukkit.broadcast(Component.text(contentDealModified, NamedTextColor.RED));
                return true;
            }
            case MATERIALS_CHANGED -> {
                sender.sendMessage(Component.text("Количество материалов изменено.", NamedTextColor.GRAY));

                Bukkit.broadcast(Component.text(contentDealModified, NamedTextColor.RED));
                return true;
            }
            case UNKNOWN_STAT -> {
                sender.sendMessage(Component.text("Неизвестный параметр.", NamedTextColor.GRAY));
                return true;
            }
            case DEAL_OWNER_RESET -> {
                sender.sendMessage(Component.text("Владелец сделки сброшен.", NamedTextColor.GRAY));

                final String content = "我 Администратор " + senderName + " обнулил сделку №" + dealId;
                Bukkit.broadcast(Component.text(content, NamedTextColor.RED));
                return true;
            }
        }


        return true;
    }
}
