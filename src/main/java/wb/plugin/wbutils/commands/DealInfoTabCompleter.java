package wb.plugin.wbutils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import wb.plugin.wbutils.usecases.DealInfoUseCase;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class DealInfoTabCompleter implements TabCompleter {

    private final DealInfoUseCase dealInfoUseCase;

    public DealInfoTabCompleter(final DealInfoUseCase dealInfoUseCase) {
        this.dealInfoUseCase = dealInfoUseCase;
    }

    @Override
    public @Nullable List<String> onTabComplete(final @NotNull CommandSender sender, final @NotNull Command command,
                                                final @NotNull String alias, final @NotNull String[] args) {
        switch (args.length) {
            case 1 -> {
                final Predicate<String> filter = s -> s.toLowerCase().startsWith(args[0].toLowerCase());
                final CompletableFuture<Stream<String>> tabsFuture = dealInfoUseCase.getDealIds(filter);
                return tabsFuture.join().toList();
            }
            case 2 -> {
                return Stream.of("owner", "materials", "coins_gold", "coins_silver", "coins_copper")
                        .filter(s -> s.toLowerCase().startsWith(args[1].toLowerCase())).toList();
            }
            case 3 -> {
                return List.of();
            }
            default -> {
                return null;
            }
        }
    }
}
