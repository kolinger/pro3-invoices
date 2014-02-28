package me.kolinger.pro3.invoices.security;

import me.kolinger.pro3.invoices.common.LoggedObject;
import me.kolinger.pro3.invoices.common.Translator;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import java.util.Map;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class LoginErrorPhaseListener extends LoggedObject implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent phaseEvent) {
        // skip
    }

    @Override
    public void beforePhase(PhaseEvent phaseEvent) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();

        Exception e = (Exception) sessionMap.get(WebAttributes.AUTHENTICATION_EXCEPTION);

        if (e instanceof BadCredentialsException) {
            sendErrorMessage("login.wrong_credentials");
        } else if (e != null) {
            sendErrorMessage("login.unknown_error");
        }

        sessionMap.put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

    private void sendErrorMessage(String string) {
        FacesMessage message = new FacesMessage(
                FacesMessage.SEVERITY_ERROR,
                Translator.translate(string),
                null);

        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
