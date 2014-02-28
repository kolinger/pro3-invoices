package me.kolinger.pro3.invoices.model.impl.dao;

import me.kolinger.pro3.invoices.model.DeletableDao;
import me.kolinger.pro3.invoices.model.impl.entities.InvoiceProduct;
import org.springframework.stereotype.Repository;


/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Repository
public class InvoiceProductsDao extends DeletableDao<InvoiceProduct> {

    public InvoiceProductsDao() {
        super(InvoiceProduct.class);
    }
}