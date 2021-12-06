package com.example.jwtsecureapp.service;

import com.example.jwtsecureapp.dao.AppUserRepository;
import com.example.jwtsecureapp.dao.RoleRepository;
import com.example.jwtsecureapp.domain.AppUser;
import com.example.jwtsecureapp.domain.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AppUserService {
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    Logger logger = LoggerFactory.getLogger(AppUserService.class);


    public AppUser saveAppUser(AppUser appUser) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public void addRoleToAppUser(String username, String roleName) {
        AppUser appUser = getAppUser(username);
        Role role = roleRepository.findRoleByName(roleName);
        //because this class is transactional, we'll save everything to the db
        appUser.getRoles().add(role);
    }

    public AppUser getAppUser(String username) {
        return appUserRepository.findAppUserByUsername(username);
    }

    public List<AppUser> getAppUsers() {
        return appUserRepository.findAll();
    }

}
