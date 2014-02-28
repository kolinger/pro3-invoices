package me.kolinger.pro3.invoices.beans.outside;


import me.kolinger.pro3.invoices.beans.AbstractBean;
import me.kolinger.pro3.invoices.common.Translator;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Scope("session")
@Component("publicGlobalBean")
public class GlobalBean extends AbstractBean {

    public MenuModel getMenuModel() {
        return createMenuModel();
    }

    private MenuModel createMenuModel() {
        MenuModel menu = new DefaultMenuModel();
        MenuItem item;

        item = new MenuItem();
        item.setValue(Translator.translate("public.index.navigation_title"));
        item.setUrl("/");
        menu.addMenuItem(item);

        item = new MenuItem();
        item.setValue(Translator.translate("public.registration.navigation_title"));
        item.setUrl("/registration/");
        menu.addMenuItem(item);

        item = new MenuItem();
        item.setValue(Translator.translate("public.login.navigation_title"));
        item.setUrl("/login/");
        menu.addMenuItem(item);

        return menu;
    }
}