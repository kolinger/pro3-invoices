package me.kolinger.pro3.invoices.model;

import me.kolinger.pro3.invoices.common.LoggedObject;
import me.kolinger.pro3.invoices.model.filter.AbstractFilter;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public abstract class AbstractDao<T> extends LoggedObject {

    @Autowired
    public SessionFactory sessionFactory;

    private Class<T> entityClass;

    public AbstractDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void delete(T entity) {
        getSession().delete(entity);
    }

    public void save(T entity) {
        getSession().saveOrUpdate(entity);
    }

    public T createNew() {
        try {
            return (T) getEntityClass().newInstance();
        } catch (InstantiationException e) {
            getLogger().error("Unable to create instance of {}", getClass());
        } catch (IllegalAccessException e) {
            getLogger().error("Unable to create instance of {}", getClass());
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public T findOneById(Long id) {
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq("id", id));
        return (T) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        Criteria criteria = createCriteria();
        return (List<T>) criteria.list();
    }

    /**
     * ***************************** primefaces *****************************
     */

    @SuppressWarnings("unchecked")
    public List<T> findAll(int first, int pageSize, String sortField, SortOrder sortOrder, AbstractFilter filter) {
        Criteria criteria = createCriteria();

        // WHERE
        if (filter != null) {
            filter.applyFilters(criteria);
        }

        // ORDER
        if (sortField != null) {
            if (sortOrder == SortOrder.ASCENDING) {
                criteria.addOrder(Order.asc(sortField));
            } else if (sortOrder == SortOrder.DESCENDING) {
                criteria.addOrder(Order.desc(sortField));
            }
        }

        // LIMIT
        criteria.setFirstResult(first);
        if (pageSize != 0) {
            criteria.setMaxResults(pageSize);
        }

        // additional criteria
        applyDataTableCriteria(criteria);

        return (List<T>) criteria.list();
    }

    @SuppressWarnings("unchecked")
    public Integer count(String sortField, SortOrder sortOrder, AbstractFilter filter) {
        Criteria criteria = createCriteria();

        // WHERE
        if (filter != null) {
            filter.applyFilters(criteria);
        }

        // ORDER
        if (sortField != null) {
            if (sortOrder == SortOrder.ASCENDING) {
                criteria.addOrder(Order.asc(sortField));
            } else if (sortOrder == SortOrder.DESCENDING) {
                criteria.addOrder(Order.desc(sortField));
            }
        }

        // additional criteria
        applyDataTableCriteria(criteria);

        criteria.setProjection(Projections.rowCount());
        return (Integer) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll(int first, int pageSize, List<SortMeta> multiSortMeta, AbstractFilter filter) {
        Criteria criteria = createCriteria();

        // WHERE
        if (filter != null) {
            filter.applyFilters(criteria);
        }

        // ORDER
        if (multiSortMeta != null) {
            for (SortMeta sort : multiSortMeta) {
                if (sort.getSortField() != null) {
                    if (sort.getSortOrder() == SortOrder.ASCENDING) {
                        criteria.addOrder(Order.asc(sort.getSortField()));
                    } else if (sort.getSortOrder() == SortOrder.DESCENDING) {
                        criteria.addOrder(Order.desc(sort.getSortField()));
                    }
                }
            }
        }

        // LIMIT
        if (first > 0) {
            criteria.setFirstResult(first);
        }
        if (pageSize > 0) {
            criteria.setMaxResults(pageSize);
        }

        // additional criteria
        applyDataTableCriteria(criteria);

        return (List<T>) criteria.list();
    }

    @SuppressWarnings("unchecked")
    public Integer count(List<SortMeta> multiSortMeta, AbstractFilter filter) {
        Criteria criteria = createCriteria();

        // WHERE
        if (filter != null) {
            filter.applyFilters(criteria);
        }

        // ORDER
        if (multiSortMeta != null) {
            for (SortMeta sort : multiSortMeta) {
                if (sort.getSortField() != null) {
                    if (sort.getSortOrder() == SortOrder.ASCENDING) {
                        criteria.addOrder(Order.asc(sort.getSortField()));
                    } else if (sort.getSortOrder() == SortOrder.DESCENDING) {
                        criteria.addOrder(Order.desc(sort.getSortField()));
                    }
                }
            }
        }

        // additional criteria
        applyDataTableCriteria(criteria);

        criteria.setProjection(Projections.rowCount());
        return ((Long) criteria.uniqueResult()).intValue();
    }

    /**
     * ***************************** helpers *****************************
     */

    protected void applyDataTableCriteria(Criteria criteria) {
    }

    protected Criteria createCriteria() {
        return getSession().createCriteria(getEntityClass());
    }

    protected Criteria createCriteria(Class clazz) {
        return getSession().createCriteria(clazz);
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }
}