package me.kolinger.pro3.invoices.beans.manager;

import me.kolinger.pro3.invoices.beans.CrudBean;
import me.kolinger.pro3.invoices.model.impl.entities.Company;
import me.kolinger.pro3.invoices.model.impl.entities.Product;
import me.kolinger.pro3.invoices.model.impl.services.CompaniesService;
import me.kolinger.pro3.invoices.model.impl.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Scope("session")
@Component
public class ProductsBean extends CrudBean<Product> {

    @Autowired
    public CompaniesService companiesService;

    @Autowired
    public ProductsBean(ProductsService service) {
        super(service);
    }

    public List<Company> getCompanies() {
        return companiesService.findAll("roleProducts");
    }
}