package me.kolinger.pro3.invoices.model.impl.services;

import me.kolinger.pro3.invoices.model.AbstractService;
import me.kolinger.pro3.invoices.model.impl.dao.ProductsDao;
import me.kolinger.pro3.invoices.model.impl.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Transactional
@Service
public class ProductsService extends AbstractService<Product> {

    private ProductsDao dao;

    public ProductsService() {
        super();
    }

    @Autowired
    public ProductsService(ProductsDao dao) {
        super(dao);
        this.dao = dao;
    }
}