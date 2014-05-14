package me.kolinger.pro3.invoices.model.impl.entities;

import me.kolinger.pro3.invoices.model.DeletableEntity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Entity
@Table(name = "products")
public class Product extends DeletableEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Company company;

    @Column(nullable = false)
    private String name;

    @Column()
    private BigDecimal price;

    @Column()
    private Integer tax;

    @Column()
    private Integer warranty;

    @Column(columnDefinition = "TEXT")
    private String comment;

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

    public Integer getWarranty() {
        return warranty;
    }

    public void setWarranty(Integer warranty) {
        this.warranty = warranty;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}