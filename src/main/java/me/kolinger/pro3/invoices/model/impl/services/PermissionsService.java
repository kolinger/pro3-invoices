package me.kolinger.pro3.invoices.model.impl.services;

import me.kolinger.pro3.invoices.model.AbstractService;
import me.kolinger.pro3.invoices.model.impl.dao.PermissionsDao;
import me.kolinger.pro3.invoices.model.impl.entities.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Transactional
@Service
public class PermissionsService extends AbstractService<Permission> {

    private PermissionsDao dao;

    public PermissionsService() {
        super();
    }

    @Autowired
    public PermissionsService(PermissionsDao dao) {
        super(dao);
        this.dao = dao;
    }
}