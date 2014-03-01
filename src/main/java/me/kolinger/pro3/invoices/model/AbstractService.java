package me.kolinger.pro3.invoices.model;

import me.kolinger.pro3.invoices.model.impl.entities.Invoice;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import org.springframework.transaction.annotation.Transactional;
import me.kolinger.pro3.invoices.common.LoggedObject;
import me.kolinger.pro3.invoices.model.filter.AbstractFilter;

import java.util.List;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Transactional
public abstract class AbstractService<T> extends LoggedObject {

    private AbstractDao<T> dao;
    private AbstractFilter filter = null;

    public AbstractService() {
    }

    public AbstractService(AbstractDao<T> dao) {
        this.dao = dao;
    }

    @Transactional
    public void save(T entity) {
        dao.save(entity);
    }

    @Transactional
    public void delete(T entity) {
        dao.delete(entity);
    }

    @Transactional
    public T createNew() {
        return dao.createNew();
    }

    @Transactional(readOnly = true)
    public T findOneById(Long id) {
        return dao.findOneById(id);
    }

    @Transactional(readOnly = true)
    public List<T> findAll() {
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    public List<T> findAll(int first, int pageSize, String sortField, SortOrder sortOrder, AbstractFilter filter) {
        return dao.findAll(first, pageSize, sortField, sortOrder, filter);
    }

    @Transactional(readOnly = true)
    public List<T> findAll(int first, int pageSize, List<SortMeta> multiSortMeta, AbstractFilter filter) {
        return dao.findAll(first, pageSize, multiSortMeta, filter);
    }

    @Transactional(readOnly = true)
    public Integer count(AbstractFilter filter) {
        return dao.count(filter);
    }
}
