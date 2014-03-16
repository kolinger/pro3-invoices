package me.kolinger.pro3.invoices.common;

import me.kolinger.pro3.invoices.model.impl.entities.Manager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Some static hell... Its necessary.
 *
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Helper {

    public static Manager getLoggedManager() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return (Manager) authentication.getPrincipal();
    }
}
