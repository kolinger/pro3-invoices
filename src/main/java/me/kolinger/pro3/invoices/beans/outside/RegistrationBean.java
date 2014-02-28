package me.kolinger.pro3.invoices.beans.outside;

import me.kolinger.pro3.invoices.beans.AbstractBean;
import me.kolinger.pro3.invoices.common.Translator;
import me.kolinger.pro3.invoices.model.impl.entities.Manager;
import me.kolinger.pro3.invoices.model.impl.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Scope("session")
@Component
public class RegistrationBean extends AbstractBean {

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public ManagerService managerService;

    private Manager entity;

    public Manager getEntity() {
        return entity;
    }

    public String getPassword() {
        return null;
    }

    public void setPassword(String password) {
        password = passwordEncoder.encode(password);
        entity.setPassword(password);
    }

    public void cleanEntity() {
        entity = new Manager();
    }

    public void initializeEntity() {
        if (isNewRequest()) {
            cleanEntity();
        }
    }

    public void saveEntity() {
        managerService.save(entity);
        cleanEntity();
        redirect("/registration/success/");
    }

    public void uniqueUsernameValidator(FacesContext context, UIComponent toValidate, Object value) {
        if (value == null) {
            return;
        }
        String username = value.toString();
        Manager manager = managerService.findOneByUsername(username);
        if (manager != null) {
            sendValidationError(Translator.translate("public.registration.username.error_already_taken"));
        }
    }
}
