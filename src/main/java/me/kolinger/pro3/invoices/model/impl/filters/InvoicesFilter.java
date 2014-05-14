package me.kolinger.pro3.invoices.model.impl.filters;

import me.kolinger.pro3.invoices.model.filter.AbstractFilter;
import me.kolinger.pro3.invoices.model.filter.Expression;
import me.kolinger.pro3.invoices.model.filter.Property;
import me.kolinger.pro3.invoices.model.impl.entities.Client;
import me.kolinger.pro3.invoices.model.impl.entities.Company;
import me.kolinger.pro3.invoices.model.impl.entities.Invoice;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class InvoicesFilter extends AbstractFilter {

    @Property(name = "company", expression = Expression.EQUAL)
    private Company company;

    @Property(name = "id", expression = Expression.EQUAL)
    private Long id;

    @Property(name = "client.name", expression = Expression.FULLTEXT)
    private String client;

    @Property(name = "type", expression = Expression.EQUAL)
    private Invoice.Type type;

    @Property(name = "amount", expression = Expression.EQUAL)
    private BigDecimal amount;

    @Property(name = "payed", expression = Expression.EQUAL)
    private BigDecimal paid;

    @Property(name = "state", expression = Expression.CRITERIA)
    private State state;

    @Property(name = "createDate", expression = Expression.GREATER_OR_EQUAL)
    private Date createDateFrom;

    @Property(name = "createDate", expression = Expression.LESS_OR_EQUAL)
    private Date createDateTo;

    @Property(name = "endDate", expression = Expression.GREATER_OR_EQUAL)
    private Date endDateFrom;

    @Property(name = "endDate", expression = Expression.LESS_OR_EQUAL)
    private Date endDateTo;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Invoice.Type getType() {
        return type;
    }

    public void setType(Invoice.Type type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPaid() {
        return paid;
    }

    public void setPaid(BigDecimal paid) {
        this.paid = paid;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Date getCreateDateFrom() {
        return createDateFrom;
    }

    public void setCreateDateFrom(Date createDateFrom) {
        this.createDateFrom = createDateFrom;
    }

    public Date getCreateDateTo() {
        return createDateTo;
    }

    public void setCreateDateTo(Date createDateTo) {
        this.createDateTo = createDateTo;
    }

    public Date getEndDateFrom() {
        return endDateFrom;
    }

    public void setEndDateFrom(Date endDateFrom) {
        this.endDateFrom = endDateFrom;
    }

    public Date getEndDateTo() {
        return endDateTo;
    }

    public void setEndDateTo(Date endDateTo) {
        this.endDateTo = endDateTo;
    }

    public void applyStateCriteria(Criteria criteria, Object state) {
        if (state == null) {
            return;
        }

        if (state.equals(State.PAID)) {
            criteria.add(Restrictions.leProperty("amount", "payed"));
        } else {
            criteria.add(Restrictions.gtProperty("amount", "payed"));
        }
    }

    public enum State {
        PAID,
        UNPAID,
    }
}
