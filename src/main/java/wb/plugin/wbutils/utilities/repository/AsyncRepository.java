package wb.plugin.wbutils.utilities.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface AsyncRepository<T, I> {

    CompletableFuture<T> save(T entity);

    CompletableFuture<Iterable<T>> saveAll(Iterable<T> entities);

    CompletableFuture<Optional<T>> findById(I id);

    CompletableFuture<Boolean> existsById(I id);

    CompletableFuture<List<T>> findAll();

    CompletableFuture<List<T>> findAllById(Iterable<I> ids);

    CompletableFuture<Long> count();

    CompletableFuture<Void> deleteById(I id);

    CompletableFuture<Void> delete(T entity);

    CompletableFuture<Void> deleteAllById(Iterable<I> ids);

    CompletableFuture<Void> deleteAll(Iterable<T> entities);

    CompletableFuture<Void> deleteAll();
}
