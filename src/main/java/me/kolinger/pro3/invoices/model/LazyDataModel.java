package me.kolinger.pro3.invoices.model;

import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import me.kolinger.pro3.invoices.model.filter.AbstractFilter;

import java.util.List;
import java.util.Map;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class LazyDataModel<T> extends org.primefaces.model.LazyDataModel<T> {

    private AbstractService<T> service;
    private AbstractFilter filter;

    public LazyDataModel(AbstractService<T> service) {
        super();
        this.service = service;
    }

    public AbstractFilter getFilter() {
        return filter;
    }

    public void setFilter(AbstractFilter filter) {
        this.filter = filter;
    }

    @Override
    public T getRowData(String key) {
        Long id = Long.parseLong(key);
        return service.findOneById(id);
    }

    @Override
    public Object getRowKey(T object) {
        return "id";
    }

    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
        List<T> list = service.findAll(first, pageSize, sortField, sortOrder, filter);
        setRowCount(list.size());
        return list;
    }

    public List<T> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String,String> filters) {
        List<T> list = service.findAll(first, pageSize, multiSortMeta, filter);
        setRowCount(list.size());
        return list;
    }
}
