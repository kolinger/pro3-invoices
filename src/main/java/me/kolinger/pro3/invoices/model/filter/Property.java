package me.kolinger.pro3.invoices.model.filter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Property {

    String name();
    Expression expression() default Expression.EQUAL;
}
