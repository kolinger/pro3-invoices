package me.kolinger.pro3.invoices.model.hibernate.postgresql;


import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.BooleanType;
import org.hibernate.type.Type;

import java.util.List;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class FullTextSearchFunction implements SQLFunction {

    @Override
    public Type getReturnType(Type columnType, Mapping mapping) throws QueryException {
        return new BooleanType();
    }

    @Override
    public String render(Type firstArgumentType, List arguments, SessionFactoryImplementor factory) throws QueryException {
        if (arguments.size() != 3) {
            throw new IllegalArgumentException("The function must be passed 3 arguments");
        }

        String ftsConfig = (String) arguments.get(0);
        String field = (String) arguments.get(1);
        String value = (String) arguments.get(2);

        return "to_tsvector(" + ftsConfig + "::regconfig, " + field + ") @@ " + "to_tsquery(" + ftsConfig + ", " + value + ")";
    }

    @Override
    public boolean hasArguments() {
        return true;
    }

    @Override
    public boolean hasParenthesesIfNoArguments() {
        return false;
    }
}

