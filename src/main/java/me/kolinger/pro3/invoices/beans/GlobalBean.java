package me.kolinger.pro3.invoices.beans;


import com.ocpsoft.pretty.PrettyContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Scope("session")
@Component
public class GlobalBean extends AbstractBean {

    public String getCurrentViewId() {
        return PrettyContext.getCurrentInstance().getCurrentMapping().getId();
    }
}