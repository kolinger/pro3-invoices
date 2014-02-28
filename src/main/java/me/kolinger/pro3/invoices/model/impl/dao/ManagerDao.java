package me.kolinger.pro3.invoices.model.impl.dao;

import me.kolinger.pro3.invoices.model.DeletableDao;
import me.kolinger.pro3.invoices.model.impl.entities.Manager;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;


/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Repository
public class ManagerDao extends DeletableDao<Manager> {

    public ManagerDao() {
        super(Manager.class);
    }

    public Manager findOneByUsername(String username) {
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq("username", username));
        return (Manager) criteria.uniqueResult();
    }

    public Manager getLoggedManager() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        User user = (User) authentication.getPrincipal();
        return findOneByUsername(user.getUsername());
    }
}