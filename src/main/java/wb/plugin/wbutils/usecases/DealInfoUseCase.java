package wb.plugin.wbutils.usecases;

import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.adapters.IDatabaseDeals;

import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DealInfoUseCase {

    private final IDatabaseDeals databaseDeals;

    public DealInfoUseCase(final IDatabaseDeals databaseDeals) {
        this.databaseDeals = databaseDeals;
    }

    public @NotNull CompletableFuture<Stream<String>> getDealIds(final Predicate<String> filter) {
        return CompletableFuture.supplyAsync(() ->
                IntStream.rangeClosed(1, databaseDeals.getDealsQuantity())
                        .mapToObj(Integer::toString)
                        .filter(filter));
    }
}
