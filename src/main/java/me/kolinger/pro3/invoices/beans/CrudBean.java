package me.kolinger.pro3.invoices.beans;

import me.kolinger.pro3.invoices.model.AbstractService;
import me.kolinger.pro3.invoices.model.LazyDataModel;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public abstract class CrudBean<T> extends AbstractBean {

    private LazyDataModel<T> lazyDataModel;
    private AbstractService<T> service;
    private T entity;

    public CrudBean(AbstractService<T> service) {
        this.service = service;
        lazyDataModel = new LazyDataModel<T>(service);
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public LazyDataModel<T> getLazyDataModel() {
        return lazyDataModel;
    }

    public void createNew() {
        entity = service.createNew();
    }

    public void selectEntity(T entity) {
        this.entity = entity;
    }

    public void saveEntity() {
        service.save(entity);
    }

    public void deleteEntity() {
        service.delete(entity);
    }
}
