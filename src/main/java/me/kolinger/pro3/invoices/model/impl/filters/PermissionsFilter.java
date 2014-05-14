package me.kolinger.pro3.invoices.model.impl.filters;

import me.kolinger.pro3.invoices.model.filter.AbstractFilter;
import me.kolinger.pro3.invoices.model.filter.Expression;
import me.kolinger.pro3.invoices.model.filter.Property;
import me.kolinger.pro3.invoices.model.impl.entities.Company;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class PermissionsFilter extends AbstractFilter {

    @Property(name = "company", expression = Expression.EQUAL)
    private Company company;

    @Property(name = "manager.username", expression = Expression.FULLTEXT)
    private String manager;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
