package me.kolinger.pro3.invoices.beans;

import me.kolinger.pro3.invoices.common.LoggedObject;
import me.kolinger.pro3.invoices.model.impl.entities.Manager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public abstract class AbstractBean extends LoggedObject {

    @Value("${date_format}")
    private String dateFormat;

    public String getDateFormat() {
        return dateFormat;
    }

    public Manager getLoggedManager() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return (Manager) authentication.getPrincipal();
    }

    protected boolean isNewRequest() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Boolean getMethod = ((HttpServletRequest) facesContext.getExternalContext().getRequest()).getMethod().equals("GET");
        Boolean ajaxRequest = facesContext.getPartialViewContext().isAjaxRequest();
        Boolean validationFailed = facesContext.isValidationFailed();
        return getMethod && !ajaxRequest && !validationFailed;
    }

    protected String getParameter(String name) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        return request.getParameter(name);
    }

    protected void sendGrowl(String message, FacesMessage.Severity severity) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(null, new FacesMessage(severity, message, null));
    }

    protected void sendInfoGrowl(String message) {
        sendGrowl(message, FacesMessage.SEVERITY_INFO);
    }

    protected void sendWarnGrowl(String message) {
        sendGrowl(message, FacesMessage.SEVERITY_WARN);
    }

    protected void sendErrorGrowl(String message) {
        sendGrowl(message, FacesMessage.SEVERITY_ERROR);
    }

    protected void sendValidationError(String string) {
        FacesMessage message = new FacesMessage(string);
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(message);
    }

    protected void redirect(String path) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        String url = request.getRequestURL().toString().replace(request.getRequestURI().substring(0), "") + request.getContextPath();

        try {
            externalContext.redirect(url + path);
        } catch (IOException e) {
            getLogger().error("Redirect failed", e);
        }
    }
}
