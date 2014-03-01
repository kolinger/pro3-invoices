package me.kolinger.pro3.invoices.beans.manager;

import me.kolinger.pro3.invoices.beans.CrudBean;
import me.kolinger.pro3.invoices.model.impl.entities.Company;
import me.kolinger.pro3.invoices.model.impl.entities.Permission;
import me.kolinger.pro3.invoices.model.impl.services.CompaniesService;
import me.kolinger.pro3.invoices.model.impl.services.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Scope("session")
@Component
public class CompaniesBean extends CrudBean<Company> {

    @Autowired
    public PermissionsService permissionsService;

    private CompaniesService service;

    @Autowired
    public CompaniesBean(CompaniesService service) {
        super(service);
        this.service = service;
    }

    @Override
    public void saveEntity() {
        boolean createPermission = getEntity().getId() == null;

        service.save(getEntity());

        if (createPermission) {
            Permission permission = permissionsService.createNew();
            permission.setCompany(getEntity());
            permission.setManager(getLoggedManager());
            permission.setRoleCompany(true);
            permission.setRolePermissions(true);
            permission.setRoleClients(true);
            permission.setRoleInvoices(true);
            permission.setRoleProducts(true);
            permission.setRolePayments(true);
            permissionsService.save(permission);
        }

        cleanEntity();
    }
}