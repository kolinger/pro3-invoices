package me.kolinger.pro3.invoices.beans.manager;

import me.kolinger.pro3.invoices.beans.CrudBean;
import me.kolinger.pro3.invoices.common.Translator;
import me.kolinger.pro3.invoices.model.impl.entities.Client;
import me.kolinger.pro3.invoices.model.impl.entities.Company;
import me.kolinger.pro3.invoices.model.impl.entities.Invoice;
import me.kolinger.pro3.invoices.model.impl.entities.InvoiceProduct;
import me.kolinger.pro3.invoices.model.impl.entities.Product;
import me.kolinger.pro3.invoices.model.impl.services.ClientsService;
import me.kolinger.pro3.invoices.model.impl.services.CompaniesService;
import me.kolinger.pro3.invoices.model.impl.services.InvoicesService;
import me.kolinger.pro3.invoices.model.impl.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Scope("session")
@Component
public class InvoicesBean extends CrudBean<Invoice> {

    @Autowired
    public ProductsService productsService;

    @Autowired
    public CompaniesService companiesService;

    @Autowired
    public ClientsService clientsService;

    private InvoicesService service;
    private Product product;

    @Autowired
    public InvoicesBean(InvoicesService service) {
        super(service);
        this.service = service;
    }

    public List<Company> getCompanies() {
        return companiesService.findAll();
    }

    public List<Client> getClients() {
        return clientsService.findAll();
    }

    public List<Product> getProducts() {
        return productsService.findAll();
    }

    public String[][] getTypes() {
        return new String[][]{
                new String[]{Invoice.Type.INVOICE.toString(),
                        Translator.translate("protected.invoices.type.invoice")},
                
                new String[]{Invoice.Type.CREDIT_NOTE.toString(),
                        Translator.translate("protected.invoices.type.credit_note")},
        };
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
}