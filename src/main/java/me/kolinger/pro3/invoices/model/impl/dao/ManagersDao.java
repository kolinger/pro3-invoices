package me.kolinger.pro3.invoices.model.impl.dao;

import me.kolinger.pro3.invoices.model.DeletableDao;
import me.kolinger.pro3.invoices.model.impl.entities.Manager;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Repository
public class ManagersDao extends DeletableDao<Manager> {

    public ManagersDao() {
        super(Manager.class);
    }

    public Manager findOneByUsername(String username) {
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq("username", username));
        return (Manager) criteria.uniqueResult();
    }
}