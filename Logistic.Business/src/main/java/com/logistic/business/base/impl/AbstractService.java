package com.logistic.business.base.impl;

import com.logistic.business.base.IBaseService;
import com.logistic.dao.base.IBaseDao;
import com.logistic.domain.base.IEntity;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@SuppressWarnings("rawtypes")
@Transactional(propagation = REQUIRED, rollbackFor = Exception.class)
public abstract class AbstractService<T extends IEntity> implements IBaseService<T> {

    private IBaseDao<T> baseDao;

    public AbstractService(IBaseDao<T> baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public void create(T entity) {
        baseDao.create(entity);
    }

    @Override
    public void create(Collection<T> list) {
        baseDao.create(list);
    }

    @Override
    public void update(T entity) {
        baseDao.update(entity);
    }

    @Override
    public void update(Collection<T> list) {
        baseDao.update(list);
    }

    @Override
    public void delete(T entity) {
        baseDao.delete(entity);
    }

    @Override
    public void deleteAndFlush(T entity) {
        baseDao.deleteAndFlush(entity);
    }

    @Override
    public void delete(Collection<T> list) {
        baseDao.delete(list);
    }

    @Override
    public T find(Serializable id) {
        return baseDao.find(id);
    }

    @Override
    public T find(T entity) {
        return baseDao.find(entity);
    }

    @Override
    public List<T> findAll() {
        return baseDao.findAll();
    }

    @Override
    public List<T> findAll(int first, int max) {
        return baseDao.findAll(first, max);
    }

    public IBaseDao<T> getBaseDao() {
        return baseDao;
    }


}