package me.kolinger.pro3.invoices.model.impl.services;

import me.kolinger.pro3.invoices.model.AbstractService;
import me.kolinger.pro3.invoices.model.impl.dao.InvoicesDao;
import me.kolinger.pro3.invoices.model.impl.entities.Invoice;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Transactional
@Service
public class InvoicesService extends AbstractService<Invoice> {

    private InvoicesDao dao;

    public InvoicesService() {
        super();
    }

    @Autowired
    public InvoicesService(InvoicesDao dao) {
        super(dao);
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public Invoice findOneByIdWithProducts(Long id) {
        Invoice invoice = dao.findOneById(id);
        Hibernate.initialize(invoice.getProducts());
        return invoice;
    }
}