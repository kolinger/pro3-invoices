package me.kolinger.pro3.invoices.model.impl.filters;

import me.kolinger.pro3.invoices.model.filter.AbstractFilter;
import me.kolinger.pro3.invoices.model.filter.Expression;
import me.kolinger.pro3.invoices.model.filter.Property;
import me.kolinger.pro3.invoices.model.impl.entities.Company;

import java.math.BigDecimal;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class ProductsFilter extends AbstractFilter {

    @Property(name = "company", expression = Expression.EQUAL)
    private Company company;

    @Property(name = "name", expression = Expression.LIKE)
    private String name;

    @Property(name = "price", expression = Expression.EQUAL)
    private BigDecimal price;

    @Property(name = "tax", expression = Expression.EQUAL)
    private Integer tax;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }
}
