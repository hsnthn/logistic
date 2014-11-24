package com.logistic.dao.base.impl;

import com.logistic.dao.base.IBaseDao;
import com.logistic.dao.base.QueryParam;
import com.logistic.domain.base.IEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.jodah.typetools.TypeResolver.resolveRawArguments;
import static org.springframework.transaction.annotation.Propagation.MANDATORY;

@SuppressWarnings("rawtypes")
@Transactional(propagation = MANDATORY, rollbackFor = Exception.class, value = "transactionManagerLogistic")
public abstract class AbstractBaseDao<T extends IEntity> implements IBaseDao<T> {

    private Class<T> entityClass;

    @PersistenceContext(unitName = "entityManagerFactoryLogistic")
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    protected AbstractBaseDao() {
        this.entityClass = (Class<T>) resolveRawArguments(AbstractBaseDao.class, getClass())[0];
    }

    @Override
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    @Override
    public void create(Collection<T> list) {
        for (T t : list) {
            create(t);
        }
    }

    @Override
    public void update(T entity) {
        getEntityManager().merge(entity);
    }

    @Override
    public void update(Collection<T> list) {
        for (T t : list) {
            update(t);
        }
    }

    @Override
    public void delete(T entity) {
        getEntityManager().remove(entity);
    }

    @Override
    public void deleteAndFlush(T entity) {
        getEntityManager().remove(entity);
        getEntityManager().flush();
    }

    @Override
    public void delete(Collection<T> list) {
        for (T t : list) {
            delete(t);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public T find(Serializable id) {
        return getEntityManager().find(entityClass, id);
    }

    @Override
    @Transactional(readOnly = true)
    public T find(T entity) {
        return getEntityManager().find(entityClass, entity.getId());
    }

    @Override
    public T find(Attribute attribute, Object value) {
        return find(new Attribute[]{attribute}, new Object[]{value});
    }

    @Override
    public T find(Attribute[] attributes, Object[] values) {
        List<T> results = findAll(createSimpleSelectQuery(attributes, values));
        if (results != null) {
            if (results.size() == 1) {
                return results.get(0);
            }
            if (results.size() > 1) {
                //hata fırlat
            }
        }
        return null;
    }

    @Override
    public List<T> findAll() {
        return findAll(createSimpleSelectQuery());
    }

    @Override
    public List<T> findAll(int first, int max) {
        return findAll(createSimpleSelectQuery(), first, max);
    }

    @Override
    public List<T> findAll(Attribute attribute, Object value) {
        Attribute[] attributes = {attribute};
        Object[] values = {value};
        return findAll(attributes, values);
    }

    @Override
    public List<T> findAll(Attribute attribute, Object value, int first, int max) {
        Attribute[] attributes = {attribute};
        Object[] values = {value};
        return findAll(attributes, values, first, max);
    }

    @Override
    public List<T> findAll(Attribute[] attributes, Object[] values) {
        return findAll(createSimpleSelectQuery(attributes, values));
    }

    @Override
    public List<T> findAll(Attribute[] attributes, Object[] values, int first, int max) {
        return findAll(createSimpleSelectQuery(attributes, values), first, max);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll(CriteriaQuery<T> criteria) {
        return getEntityManager().createQuery(criteria).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll(CriteriaQuery<T> criteria, int first, int max) {

        TypedQuery<T> typedQuery = getEntityManager().createQuery(criteria);

        if (first > -1) {
            typedQuery.setFirstResult(first);
        }

        if (max > 0) {
            typedQuery.setMaxResults(max);
        }

        return typedQuery.getResultList();
    }

    @Override
    public List<T> findAllByQuery(String queryStr) {
        return findAllByQuery(queryStr, entityClass);
    }

    @Override
    @Transactional(readOnly = true)
    public <R> List<R> findAllByQuery(String queryStr, Class<R> resultClass) {
        return getEntityManager().createQuery(queryStr, resultClass).getResultList();
    }

    @Override
    public List<T> findAllByQuery(String queryStr, QueryParam... params) {
        return findAllByQuery(queryStr, entityClass, params);
    }

    @Override
    @Transactional(readOnly = true)
    public <R> List<R> findAllByQuery(String queryStr, Class<R> resultClass, QueryParam... params) {
        TypedQuery<R> query = getEntityManager().createQuery(queryStr, resultClass);
        if (params != null && params.length != 0) {
            for (QueryParam param : params) {
                query.setParameter(param.getName(), param.getValue());
            }
        }
        return query.getResultList();
    }

    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }

    private CriteriaQuery<T> createSimpleSelectQuery() {
        return createSimpleSelectQuery(null, null);
    }

    @SuppressWarnings({"unchecked"})
    private CriteriaQuery<T> createSimpleSelectQuery(Attribute[] attributes, Object[] values) {

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClass);
        Root<T> root = query.from(entityClass);

        query.select(root);

        if (attributes != null && values != null) {
            if (attributes.length != values.length) {
                throw new PersistenceException("The length must be same for both arrays.");
            }
            List<Predicate> predicates = new ArrayList<Predicate>();
            for (int i = 0; i < attributes.length; i++) {
                Attribute attribute = attributes[i];
                if (attribute instanceof SingularAttribute) {
                    SingularAttribute singularAttribute = (SingularAttribute) attributes[i];
                    predicates.add(cb.equal(root.get(singularAttribute), values[i]));
                } else if (attribute instanceof MapAttribute) {
                    throw new PersistenceException("MapAttribute not supported.");
                } else if (attribute instanceof PluralAttribute) {
                    PluralAttribute pluralAttribute = (PluralAttribute) attributes[i];
                    predicates.add(cb.equal(root.get(pluralAttribute), values[i]));
                } else {
                    throw new PersistenceException("Attribute type of '" + attribute
                            + "' must be one of [SingularAttribute, PluralAttribute].");
                }
            }
            if (!predicates.isEmpty()) {
                query.where(predicates.toArray(new Predicate[predicates.size()]));
            }
        }

        return query;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }
}