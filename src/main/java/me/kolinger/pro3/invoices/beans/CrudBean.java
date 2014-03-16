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
    private Boolean addDialogDisplayed = false;
    private Boolean editDialogDisplayed = false;
    private Boolean deleteDialogDisplayed = false;

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

    public Boolean getAddDialogDisplayed() {
        return addDialogDisplayed;
    }

    public Boolean getEditDialogDisplayed() {
        return editDialogDisplayed;
    }

    public Boolean getDeleteDialogDisplayed() {
        return deleteDialogDisplayed;
    }

    public void showAddDialog() {
        addDialogDisplayed = true;
        entity = service.createNew();
    }

    public void hideAddDialog() {
        addDialogDisplayed = false;
    }

    public void showEditDialog(T entity) {
        editDialogDisplayed = true;
        this.entity = entity;
    }

    public void hideEditDialog() {
        editDialogDisplayed = false;
    }

    public void showDeleteDialog(T entity) {
        deleteDialogDisplayed = true;
        this.entity = entity;
    }

    public void hideDeleteDialog() {
        deleteDialogDisplayed = false;
    }

    public void saveEntity() {
        editDialogDisplayed = false;
        addDialogDisplayed = false;
        service.save(entity);
    }

    public void deleteEntity() {
        service.delete(entity);
    }
}
