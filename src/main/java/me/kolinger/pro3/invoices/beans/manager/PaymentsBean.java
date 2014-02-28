package me.kolinger.pro3.invoices.beans.manager;

import me.kolinger.pro3.invoices.beans.CrudBean;
import me.kolinger.pro3.invoices.model.impl.entities.Payment;
import me.kolinger.pro3.invoices.model.impl.services.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Scope("session")
@Component
public class PaymentsBean extends CrudBean<Payment> {

    private PaymentsService service;

    @Autowired
    public PaymentsBean(PaymentsService service) {
        super(service);
        this.service = service;
    }

}