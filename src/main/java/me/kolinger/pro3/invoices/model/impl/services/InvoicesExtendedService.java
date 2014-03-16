package me.kolinger.pro3.invoices.model.impl.services;

import me.kolinger.pro3.invoices.model.AbstractService;
import me.kolinger.pro3.invoices.model.impl.dao.InvoicesExtendedDao;
import me.kolinger.pro3.invoices.model.impl.entities.InvoiceExtended;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 * @todo split abstract service to multiple independent classes and make this service read only
 */
@Transactional
@Service
public class InvoicesExtendedService extends AbstractService<InvoiceExtended> {

    public InvoicesExtendedService() {
        super();
    }

    @Autowired
    public InvoicesExtendedService(InvoicesExtendedDao dao) {
        super(dao);
    }
}