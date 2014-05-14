package me.kolinger.pro3.invoices.model.hibernate;

import me.kolinger.pro3.invoices.model.hibernate.postgresql.FullTextSearchExpression;
import org.hibernate.criterion.Criterion;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class ExtendedRestrictions {

    private ExtendedRestrictions() {
    }

    public static Criterion search(String propertyName, Object value) {
        return new FullTextSearchExpression(propertyName, value);
    }
}
