package com.logistic.dao.base;

import com.logistic.domain.base.IEntity;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.Attribute;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;


@SuppressWarnings("rawtypes")
public interface IBaseDao<T extends IEntity> {

    void create(T entity);

    void create(Collection<T> list);

    void update(T entity);

    void update(Collection<T> list);

    void delete(T entity);

    void deleteAndFlush(T entity);

    void delete(Collection<T> list);

    T find(Serializable id);

    T find(T entity);

    T find(Attribute attribute, Object value);

    T find(Attribute[] attributes, Object[] values);

    List<T> findAll();

    List<T> findAll(int first, int max);

    List<T> findAll(Attribute attribute, Object value);

    List<T> findAll(Attribute attribute, Object value, int first, int max);

    List<T> findAll(Attribute[] attributes, Object[] values);

    List<T> findAll(Attribute[] attributes, Object[] values, int first, int max);

    List<T> findAll(CriteriaQuery<T> criteria);

    List<T> findAll(CriteriaQuery<T> criteria, int first, int max);

    List<T> findAllByQuery(String queryStr);

    <R> List<R> findAllByQuery(String queryStr, Class<R> resultClass);

    List<T> findAllByQuery(String queryStr, QueryParam... params);

    <R> List<R> findAllByQuery(String queryStr, Class<R> resultClass, QueryParam... params);

    Class<T> getEntityClass();
}