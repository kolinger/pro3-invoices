package me.kolinger.pro3.invoices.model.impl.filters;

import me.kolinger.pro3.invoices.model.filter.AbstractFilter;
import me.kolinger.pro3.invoices.model.filter.Expression;
import me.kolinger.pro3.invoices.model.filter.Property;
import me.kolinger.pro3.invoices.model.impl.entities.Company;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class ProductsFilter extends AbstractFilter {

    @Property(name = "company", expression = Expression.EQUAL)
    private Company company;

    @Property(name = "name", expression = Expression.FULLTEXT)
    private String name;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
