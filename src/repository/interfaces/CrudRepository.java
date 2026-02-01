package repository.interfaces;

import java.util.List;

public interface CrudRepository<T, ID> {
    void create(T entity);
    List<T> getAll();
    T getById(ID id);
    void update(ID id, T entity);
    void delete(ID id);
}
