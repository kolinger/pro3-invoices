package me.kolinger.pro3.invoices.model.impl.dao;

import me.kolinger.pro3.invoices.common.Helper;
import me.kolinger.pro3.invoices.model.DeletableDao;
import me.kolinger.pro3.invoices.model.impl.entities.Company;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Repository
public class CompaniesDao extends DeletableDao<Company> {

    public CompaniesDao() {
        super(Company.class);
    }

    @SuppressWarnings("unchecked")
    public List<Company> findAll(String permissions) {
        Criteria criteria = super.createCriteria(Company.class);
        criteria.createAlias("permissions", "permissions");
        criteria.add(Restrictions.eq("permissions.manager", Helper.getLoggedManager()));
        criteria.add(Restrictions.eq("permissions." + permissions, true));
        criteria.addOrder(Order.asc("name"));
        return (List<Company>) criteria.list();
    }

    @Override
    protected Criteria createCriteria(Class clazz) {
        Criteria criteria = super.createCriteria(clazz);

        // security
        criteria.createAlias("permissions", "permissions");
        criteria.add(Restrictions.eq("permissions.manager", Helper.getLoggedManager()));
        criteria.add(Restrictions.eq("permissions.roleCompany", true));

        return criteria;
    }
}