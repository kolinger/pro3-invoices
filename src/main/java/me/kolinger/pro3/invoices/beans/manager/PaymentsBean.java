package me.kolinger.pro3.invoices.beans.manager;

import me.kolinger.pro3.invoices.beans.CrudBean;
import me.kolinger.pro3.invoices.common.Translator;
import me.kolinger.pro3.invoices.model.impl.entities.Invoice;
import me.kolinger.pro3.invoices.model.impl.entities.Manager;
import me.kolinger.pro3.invoices.model.impl.entities.Payment;
import me.kolinger.pro3.invoices.model.impl.services.InvoicesService;
import me.kolinger.pro3.invoices.model.impl.services.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Scope("session")
@Component
public class PaymentsBean extends CrudBean<Payment> {

    @Autowired
    public InvoicesService invoicesService;

    private PaymentsService service;
    private Long id;

    @Autowired
    public PaymentsBean(PaymentsService service) {
        super(service);
        this.service = service;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void invoiceValidator(FacesContext context, UIComponent toValidate, Object value) {
        if (value == null) {
            return;
        }
        String username = value.toString();
        Invoice invoice = invoicesService.findOneById(id);
        if (invoice == null) {
            sendValidationError(Translator.translate("protected.payments.id.error_not_found"));
        } else {
            getEntity().setInvoice(invoice);
        }
    }
}