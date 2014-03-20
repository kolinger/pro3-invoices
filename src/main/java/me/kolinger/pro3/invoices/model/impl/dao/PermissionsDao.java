package me.kolinger.pro3.invoices.model.impl.dao;

import me.kolinger.pro3.invoices.common.Helper;
import me.kolinger.pro3.invoices.model.AbstractDao;
import me.kolinger.pro3.invoices.model.impl.entities.Permission;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
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

        // eager loading
        criteria.createAlias("company", "company");
        criteria.createAlias("manager", "manager");

        // security
        criteria.createAlias("company.permissions", "permissions");
        criteria.add(Restrictions.eq("permissions.manager", Helper.getLoggedManager()));
        criteria.add(Restrictions.eq("permissions.rolePermissions", true));

        return criteria;
    }
}