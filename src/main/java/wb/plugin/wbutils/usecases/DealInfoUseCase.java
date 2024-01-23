package wb.plugin.wbutils.usecases;

import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.adapters.DealsRepository;

import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DealInfoUseCase {

    private final DealsRepository databaseDeals;

    public DealInfoUseCase(final DealsRepository databaseDeals) {
        this.databaseDeals = databaseDeals;
    }

    public @NotNull CompletableFuture<Stream<String>> getDealIds(final Predicate<String> filter) {
        return CompletableFuture.supplyAsync(() ->
                IntStream.rangeClosed(1, databaseDeals.getDealsQuantity())
                        .mapToObj(Integer::toString)
                        .filter(filter));
    }
}
