package me.kolinger.pro3.invoices.beans.manager;

import me.kolinger.pro3.invoices.beans.FilteredCrudBean;
import me.kolinger.pro3.invoices.model.impl.entities.Company;
import me.kolinger.pro3.invoices.model.impl.entities.Product;
import me.kolinger.pro3.invoices.model.impl.filters.ProductsFilter;
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
public class ProductsBean extends FilteredCrudBean<Product, ProductsFilter> {

    @Autowired
    public CompaniesService companiesService;

    @Autowired
    public ProductsBean(ProductsService service) {
        super(service);
        setFilter(new ProductsFilter());
    }

    public List<Company> getCompanies() {
        return companiesService.findAll("roleProducts");
    }
}