package me.kolinger.pro3.invoices.model.impl.services;

import me.kolinger.pro3.invoices.model.AbstractService;
import me.kolinger.pro3.invoices.model.impl.dao.ManagersDao;
import me.kolinger.pro3.invoices.model.impl.entities.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Transactional
@Service
public class ManagersService extends AbstractService<Manager> {

    private ManagersDao dao;

    public ManagersService() {
        super();
    }

    @Autowired
    public ManagersService(ManagersDao dao) {
        super(dao);
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public Manager findOneByUsername(String username) {
        return dao.findOneByUsername(username);
    }

    @Transactional(readOnly = true)
    public Manager getLoggedManager() {
        return dao.getLoggedManager();
    }
}