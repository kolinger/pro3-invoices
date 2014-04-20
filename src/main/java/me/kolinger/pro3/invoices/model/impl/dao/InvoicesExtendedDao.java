package me.kolinger.pro3.invoices.model.impl.dao;

import me.kolinger.pro3.invoices.common.Helper;
import me.kolinger.pro3.invoices.model.AbstractDao;
import me.kolinger.pro3.invoices.model.filter.AbstractFilter;
import me.kolinger.pro3.invoices.model.impl.entities.InvoiceExtended;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 * @todo split abstract DAO to multiple independent classes and make this DAO read only
 */
@Repository
public class InvoicesExtendedDao extends AbstractDao<InvoiceExtended> {

    public InvoicesExtendedDao() {
        super(InvoiceExtended.class);
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