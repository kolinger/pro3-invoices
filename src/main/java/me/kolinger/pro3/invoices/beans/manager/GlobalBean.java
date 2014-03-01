package me.kolinger.pro3.invoices.beans.manager;


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
@Component("protectedGlobalBean")
public class GlobalBean extends AbstractBean {

    public MenuModel getMenuModel() {
        return createMenuModel();
    }

    private MenuModel createMenuModel() {
        MenuModel menu = new DefaultMenuModel();
        MenuItem item;

        item = new MenuItem();
        item.setValue(Translator.translate("protected.companies.navigation_title"));
        item.setUrl("/protected/companies/");
        menu.addMenuItem(item);

        item = new MenuItem();
        item.setValue(Translator.translate("protected.permissions.navigation_title"));
        item.setUrl("/protected/permissions/");
        menu.addMenuItem(item);

        item = new MenuItem();
        item.setValue(Translator.translate("protected.clients.navigation_title"));
        item.setUrl("/protected/clients/");
        menu.addMenuItem(item);

        item = new MenuItem();
        item.setValue(Translator.translate("protected.invoices.navigation_title"));
        item.setUrl("/protected/invoices/");
        menu.addMenuItem(item);

        item = new MenuItem();
        item.setValue(Translator.translate("protected.products.navigation_title"));
        item.setUrl("/protected/products/");
        menu.addMenuItem(item);

        item = new MenuItem();
        item.setValue(Translator.translate("protected.payments.navigation_title"));
        item.setUrl("/protected/payments/");
        menu.addMenuItem(item);

        return menu;
    }
}