package com.github.shortlinks.dao.abstracts;


import java.util.Optional;

public interface GenericDao<T, PK> {
    Optional<T> findById(PK id);

    void create(T entity);

    void update(T entity);

    void delete(T entity);

    void deleteById(PK id);

    boolean existById(PK id);

}
