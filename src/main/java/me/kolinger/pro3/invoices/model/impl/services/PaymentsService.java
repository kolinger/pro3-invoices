package me.kolinger.pro3.invoices.model.impl.services;

import me.kolinger.pro3.invoices.model.AbstractService;
import me.kolinger.pro3.invoices.model.impl.dao.PaymentsDao;
import me.kolinger.pro3.invoices.model.impl.entities.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Transactional
@Service
public class PaymentsService extends AbstractService<Payment> {

    private PaymentsDao dao;

    public PaymentsService() {
        super();
    }

    @Autowired
    public PaymentsService(PaymentsDao dao) {
        super(dao);
        this.dao = dao;
    }
}