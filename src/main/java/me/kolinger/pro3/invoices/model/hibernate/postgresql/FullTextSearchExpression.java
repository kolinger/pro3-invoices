package me.kolinger.pro3.invoices.model.hibernate.postgresql;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.engine.spi.TypedValue;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class FullTextSearchExpression implements Criterion {

    private String propertyName;
    private Object value;

    public FullTextSearchExpression(String propertyName, Object value) {
        this.propertyName = propertyName;
        this.value = value;
    }

    @Override
    public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
        StringBuilder fragment = new StringBuilder();
        String[] columns = criteriaQuery.findColumns(propertyName, criteria);
        for (int i = 0; i < columns.length; i++) {
            fragment.append("to_tsvector('english'::regconfig, ");
            fragment.append(columns[i]);
            fragment.append(") @@ to_tsquery('english'::regconfig, ");
            fragment.append("?)");
            if (i < columns.length - 1) {
                fragment.append(" and ");
            }
        }
        return fragment.toString();
    }

    @Override
    public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
        return new TypedValue[]{criteriaQuery.getTypedValue(criteria, propertyName, value)};
    }
}
