package me.kolinger.pro3.invoices.beans.manager;

import me.kolinger.pro3.invoices.beans.CrudBean;
import me.kolinger.pro3.invoices.model.impl.entities.Permission;
import me.kolinger.pro3.invoices.model.impl.services.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Scope("session")
@Component
public class PermissionsBean extends CrudBean<Permission> {

    private PermissionsService service;

    @Autowired
    public PermissionsBean(PermissionsService service) {
        super(service);
        this.service = service;
    }

}