package me.kolinger.pro3.invoices.model;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public abstract class DeletableDao<T extends DeletableEntity> extends AbstractDao<T> {

    public DeletableDao(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    public void delete(T entity) {
        entity.setDeleted(true);
        save(entity);
    }

    public void permanentDelete(T entity) {
        super.delete(entity);
    }

    @Override
    protected Criteria createCriteria() {
        Criteria criteria = super.createCriteria();
        criteria.add(Restrictions.eq("deleted", false));
        return criteria;
    }

    @Override
    protected Criteria createCriteria(Class clazz) {
        Criteria criteria = super.createCriteria(clazz);
        criteria.add(Restrictions.eq("deleted", false));
        return criteria;
    }
}