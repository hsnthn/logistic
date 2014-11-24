package com.logistic.business.base;

import com.logistic.domain.base.IEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface IBaseService<T extends IEntity> extends Serializable {

    void create(T entity);

    void create(Collection<T> list);

    void update(T entity);

    void update(Collection<T> list);

    void delete(T entity);

    void deleteAndFlush(T entity);

    void delete(Collection<T> list);

    T find(Serializable id);

    T find(T entity);

    List<T> findAll();

    List<T> findAll(int first, int max);

}
