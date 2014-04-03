package me.kolinger.pro3.invoices.model.impl.filters;

import me.kolinger.pro3.invoices.model.filter.AbstractFilter;
import me.kolinger.pro3.invoices.model.filter.Expression;
import me.kolinger.pro3.invoices.model.filter.Property;
import me.kolinger.pro3.invoices.model.impl.entities.Company;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class PaymentsFilter extends AbstractFilter {

    @Property(name = "invoice.company", expression = Expression.EQUAL)
    private Company company;

    @Property(name = "invoice.id", expression = Expression.EQUAL)
    private Long variableSymbol;

    @Property(name = "client.name", expression = Expression.LIKE)
    private String client;

    @Property(name = "amount", expression = Expression.EQUAL)
    private BigDecimal amount;

    @Property(name = "date", expression = Expression.GREATER_OR_EQUAL)
    private Date dateFrom;

    @Property(name = "date", expression = Expression.LESS_OR_EQUAL)
    private Date dateTo;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Long getVariableSymbol() {
        return variableSymbol;
    }

    public void setVariableSymbol(Long variableSymbol) {
        this.variableSymbol = variableSymbol;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
}
