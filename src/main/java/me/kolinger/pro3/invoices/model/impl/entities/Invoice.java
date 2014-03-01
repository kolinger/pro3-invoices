package me.kolinger.pro3.invoices.model.impl.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Entity
@Table(name = "invoices")
public class Invoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_seq")
    @SequenceGenerator(name = "invoice_seq", sequenceName = "invoice_seq", initialValue = 1000000000, allocationSize = 1)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice")
    private List<InvoiceProduct> products = new ArrayList<InvoiceProduct>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice")
    private List<Payment> payments = new ArrayList<Payment>();

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Company company;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Client client;

    @Column(nullable = false)
    private Type type;

    @Column(nullable = false)
    private Date createDate = new Date();

    @Column(nullable = false)
    private Date endDate = new Date();

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(nullable = false)
    private Boolean deleted = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<InvoiceProduct> getProducts() {
        return products;
    }

    public void setProducts(List<InvoiceProduct> products) {
        this.products = products;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Invoice that = (Invoice) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public enum Type {
        INVOICE,
        CREDIT_NOTE,
    }
}