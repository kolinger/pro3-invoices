package me.kolinger.pro3.invoices.model.filter;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import me.kolinger.pro3.invoices.common.LoggedObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public abstract class AbstractFilter extends LoggedObject {

    public void cleanFilters() {
        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                PropertyUtils.setProperty(this, field.getName(), null);
            } catch (Exception e) {
                getLogger().debug("Cannot clean property " + field.getName(), e);
            }
        }
    }

    public void applyFilters(Criteria criteria) {
        Field[] fields = getClass().getDeclaredFields();
        getLogger().debug("filters: " + fields.length);
        for (Field field : fields) {
            Property property = field.getAnnotation(Property.class);
            if (property != null) {
                try {
                    if (getScalarValue(field) == null) {
                        continue;
                    }
                    Criterion criterion = createCriterion(field, property, criteria);
                    if (criterion != null) {
                        criteria.add(criterion);
                    }
                } catch (Exception e) {
                    getLogger().error("Failed to apply filter " + property.name() + " ", e);
                }
            }
        }
    }

    /**
     * ***************************** helpers *****************************
     */

    private Criterion createCriterion(Field field, Property property, Criteria criteria) throws Exception {
        Criterion criterion = null;

        if (property.expression() == Expression.EQUAL) {
            Object value = getScalarValue(field);
            criterion = Restrictions.eq(property.name(), value);

        } else if (property.expression() == Expression.NOT_EQUAL) {
            Object value = getScalarValue(field);
            criterion = Restrictions.not(Restrictions.eq(property.name(), value));

        } else if (property.expression() == Expression.LIKE) {
            Object value = getScalarValue(field);
            criterion = Restrictions.like(property.name(), "%" + value.toString() + "%");

        } else if (property.expression() == Expression.GREATER) {
            Object value = getScalarValue(field);
            criterion = Restrictions.gt(property.name(), value);

        } else if (property.expression() == Expression.GREATER_OR_EQUAL) {
            Object value = getScalarValue(field);
            criterion = Restrictions.ge(property.name(), value);

        } else if (property.expression() == Expression.LESS) {
            Object value = getScalarValue(field);
            criterion = Restrictions.lt(property.name(), value);

        } else if (property.expression() == Expression.LESS_OR_EQUAL) {
            Object value = getScalarValue(field);
            criterion = Restrictions.le(property.name(), value);

        } else if (property.expression() == Expression.IN) {
            Collection value = getCollectionValue(field);
            criterion = Restrictions.in(property.name(), value);

        } else if (property.expression() == Expression.NOT_IN) {
            Collection value = getCollectionValue(field);
            criterion = Restrictions.not(Restrictions.in(property.name(), value));
        } else if (property.expression() == Expression.CRITERIA) {
            Object value = getScalarValue(field);
            String name = "apply" + firstUpper(field.getName()) + "Criteria";
            Method method = getClass().getMethod(name, Criteria.class, String.class);
            method.invoke(this, criteria, value);
        }
        return criterion;
    }

    private Object getScalarValue(Field field) throws Exception {
        return PropertyUtils.getProperty(this, field.getName());
    }

    private Collection getCollectionValue(Field field) throws Exception {
        return (Collection) PropertyUtils.getProperty(this, field.getName());
    }

    private String firstUpper(String string) {
        char[] array = string.toCharArray();
        array[0] = Character.toUpperCase(array[0]);
        return new String(array);
    }
}
