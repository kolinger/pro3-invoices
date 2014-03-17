package me.kolinger.pro3.invoices.model.impl;

import me.kolinger.pro3.invoices.common.LoggedObject;
import me.kolinger.pro3.invoices.common.Translator;
import me.kolinger.pro3.invoices.model.impl.entities.Invoice;
import me.kolinger.pro3.invoices.model.impl.entities.InvoiceProduct;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class InvoiceReport extends LoggedObject {

    private InputStream template;
    private Invoice invoice;

    public InvoiceReport(InputStream template, Invoice invoice) {
        this.template = template;
        this.invoice = invoice;
    }

    private JRDataSource createDataSource() throws JRException {
        List<Product> products = new ArrayList<Product>();
        for (InvoiceProduct invoiceProduct : invoice.getProducts()) {
            BigDecimal tax = invoiceProduct.getPrice()
                    .divide(new BigDecimal(100))
                    .multiply(new BigDecimal(invoiceProduct.getTax()));

            Product product = new Product();
            product.setId(invoiceProduct.getProduct().getId());
            product.setName(invoiceProduct.getProduct().getName());
            product.setCount(invoiceProduct.getCount());
            product.setPrice(invoiceProduct.getPrice().setScale(2));
            product.setTax(tax.setScale(2));
            product.setTaxPercent(invoiceProduct.getTax());
            product.setSum(invoiceProduct.getPrice().add(tax).multiply(new BigDecimal(invoiceProduct.getCount())));
            product.setTaxPercent(invoiceProduct.getWarranty());
            product.setWarranty(invoiceProduct.getWarranty());
            products.add(product);
        }
        return new JRBeanCollectionDataSource(products);
    }

    public Map<String, Object> createParameters() {
        Translator translator = Translator.getInstance(new Locale("cs", "CZ"));
        Map<String, Object> parameters = new HashMap<String, Object>();

        String title = (invoice.getType().equals(Invoice.Type.CREDIT_NOTE) ?
                translator.translateKey("protected.invoices.type.credit_note") :
                translator.translateKey("protected.invoices.type.invoice")) + " " + invoice.getId();

        // layout
        parameters.put("title", title);

        // seller side
        parameters.put("company_name_label", translator.translateKey("protected.invoices.report.seller"));
        parameters.put("company_name_value", invoice.getCompany().getName());

        parameters.put("company_address_label", translator.translateKey("protected.invoices.report.address"));
        parameters.put("company_address_value", invoice.getCompany().getStreet() + "\n" +
                invoice.getCompany().getZip() + " " + invoice.getCompany().getCity());

        parameters.put("company_in_label", translator.translateKey("protected.invoices.report.in"));
        parameters.put("company_in_value", invoice.getCompany().getCompanyIn());

        parameters.put("company_vat_id_label", translator.translateKey("protected.invoices.report.vat_id"));
        parameters.put("company_vat_id_value", invoice.getCompany().getVatId());

        parameters.put("company_register_value", invoice.getCompany().getTradeRegister());

        parameters.put("company_email_label", translator.translateKey("protected.invoices.report.email"));
        parameters.put("company_email_value", invoice.getCompany().getEmail());

        parameters.put("company_phone_label", translator.translateKey("protected.invoices.report.phone"));
        parameters.put("company_phone_value", invoice.getCompany().getPhone());

        parameters.put("company_website_label", translator.translateKey("protected.invoices.report.website"));
        parameters.put("company_website_value", invoice.getCompany().getWebsite());

        // payment info
        parameters.put("bank_account_label", translator.translateKey("protected.invoices.report.bank_account"));
        parameters.put("bank_account_value", invoice.getCompany().getBankAccount());

        parameters.put("id_label", translator.translateKey("protected.invoices.report.id"));
        parameters.put("id_value", invoice.getId());

        // dates
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        parameters.put("date_created_label", translator.translateKey("protected.invoices.report.date_created"));
        parameters.put("date_created_value", format.format(invoice.getCreateDate()));

        parameters.put("date_end_label", translator.translateKey("protected.invoices.report.date_end"));
        parameters.put("date_end_value", format.format(invoice.getEndDate()));

        // buyer side
        parameters.put("buyer_name_label", translator.translateKey("protected.invoices.report.buyer"));
        parameters.put("buyer_name_value", invoice.getClient().getName());

        parameters.put("buyer_address_label", translator.translateKey("protected.invoices.report.address"));
        parameters.put("buyer_address_value", invoice.getClient().getStreet() + "\n" +
                invoice.getClient().getZip() + " " + invoice.getClient().getCity());

        parameters.put("buyer_in_label", translator.translateKey("protected.invoices.report.in"));
        parameters.put("buyer_in_value", invoice.getClient().getCompanyIn());

        parameters.put("buyer_vat_id_label", translator.translateKey("protected.invoices.report.vat_id"));
        parameters.put("buyer_vat_id_value", invoice.getClient().getVatId());

        parameters.put("buyer_email_label", translator.translateKey("protected.invoices.report.email"));
        parameters.put("buyer_email_value", invoice.getClient().getEmail());

        parameters.put("buyer_phone_label", translator.translateKey("protected.invoices.report.phone"));
        parameters.put("buyer_phone_value", invoice.getClient().getPhone());

        // columns
        parameters.put("code_label", translator.translateKey("protected.invoices.report.code"));
        parameters.put("name_label", translator.translateKey("protected.invoices.report.name"));
        parameters.put("price_label", translator.translateKey("protected.invoices.report.price"));
        parameters.put("count_label", translator.translateKey("protected.invoices.report.count"));
        parameters.put("tax_label", translator.translateKey("protected.invoices.report.tax"));
        parameters.put("warranty_label", translator.translateKey("protected.invoices.report.warranty"));
        parameters.put("tax_percent_label", translator.translateKey("protected.invoices.report.tax_percent"));
        parameters.put("price_sum_label", translator.translateKey("protected.invoices.report.sum"));

        // under line
        parameters.put("total_sum_label", translator.translateKey("protected.invoices.report.total_sum"));
        parameters.put("round_label", translator.translateKey("protected.invoices.report.round"));
        parameters.put("final_sum_label", translator.translateKey("protected.invoices.report.final_sum"));

        return parameters;
    }

    public void reportToPdf(OutputStream outputStream) {
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(template, createParameters(), createDataSource());
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } catch (Exception e) {
            getLogger().error("Invoice report failed!", e);
        }
    }

    public void reportToHtml(OutputStream outputStream) {
        try {
            Map<String, Object> parameters = createParameters();
            parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);
            JasperPrint jasperPrint = JasperFillManager.fillReport(template, parameters, createDataSource());

            JRHtmlExporter exporter = new JRHtmlExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, false);
            exporter.exportReport();
        } catch (Exception e) {
            getLogger().error("Invoice report failed!", e);
        }
    }

    public void reportToXlsx(OutputStream outputStream) {
        try {
            Map<String, Object> parameters = createParameters();
            parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);
            JasperPrint jasperPrint = JasperFillManager.fillReport(template, parameters, createDataSource());

            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            exporter.exportReport();
        } catch (Exception e) {
            getLogger().error("Invoice report failed!", e);
        }
    }

    public class Product {
        private Long id;
        private String name;
        private Integer count;
        private BigDecimal price;
        private BigDecimal tax;
        private Integer taxPercent;
        private BigDecimal sum;
        private Integer warranty;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public BigDecimal getTax() {
            return tax;
        }

        public void setTax(BigDecimal tax) {
            this.tax = tax;
        }

        public Integer getTaxPercent() {
            return taxPercent;
        }

        public void setTaxPercent(Integer taxPercent) {
            this.taxPercent = taxPercent;
        }

        public BigDecimal getSum() {
            return sum;
        }

        public void setSum(BigDecimal sum) {
            this.sum = sum;
        }

        public Integer getWarranty() {
            return warranty;
        }

        public void setWarranty(Integer warranty) {
            this.warranty = warranty;
        }
    }
}
