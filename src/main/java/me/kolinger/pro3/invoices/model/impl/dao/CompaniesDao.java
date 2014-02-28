package me.kolinger.pro3.invoices.model.impl.dao;

import me.kolinger.pro3.invoices.model.DeletableDao;
import me.kolinger.pro3.invoices.model.impl.entities.Company;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;


/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Repository
public class CompaniesDao extends DeletableDao<Company> {

    public CompaniesDao() {
        super(Company.class);
    }

    @Override
    protected Criteria createCriteria() {
        Criteria criteria = super.createCriteria();
        criteria.addOrder(Order.asc("name"));
        return criteria;
    }
}