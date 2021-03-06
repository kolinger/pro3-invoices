package me.kolinger.pro3.invoices.model.impl.entities;

import me.kolinger.pro3.invoices.model.DeletableEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Entity
@Table(name = "payments")
public class Payment extends DeletableEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Invoice invoice;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Date date;

    @Column(columnDefinition = "TEXT")
    private String comment;

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}