package me.kolinger.pro3.invoices.model.impl.dao;

import me.kolinger.pro3.invoices.model.AbstractDao;
import me.kolinger.pro3.invoices.model.impl.entities.Manager;
import me.kolinger.pro3.invoices.model.impl.entities.Permission;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;


/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Repository
public class PermissionsDao extends AbstractDao<Permission> {

    public PermissionsDao() {
        super(Permission.class);
    }

    @Override
    protected Criteria createCriteria(Class clazz) {
        Criteria criteria = super.createCriteria(clazz);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Manager manager = (Manager) authentication.getPrincipal();

        DetachedCriteria permissionsQuery = DetachedCriteria.forClass(Permission.class)
                .add(Restrictions.eq("rolePermissions", true))
                .add(Restrictions.eq("manager", manager))
                .setProjection(Projections.property("company.id"));

        return criteria.add(Property.forName("company.id").in(permissionsQuery));
    }
}