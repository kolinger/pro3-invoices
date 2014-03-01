package me.kolinger.pro3.invoices.model.impl.dao;

import me.kolinger.pro3.invoices.model.DeletableDao;
import me.kolinger.pro3.invoices.model.impl.entities.Manager;
import me.kolinger.pro3.invoices.model.impl.entities.Permission;
import me.kolinger.pro3.invoices.model.impl.entities.Product;
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
public class ProductsDao extends DeletableDao<Product> {

    public ProductsDao() {
        super(Product.class);
    }

    @Override
    protected Criteria createCriteria(Class clazz) {
        Criteria criteria = super.createCriteria(clazz);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Manager manager = (Manager) authentication.getPrincipal();

        DetachedCriteria permissionsQuery = DetachedCriteria.forClass(Permission.class)
                .add(Restrictions.eq("roleProducts", true))
                .add(Restrictions.eq("manager", manager))
                .setProjection(Projections.property("company.id"));

        return criteria.add(Property.forName("company.id").in(permissionsQuery));
    }
}