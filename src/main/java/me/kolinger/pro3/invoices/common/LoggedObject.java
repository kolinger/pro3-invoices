package me.kolinger.pro3.invoices.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public abstract class LoggedObject {

    public Logger getLogger() {
        return LoggerFactory.getLogger(getClass());
    }
}
