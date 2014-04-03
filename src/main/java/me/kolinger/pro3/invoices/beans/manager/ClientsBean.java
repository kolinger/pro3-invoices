package me.kolinger.pro3.invoices.beans.manager;

import me.kolinger.pro3.invoices.beans.FilteredCrudBean;
import me.kolinger.pro3.invoices.model.impl.entities.Client;
import me.kolinger.pro3.invoices.model.impl.entities.Company;
import me.kolinger.pro3.invoices.model.impl.filters.ClientsFilter;
import me.kolinger.pro3.invoices.model.impl.services.ClientsService;
import me.kolinger.pro3.invoices.model.impl.services.CompaniesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Scope("session")
@Component
public class ClientsBean extends FilteredCrudBean<Client, ClientsFilter> {

    @Autowired
    public CompaniesService companiesService;

    @Autowired
    public ClientsBean(ClientsService service) {
        super(service);
        setFilter(new ClientsFilter());
    }

    public List<Company> getCompanies() {
        return companiesService.findAll("roleClients");
    }
}