package me.kolinger.pro3.invoices.model.impl.dao;

import me.kolinger.pro3.invoices.common.Helper;
import me.kolinger.pro3.invoices.model.AbstractDao;
import me.kolinger.pro3.invoices.model.filter.AbstractFilter;
import me.kolinger.pro3.invoices.model.impl.entities.Invoice;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import org.springframework.stereotype.Repository;

import java.util.List;


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

    public List<Invoice> findAll(int first, int pageSize, String sortField, SortOrder sortOrder, AbstractFilter filter) {
        List<Invoice> list = super.findAll(first, pageSize, sortField, sortOrder, filter);
        for (Invoice invoice : list) {
            Hibernate.initialize(invoice.getProducts());
        }
        return list;
    }

    public List<Invoice> findAll(int first, int pageSize, List<SortMeta> multiSortMeta, AbstractFilter filter) {
        List<Invoice> list = super.findAll(first, pageSize, multiSortMeta, filter);
        for (Invoice invoice : list) {
            Hibernate.initialize(invoice.getProducts());
        }
        return list;
    }

    @Override
    protected Criteria createCriteria(Class clazz) {
        Criteria criteria = super.createCriteria(clazz);

        // eager loading
        criteria.createCriteria("client", "client");

        // security
        criteria.createCriteria("company", "company");
        criteria.createAlias("company.permissions", "permissions");
        criteria.add(Restrictions.eq("permissions.manager", Helper.getLoggedManager()));
        criteria.add(Restrictions.eq("permissions.roleInvoices", true));

        // common
        criteria.add(Restrictions.eq("deleted", false));

        return criteria;
    }
}