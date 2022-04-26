package com.onlinepharma.onlinepharma.dao;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.onlinepharma.onlinepharma.criteria.GenericCriteria;
import com.onlinepharma.onlinepharma.domain.Auditable;
import com.onlinepharma.onlinepharma.utils.BaseUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GenericDao<T extends Auditable, C extends GenericCriteria> {

    @Autowired
    protected EntityManager entityManager;
    @Autowired
    protected BaseUtils utils;

    protected SimpleDateFormat dateTimeFormat;
    protected Gson gson;
    protected JpaEntityInformation<T, ?> entityInformation;
    protected Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public GenericDao() {
        this.dateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        initEntityInformation();
    }

    private void initEntityInformation() {
        if (entityManager != null && entityInformation == null) {
            this.entityInformation = JpaEntityInformationSupport.getEntityInformation(persistentClass, entityManager);
        }
    }

    public T find(Long id) {
        try {
            return entityManager.createQuery("select t from " + persistentClass.getSimpleName() + " t where not t.deleted and t.id = " + id, persistentClass).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public <G> G find(Long id, Class<G> object) {
        try {
            return entityManager.createQuery("Select t from " + object.getSimpleName() + " t where t.state <> 2 and t.id = " + id, object).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public T find(C criteria) {
        Query query = findInit(criteria, false);
        try {
            return (T) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public <G> G findGeneric(C criteria) {
        Query query = findInit(criteria, false);
        try {
            return (G) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public List<T> findAll(C criteria) {
        return findAllGeneric(criteria);
    }

    protected <G> List<G> findAllGeneric(C criteria) {
        Query query = findInit(criteria, false);
        return getResults(criteria, query);
    }

    public Long getTotalCount(C criteria) {
        Query query = findInit(criteria, true);
        return (Long) query.getSingleResult();
    }

    private Query findInit(C criteria, boolean onDefineCount) {
        Query query;
        Map<String, Object> params = new HashMap<>();
        List<String> whereCause = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder();

        defineCriteriaOnQuerying(criteria, whereCause, params, queryBuilder);

        query = defineQuerySelect(criteria, queryBuilder, onDefineCount);

        defineSetterParams(query, params);

        return query;
    }

    private void defineSetterParams(Query query, Map<String, Object> params) {
        params.keySet().forEach(t -> query.setParameter(t, params.get(t)));
    }

    protected void defineCriteriaOnQuerying(C criteria, List<String> whereCause, Map<String, Object> params, StringBuilder queryBuilder) {
        onDefineWhereCause(criteria, whereCause, params, queryBuilder);
    }

    protected void onDefineWhereCause(C criteria, List<String> whereCause, Map<String, Object> params, StringBuilder queryBuilder) {
        if (!whereCause.isEmpty()) {
            queryBuilder.append(" and ").append(StringUtils.join(whereCause, " and "));
        }
    }

    protected Query defineQuerySelect(C criteria, StringBuilder queryBuilder, boolean onDefineCount) {
        String queryStr = " select " + (onDefineCount ? " count(t) " : " t ") + " from " + persistentClass.getSimpleName() + " t " +
                joinStringOnQuerying(criteria) +
                " where not t.deleted " + queryBuilder.toString() + (onDefineCount ? "" : onSortBy(criteria).toString());
        return onDefineCount ? entityManager.createQuery(queryStr, Long.class) : entityManager.createQuery(queryStr);
    }

    protected StringBuilder joinStringOnQuerying(C criteria) {
        return new StringBuilder();
    }

    protected List<Object[]> findAllWithCustomFields(C criteria) {
        return findAllGeneric(criteria);
    }

    protected Object[] findSingleWithCustomFields(C criteria) {
        return findGeneric(criteria);
    }

    protected StringBuilder onSortBy(C criteria) {
        StringBuilder sorting = new StringBuilder();
        if (!utils.isEmpty(criteria.getSortBy())) {
            String ascDesc = criteria.getSortDirection();
            sorting.append(" order by ").append("t.").append(criteria.getSortBy()).append(" ").append(ascDesc);
        }
        return sorting;
    }

    protected <G> List<G> getResults(C criteria, Query query) {
        if ((criteria.getPage() == null || criteria.getPerPage() == null) || (criteria.getPage() < 0 || criteria.getPerPage() <= 0)) {
            return query.getResultList();
        } else {
            return query.setFirstResult(criteria.getPage() * criteria.getPerPage())
                    .setMaxResults(criteria.getPerPage())
                    .getResultList();
        }
    }

    public Long save(T entity) {
        if (entity == null) return null;
        return (Long) getSession().save(entity);
    }

    public T update(T entity) {
        if (entity == null) return null;
        getSession().update(entity);
        return entity;
    }

    public void delete(T entity) {
        if (entity == null) return;
        entity.setDeleted(true);
        save(entity);
    }

    public Session getSession() {
        return entityManager.unwrap(Session.class);
    }
}

