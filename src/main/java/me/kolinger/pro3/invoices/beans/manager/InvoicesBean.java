package me.kolinger.pro3.invoices.beans.manager;

import me.kolinger.pro3.invoices.beans.FilteredCrudBean;
import me.kolinger.pro3.invoices.common.Translator;
import me.kolinger.pro3.invoices.model.LazyDataModel;
import me.kolinger.pro3.invoices.model.impl.entities.Client;
import me.kolinger.pro3.invoices.model.impl.entities.Company;
import me.kolinger.pro3.invoices.model.impl.entities.Invoice;
import me.kolinger.pro3.invoices.model.impl.entities.InvoiceExtended;
import me.kolinger.pro3.invoices.model.impl.entities.InvoiceProduct;
import me.kolinger.pro3.invoices.model.impl.entities.Product;
import me.kolinger.pro3.invoices.model.impl.filters.InvoicesFilter;
import me.kolinger.pro3.invoices.model.impl.services.ClientsService;
import me.kolinger.pro3.invoices.model.impl.services.CompaniesService;
import me.kolinger.pro3.invoices.model.impl.services.InvoicesExtendedService;
import me.kolinger.pro3.invoices.model.impl.services.InvoicesService;
import me.kolinger.pro3.invoices.model.impl.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Scope("session")
@Component
public class InvoicesBean extends FilteredCrudBean<Invoice, InvoicesFilter> {

    @Autowired
    public ProductsService productsService;

    @Autowired
    public CompaniesService companiesService;

    @Autowired
    public ClientsService clientsService;

    private InvoicesService service;
    private Product product;
    private LazyDataModel<InvoiceExtended> extendedLazyDataModel;
    private List<Company> companies;

    @Autowired
    public InvoicesBean(InvoicesService service, InvoicesExtendedService extendedService) {
        super(service);
        this.service = service;
        extendedLazyDataModel = new LazyDataModel<InvoiceExtended>(extendedService);
        setFilter(new InvoicesFilter());
        extendedLazyDataModel.setFilter(getFilter());
    }

    public LazyDataModel<InvoiceExtended> getExtendedLazyDataModel() {
        return extendedLazyDataModel;
    }

    public void initializeCompanies() {
        companies = companiesService.findAll("roleInvoices");
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public List<Client> getClients() {
        if ((getEntity() == null || getEntity().getCompany() == null) && companies.size() > 0) {
            return clientsService.findByCompany(companies.get(0));
        }
        return clientsService.findByCompany(getEntity().getCompany());
    }

    public String[][] getTypes() {
        return new String[][]{
                new String[]{Invoice.Type.INVOICE.toString(),
                        Translator.translate("protected.invoices.type.invoice")},

                new String[]{Invoice.Type.CREDIT_NOTE.toString(),
                        Translator.translate("protected.invoices.type.credit_note")},
        };
    }

    public String[][] getStates() {
        return new String[][]{
                new String[]{InvoicesFilter.State.PAID.toString(),
                        Translator.translate("protected.invoices.state.paid")},

                new String[]{InvoicesFilter.State.UNPAID.toString(),
                        Translator.translate("protected.invoices.state.unpaid")},
        };
    }

    public void selectEntity(InvoiceExtended invoiceExtended) {
        setEntity(service.findOneByIdWithProducts(invoiceExtended.getId()));
    }

    /**
     * ***************************** products *****************************
     */

    public List<Product> getProducts() {
        return productsService.findAll();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void addProduct() {
        InvoiceProduct invoiceProduct = new InvoiceProduct();
        invoiceProduct.setInvoice(getEntity());
        invoiceProduct.setProduct(product);
        invoiceProduct.setCount(1);
        invoiceProduct.setPrice(product.getPrice());
        invoiceProduct.setTax(product.getTax());
        invoiceProduct.setWarranty(product.getWarranty());
        getEntity().getProducts().add(invoiceProduct);
    }

    public void removeProduct(InvoiceProduct product) {
        getEntity().getProducts().remove(product);
    }


    /**
     * ***************************** helpers *****************************
     */

    public BigDecimal round(BigDecimal number) {
        return number.setScale(2);
    }
}