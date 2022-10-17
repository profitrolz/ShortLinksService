package com.github.shortlinks.dao.abstracts;

import com.github.shortlinks.dao.util.SingleResultUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

public abstract class AbstractGenericDao<T, PK> implements GenericDao<T, PK> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> clazz;

    protected AbstractGenericDao() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        clazz = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public Optional<T> findById(PK id) {

        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery(
                        "SELECT b " +
                                "FROM " + clazz.getSimpleName() + " b " +
                                "WHERE b.id = :paramId", clazz)
                .setParameter("paramId", id));
    }



    @Override
    public void create(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteById(PK id) {
        entityManager.remove(entityManager.find(clazz, id));
    }

    @Override
    public boolean existById(PK id) {
        Long count = entityManager.createQuery(
                        "SELECT count(b) " +
                                "FROM " + clazz.getSimpleName() + " b " +
                                "WHERE b.id = :paramId", Long.class)
                .setParameter("paramId", id)
                .getSingleResult();
        return (count > 0);
    }
}
