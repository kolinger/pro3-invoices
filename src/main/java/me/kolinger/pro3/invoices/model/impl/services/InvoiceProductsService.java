package me.kolinger.pro3.invoices.model.impl.services;

import me.kolinger.pro3.invoices.model.AbstractService;
import me.kolinger.pro3.invoices.model.impl.dao.InvoiceProductsDao;
import me.kolinger.pro3.invoices.model.impl.entities.InvoiceProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Transactional
@Service
public class InvoiceProductsService extends AbstractService<InvoiceProduct> {

    private InvoiceProductsDao dao;

    public InvoiceProductsService() {
        super();
    }

    @Autowired
    public InvoiceProductsService(InvoiceProductsDao dao) {
        super(dao);
        this.dao = dao;
    }
}