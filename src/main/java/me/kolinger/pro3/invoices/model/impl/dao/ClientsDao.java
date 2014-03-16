package me.kolinger.pro3.invoices.model.impl.dao;

import me.kolinger.pro3.invoices.common.Helper;
import me.kolinger.pro3.invoices.model.DeletableDao;
import me.kolinger.pro3.invoices.model.impl.entities.Client;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Repository
public class ClientsDao extends DeletableDao<Client> {

    public ClientsDao() {
        super(Client.class);
    }

    @SuppressWarnings("unchecked")
    public List<Client> findAll() {
        Criteria criteria = createCriteria();
        criteria.addOrder(Order.asc("name"));
        return (List<Client>) criteria.list();
    }

    @Override
    protected Criteria createCriteria(Class clazz) {
        Criteria criteria = super.createCriteria(clazz);

        // security
        criteria.createAlias("company", "company");
        criteria.createAlias("company.permissions", "permissions");
        criteria.add(Restrictions.eq("permissions.manager", Helper.getLoggedManager()));
        criteria.add(Restrictions.eq("permissions.roleClients", true));

        return criteria;
    }
}