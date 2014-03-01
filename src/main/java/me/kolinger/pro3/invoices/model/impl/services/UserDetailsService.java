package me.kolinger.pro3.invoices.model.impl.services;

import me.kolinger.pro3.invoices.model.impl.dao.ManagersDao;
import me.kolinger.pro3.invoices.model.impl.entities.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Transactional
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private ManagersDao dao;

    @Autowired
    public UserDetailsService(ManagersDao dao) {
        this.dao = dao;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Manager manager = dao.findOneByUsername(username);
        if (manager == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        return manager;
    }
}