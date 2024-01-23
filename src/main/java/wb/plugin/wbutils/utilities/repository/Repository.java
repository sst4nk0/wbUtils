package wb.plugin.wbutils.utilities.repository;

import java.util.Optional;

public interface Repository<T, Id> {

    T save(T entity);

    Iterable<T> saveAll(Iterable<T> entities);

    Optional<T> findById(Id id);


    boolean existsById(Id id);

    Iterable<T> findAll();

    Iterable<T> findAllById(Iterable<Id> ids);

    long count();

    void deleteById(Id id);

    void delete(T entity);

    void deleteAllById(Iterable<? extends Id> ids);

    void deleteAll(Iterable<? extends T> entities);

    void deleteAll();
}
