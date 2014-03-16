package me.kolinger.pro3.invoices.model.impl.dao;

import me.kolinger.pro3.invoices.common.Helper;
import me.kolinger.pro3.invoices.model.DeletableDao;
import me.kolinger.pro3.invoices.model.impl.entities.Payment;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Repository
public class PaymentsDao extends DeletableDao<Payment> {

    public PaymentsDao() {
        super(Payment.class);
    }

    @Override
    protected Criteria createCriteria(Class clazz) {
        Criteria criteria = super.createCriteria(clazz);

        // eager loading
        criteria.createAlias("invoice", "invoice");
        criteria.createAlias("invoice.company", "company");
        criteria.createAlias("invoice.client", "client");

        // security
        criteria.createAlias("company.permissions", "permissions");
        criteria.add(Restrictions.eq("permissions.manager", Helper.getLoggedManager()));
        criteria.add(Restrictions.eq("permissions.rolePayments", true));

        return criteria;
    }
}