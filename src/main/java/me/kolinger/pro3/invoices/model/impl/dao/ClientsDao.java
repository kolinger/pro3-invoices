package me.kolinger.pro3.invoices.model.impl.dao;

import me.kolinger.pro3.invoices.model.DeletableDao;
import me.kolinger.pro3.invoices.model.impl.entities.Client;
import me.kolinger.pro3.invoices.model.impl.entities.Manager;
import me.kolinger.pro3.invoices.model.impl.entities.Permission;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Manager manager = (Manager) authentication.getPrincipal();

        DetachedCriteria permissionsQuery = DetachedCriteria.forClass(Permission.class)
                .add(Restrictions.eq("roleClients", true))
                .add(Restrictions.eq("manager", manager))
                .setProjection(Projections.property("company.id"));

        return criteria.add(Property.forName("company.id").in(permissionsQuery));
    }
}