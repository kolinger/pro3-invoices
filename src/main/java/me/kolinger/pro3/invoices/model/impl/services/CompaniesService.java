package me.kolinger.pro3.invoices.model.impl.services;

import me.kolinger.pro3.invoices.model.AbstractService;
import me.kolinger.pro3.invoices.model.impl.dao.CompaniesDao;
import me.kolinger.pro3.invoices.model.impl.entities.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Transactional
@Service
public class CompaniesService extends AbstractService<Company> {

    private CompaniesDao dao;

    public CompaniesService() {
        super();
    }

    @Autowired
    public CompaniesService(CompaniesDao dao) {
        super(dao);
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public List<Company> findAll(String permission) {
        return dao.findAll(permission);
    }
}