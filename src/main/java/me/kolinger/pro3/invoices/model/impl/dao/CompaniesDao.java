package me.kolinger.pro3.invoices.model.impl.dao;

import me.kolinger.pro3.invoices.model.DeletableDao;
import me.kolinger.pro3.invoices.model.impl.entities.Company;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
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
    public List<Company> findAll() {
        Criteria criteria = createCriteria();
        criteria.addOrder(Order.asc("name"));
        return (List<Company>) criteria.list();
    }
}