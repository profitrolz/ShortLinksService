package com.github.shortlinks.dao.abstracts;


public interface GenericDao<T, PK> {
    void create(T entity);

    void update(T entity);

    void delete(T entity);

    void deleteById(PK id);

    boolean existById(PK id);

}
