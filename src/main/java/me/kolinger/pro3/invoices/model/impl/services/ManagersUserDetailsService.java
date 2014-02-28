package me.kolinger.pro3.invoices.model.impl.services;

import me.kolinger.pro3.invoices.common.LoggedObject;
import me.kolinger.pro3.invoices.model.impl.dao.ManagersDao;
import me.kolinger.pro3.invoices.model.impl.entities.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
@Transactional
@Service
public class ManagersUserDetailsService extends LoggedObject implements UserDetailsService {

    @Autowired
    private ManagersDao dao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Manager manager = dao.findOneByUsername(username);
        if (manager == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        Boolean enabled = true;
        if (manager.isDeleted()) {
            enabled = false;
        }

        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_MANAGER";
            }
        });

        return new User(manager.getUsername(), manager.getPassword(), enabled, true, true, true, roles);
    }
}