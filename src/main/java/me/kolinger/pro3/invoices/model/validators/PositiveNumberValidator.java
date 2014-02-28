package me.kolinger.pro3.invoices.model.validators;

import me.kolinger.pro3.invoices.common.Translator;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Component
public class PositiveNumberValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        if (value == null) {
            throwException();
            return; // only for IDE (null pointer checking)
        }
        try {
            Integer number = Integer.valueOf(value.toString());
            if (number < 0) {
                throwException();
            }
        } catch (NumberFormatException e) {
            throwException();
        }
    }

    private void throwException() throws ValidatorException {
        FacesMessage message = new FacesMessage(Translator.translate("number_validator.required_positive_number"));
        throw new ValidatorException(message);
    }
}
