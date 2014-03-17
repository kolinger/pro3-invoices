package me.kolinger.pro3.invoices.handlers;

import me.kolinger.pro3.invoices.common.LoggedObject;
import me.kolinger.pro3.invoices.model.impl.InvoiceReport;
import me.kolinger.pro3.invoices.model.impl.entities.Invoice;
import me.kolinger.pro3.invoices.model.impl.services.InvoicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Component
public class InvoiceReportHandler extends LoggedObject implements HttpRequestHandler {

    @Autowired
    private InvoicesService invoicesService;

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String[] parts = uri.split("/");
        try {
            Long id = Long.valueOf(parts[parts.length - 2]);
            String type = parts[parts.length - 1];

            Invoice invoice = invoicesService.findOneByIdWithProducts(id);
            if (invoice == null) {
                getLogger().info("Invoice report handler - requested invoice not exists");
                return;
            }

            InputStream template = this.getClass().getClassLoader().getResourceAsStream("invoice.jasper");
            if (type.equals("html")) {
                response.addHeader("Content-disposition", "attachment; filename=" + invoice.getId() + ".html");
                InvoiceReport report = new InvoiceReport(template, invoice);
                report.reportToHtml(response.getOutputStream());
            } else if (type.equals("xlsx")) {
                response.addHeader("Content-disposition", "attachment; filename=" + invoice.getId() + ".xlsx");
                InvoiceReport report = new InvoiceReport(template, invoice);
                report.reportToXlsx(response.getOutputStream());
            } else if (type.equals("pdf")) {
                response.addHeader("Content-disposition", "attachment; filename=" + invoice.getId() + ".pdf");
                InvoiceReport report = new InvoiceReport(template, invoice);
                report.reportToPdf(response.getOutputStream());
            }
        } catch (Exception e) {
            getLogger().warn("Invoice report handler failed", e);
        }
    }
}