package wb.plugin.wbutils.utilities.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface AsyncRepository<T, Id> {

    CompletableFuture<T> save(T entity);

    CompletableFuture<Iterable<T>> saveAll(Iterable<T> entities);

    CompletableFuture<Optional<T>> findById(Id id);

    CompletableFuture<Boolean> existsById(Id id);

    CompletableFuture<List<T>> findAll();

    CompletableFuture<List<T>> findAllById(Iterable<Id> ids);

    CompletableFuture<Long> count();

    CompletableFuture<Void> deleteById(Id id);

    CompletableFuture<Void> delete(T entity);

    CompletableFuture<Void> deleteAllById(Iterable<Id> ids);

    CompletableFuture<Void> deleteAll(Iterable<T> entities);

    CompletableFuture<Void> deleteAll();
}
