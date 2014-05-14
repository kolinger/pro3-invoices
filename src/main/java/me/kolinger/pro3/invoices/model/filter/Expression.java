package me.kolinger.pro3.invoices.model.filter;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public enum Expression {

    EQUAL,
    NOT_EQUAL,

    LIKE,
    LIKE_PARTIAL,
    FULLTEXT,

    GREATER,
    GREATER_OR_EQUAL,

    LESS,
    LESS_OR_EQUAL,

    IN,
    NOT_IN,

    CRITERIA,
}
