package me.kolinger.pro3.invoices.model.impl.services;

import me.kolinger.pro3.invoices.model.AbstractService;
import me.kolinger.pro3.invoices.model.impl.dao.ClientsDao;
import me.kolinger.pro3.invoices.model.impl.entities.Client;
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
public class ClientsService extends AbstractService<Client> {

    private ClientsDao dao;

    public ClientsService() {
        super();
    }

    @Autowired
    public ClientsService(ClientsDao dao) {
        super(dao);
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public List<Client> findByCompany(Company company) {
        return dao.findByCompany(company);
    }
}