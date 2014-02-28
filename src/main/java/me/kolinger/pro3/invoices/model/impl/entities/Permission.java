package me.kolinger.pro3.invoices.model.impl.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Entity
@Table(name = "permissions")
public class Permission implements Serializable {

    @Id
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Company company;

    @Id
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Manager manager;

    @Column(nullable = false)
    private Boolean roleCompany;

    @Column(nullable = false)
    private Boolean rolePermissions;

    @Column(nullable = false)
    private Boolean roleClients;

    @Column(nullable = false)
    private Boolean roleInvoices;

    @Column(nullable = false)
    private Boolean roleProducts;

    @Column(nullable = false)
    private Boolean rolePayments;

    @Column(nullable = false)
    private Boolean deleted = false;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Boolean getRoleCompany() {
        return roleCompany;
    }

    public void setRoleCompany(Boolean roleCompany) {
        this.roleCompany = roleCompany;
    }

    public Boolean getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(Boolean rolePermissions) {
        this.rolePermissions = rolePermissions;
    }

    public Boolean getRoleClients() {
        return roleClients;
    }

    public void setRoleClients(Boolean roleClients) {
        this.roleClients = roleClients;
    }

    public Boolean getRoleInvoices() {
        return roleInvoices;
    }

    public void setRoleInvoices(Boolean roleInvoices) {
        this.roleInvoices = roleInvoices;
    }

    public Boolean getRoleProducts() {
        return roleProducts;
    }

    public void setRoleProducts(Boolean roleProducts) {
        this.roleProducts = roleProducts;
    }

    public Boolean getRolePayments() {
        return rolePayments;
    }

    public void setRolePayments(Boolean rolePayments) {
        this.rolePayments = rolePayments;
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

        Permission that = (Permission) o;

        return !(company == null || manager == null || !company.equals(that.getCompany()) ||
                !manager.equals(that.getManager()));
    }

    @Override
    public int hashCode() {
        if (company == null || manager == null) {
            return 0;
        }
        String id = company.getId().toString() + manager.getId().toString();
        return id.hashCode();
    }
}