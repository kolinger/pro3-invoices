package me.kolinger.pro3.invoices.beans.manager;

import me.kolinger.pro3.invoices.beans.CrudBean;
import me.kolinger.pro3.invoices.common.Translator;
import me.kolinger.pro3.invoices.model.impl.entities.Company;
import me.kolinger.pro3.invoices.model.impl.entities.Manager;
import me.kolinger.pro3.invoices.model.impl.entities.Permission;
import me.kolinger.pro3.invoices.model.impl.services.CompaniesService;
import me.kolinger.pro3.invoices.model.impl.services.ManagersService;
import me.kolinger.pro3.invoices.model.impl.services.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.util.List;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Scope("session")
@Component
public class PermissionsBean extends CrudBean<Permission> {

    @Autowired
    public CompaniesService companiesService;

    @Autowired
    public ManagersService managersService;

    private PermissionsService service;
    private String managerUsername;

    @Autowired
    public PermissionsBean(PermissionsService service) {
        super(service);
        this.service = service;
    }

    public String getManagerUsername() {
        return managerUsername;
    }

    public void setManagerUsername(String managerUsername) {
        this.managerUsername = managerUsername;
    }

    public List<Company> getCompanies() {
        return companiesService.findAll();
    }

    public void managerValidator(FacesContext context, UIComponent toValidate, Object value) {
        if (value == null) {
            return;
        }
        String username = value.toString();
        Manager manager = managersService.findOneByUsername(username);
        if (manager == null) {
            sendValidationError(Translator.translate("protected.permissions.manager.error_not_found"));
        } else {
            getEntity().setManager(manager);
        }
    }
}