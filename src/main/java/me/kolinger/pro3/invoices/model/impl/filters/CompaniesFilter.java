package me.kolinger.pro3.invoices.model.impl.filters;

import me.kolinger.pro3.invoices.model.filter.AbstractFilter;
import me.kolinger.pro3.invoices.model.filter.Expression;
import me.kolinger.pro3.invoices.model.filter.Property;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class CompaniesFilter extends AbstractFilter {

    @Property(name = "name", expression = Expression.FULLTEXT)
    private String name;

    @Property(name = "address", expression = Expression.FULLTEXT)
    private String address;

    @Property(name = "companyIn", expression = Expression.LIKE_PARTIAL)
    private String companyIn;

    @Property(name = "vatId", expression = Expression.LIKE_PARTIAL)
    private String vatId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyIn() {
        return companyIn;
    }

    public void setCompanyIn(String companyIn) {
        this.companyIn = companyIn;
    }

    public String getVatId() {
        return vatId;
    }

    public void setVatId(String vatId) {
        this.vatId = vatId;
    }
}
