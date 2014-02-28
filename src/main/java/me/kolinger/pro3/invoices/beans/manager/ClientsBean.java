package me.kolinger.pro3.invoices.beans.manager;

import me.kolinger.pro3.invoices.beans.CrudBean;
import me.kolinger.pro3.invoices.model.impl.entities.Client;
import me.kolinger.pro3.invoices.model.impl.services.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Scope("session")
@Component
public class ClientsBean extends CrudBean<Client> {

    private ClientsService service;

    @Autowired
    public ClientsBean(ClientsService service) {
        super(service);
        this.service = service;
    }

}