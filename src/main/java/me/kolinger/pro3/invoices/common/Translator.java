package me.kolinger.pro3.invoices.common;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Translator extends LoggedObject {

    final private static String BUNDLE_NAME = "me.kolinger.pro3.invoices.messages.Messages";

    private ResourceBundle bundle;
    private static Translator instance;

    public static String translate(String key) {
        return getInstance().translateKey(key);
    }

    private Translator(Locale locale) {
        if (locale == null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            UIViewRoot viewRoot = facesContext.getViewRoot();
            locale = viewRoot.getLocale();
        }
        bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
    }

    public static Translator getInstance() {
        if (instance == null) {
            instance = new Translator(null);
        }
        return instance;
    }

    public static Translator getInstance(Locale locale) {
        if (instance == null) {
            instance = new Translator(locale);
        }
        return instance;
    }

    public String translateKey(String key) {
        try {
            Object value = bundle.getObject(key);
            return (String) value;
        } catch (MissingResourceException e) {
            getLogger().warn("Translating key {} failed", key, e);
        }
        return "???" + key + "???";
    }
}
