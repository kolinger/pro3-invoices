package me.kolinger.pro3.invoices.model.hibernate.postgresql;

import org.hibernate.dialect.PostgreSQL82Dialect;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Dialect extends PostgreSQL82Dialect {

    public Dialect() {
        super();
        registerFunction("fts", new FullTextSearchFunction());
    }
}
