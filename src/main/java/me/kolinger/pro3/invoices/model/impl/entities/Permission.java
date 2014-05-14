package me.kolinger.pro3.invoices.model.impl.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Entity
@Table(name = "permissions")
public class Permission implements Serializable {

    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Company company;

    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Manager manager;

    @Column(nullable = false)
    private Boolean roleCompany = false;

    @Column(nullable = false)
    private Boolean rolePermissions = false;

    @Column(nullable = false)
    private Boolean roleClients = false;

    @Column(nullable = false)
    private Boolean roleInvoices = false;

    @Column(nullable = false)
    private Boolean roleProducts = false;

    @Column(nullable = false)
    private Boolean rolePayments = false;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permission that = (Permission) o;

        return !(company != null ? !company.equals(that.company) : that.company != null) &&
                !(manager != null ? !manager.equals(that.manager) : that.manager != null);
    }

    @Override
    public int hashCode() {
        int result = company != null ? company.hashCode() : 0;
        result = 31 * result + (manager != null ? manager.hashCode() : 0);
        return result;
    }
}