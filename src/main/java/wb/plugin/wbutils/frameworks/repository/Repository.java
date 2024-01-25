package wb.plugin.wbutils.frameworks.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, I> {

    boolean existsById(I id);

    Optional<T> findById(I id);

    List<T> findAll();

    List<T> findAllById(Iterable<I> ids);

    T save(T entity);

    List<T> saveAll(Iterable<T> entities);

    long count();

    void deleteById(I id);

    void delete(T entity);

    void deleteAllById(Iterable<I> ids);

    void deleteAll(Iterable<T> entities);

    void deleteAll();
}
