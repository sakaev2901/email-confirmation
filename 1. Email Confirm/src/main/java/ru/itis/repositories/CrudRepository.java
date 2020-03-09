package ru.itis.repositories;

import java.util.List;

public interface CrudRepository<ID, T> {
    T findById(ID id);
    List<T> findAll();
    void save(T model);
    void update(T model);
    void delete(ID id);
}
