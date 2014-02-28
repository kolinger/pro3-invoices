package me.kolinger.pro3.invoices.model.converters;

import me.kolinger.pro3.invoices.common.LoggedObject;
import me.kolinger.pro3.invoices.model.impl.entities.Company;
import me.kolinger.pro3.invoices.model.impl.services.CompaniesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Component
public class CompanyTypeConverter extends LoggedObject implements Converter {

    @Autowired
    private CompaniesService service;

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0 || value.equals("null")) {
            return null;
        }
        Long id = Long.parseLong(value);
        return service.findOneById(id);
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value instanceof Company ? ((Company) value).getId().toString() : null;
    }
}