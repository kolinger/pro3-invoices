package me.kolinger.pro3.invoices.beans.manager;

import me.kolinger.pro3.invoices.beans.FilteredCrudBean;
import me.kolinger.pro3.invoices.common.Translator;
import me.kolinger.pro3.invoices.model.impl.entities.Company;
import me.kolinger.pro3.invoices.model.impl.entities.Invoice;
import me.kolinger.pro3.invoices.model.impl.entities.Payment;
import me.kolinger.pro3.invoices.model.impl.filters.PaymentsFilter;
import me.kolinger.pro3.invoices.model.impl.services.CompaniesService;
import me.kolinger.pro3.invoices.model.impl.services.InvoicesService;
import me.kolinger.pro3.invoices.model.impl.services.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.util.List;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Scope("session")
@Component
public class PaymentsBean extends FilteredCrudBean<Payment, PaymentsFilter> {

    @Autowired
    public InvoicesService invoicesService;

    @Autowired
    public CompaniesService companiesService;

    private Long id;

    @Autowired
    public PaymentsBean(PaymentsService service) {
        super(service);
        setFilter(new PaymentsFilter());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Company> getCompanies() {
        return companiesService.findAll("rolePayments");
    }

    public void invoiceValidator(FacesContext context, UIComponent toValidate, Object value) {
        if (value == null) {
            return;
        }
        Invoice invoice = invoicesService.findOneById(id);
        if (invoice == null) {
            sendValidationError(Translator.translate("protected.payments.id.error_not_found"));
        } else {
            getEntity().setInvoice(invoice);
        }
    }
}