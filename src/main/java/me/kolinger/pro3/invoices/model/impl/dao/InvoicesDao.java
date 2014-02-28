package me.kolinger.pro3.invoices.model.impl.dao;

import me.kolinger.pro3.invoices.model.AbstractDao;
import me.kolinger.pro3.invoices.model.impl.entities.Invoice;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Repository
public class InvoicesDao extends AbstractDao<Invoice> {

    public InvoicesDao() {
        super(Invoice.class);
    }

    @Override
    public void delete(Invoice entity) {
        entity.setDeleted(true);
        save(entity);
    }

    public void permanentDelete(Invoice entity) {
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