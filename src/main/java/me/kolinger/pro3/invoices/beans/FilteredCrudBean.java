package me.kolinger.pro3.invoices.beans;

import me.kolinger.pro3.invoices.model.AbstractService;
import me.kolinger.pro3.invoices.model.filter.AbstractFilter;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public abstract class FilteredCrudBean<E, F extends AbstractFilter> extends CrudBean<E> {

    private F filter;
    private Boolean state = false;

    public FilteredCrudBean(AbstractService<E> service) {
        super(service);
    }

    public void setFilter(F filter) {
        this.filter = filter;
        getLazyDataModel().setFilter(filter);
    }

    public F getFilter() {
        return filter;
    }

    public Boolean getFilterState() {
        return state;
    }

    public void showFilter() {
        state = true;
    }

    public void cleanFilter() {
        filter.cleanFilters();
        state = false;
    }
}
