package wb.plugin.wbutils.adapters.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(final @NotNull PlayerJoinEvent jEvent) {
        final TextComponent prefix = Component.text("加 ");
        final TextComponent content;
        final Player player = jEvent.getPlayer();
        final Component displayNameComponent = player.displayName();
        final String playerName = PlainTextComponentSerializer.plainText().serialize(displayNameComponent);
        final NamedTextColor color = NamedTextColor.YELLOW;
        if (player.hasPlayedBefore()) {
            final ThreadLocalRandom random = ThreadLocalRandom.current();
            switch (random.nextInt(0, 7)) {
                case 0 -> content = Component.text(playerName + " почтил нас своим присутствием.", color);
                case 1 -> content = Component.text(playerName + " решил заглянуть к нам.", color);
                case 2 -> content = Component.text(playerName + " в деле, всем молчать.", color);
                case 3 -> content = Component.text(playerName + " среди нас.", color);
                case 4 -> content = Component.text(playerName + " врывается в игру!", color);
                case 5 -> content = Component.text(playerName + " незаметно проник к нам...", color);
                case 6 -> content = Component.text(playerName + " хочет пообщаться!", color);
                default -> throw new IllegalStateException("Unexpected value.");
            }
        } else {
            content = Component.text(playerName + " впервые тут. Приветствуем!", color);
        }

        final TextComponent message = prefix.append(Objects.requireNonNull(content));
        jEvent.joinMessage(message);
    }

    @EventHandler
    public void onPlayerLeave(final @NotNull PlayerQuitEvent qEvent) {
        final TextComponent prefix = Component.text("放 ");
        final TextComponent content;
        final Player player = qEvent.getPlayer();
        final Component displayNameComponent = player.displayName();
        final String playerName = PlainTextComponentSerializer.plainText().serialize(displayNameComponent);
        final NamedTextColor color = NamedTextColor.YELLOW;
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        switch (random.nextInt(0, 7)) {
            case 0 -> content = Component.text(playerName + " устал от нашей компании.", color);
            case 1 -> content = Component.text(playerName + " решил сделать перерыв.", color);
            case 2 -> content = Component.text(playerName + " засиделся.", color);
            case 3 -> content = Component.text(playerName + " обрадовал нас своим уходом!", color);
            case 4 -> content = Component.text(playerName + " ушёл по-английски...", color);
            case 5 -> content = Component.text(playerName + " отправился в реальность.", color);
            case 6 -> content = Component.text(playerName + " нашёл кнопку отключения...", color);
            default -> throw new IllegalStateException("Unexpected value.");
        }

        final TextComponent message = prefix.append(Objects.requireNonNull(content));
        qEvent.quitMessage(message);
    }
}
