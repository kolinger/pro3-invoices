package me.kolinger.pro3.invoices.beans.manager;

import me.kolinger.pro3.invoices.beans.CrudBean;
import me.kolinger.pro3.invoices.model.impl.entities.Product;
import me.kolinger.pro3.invoices.model.impl.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Scope("session")
@Component
public class ProductsBean extends CrudBean<Product> {

    private ProductsService service;

    @Autowired
    public ProductsBean(ProductsService service) {
        super(service);
        this.service = service;
    }

}