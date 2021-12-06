package com.example.jwtsecureapp.service;

import com.example.jwtsecureapp.dao.AppUserRepository;
import com.example.jwtsecureapp.domain.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class MyUserDetailService implements UserDetailsService {
    Logger logger= LoggerFactory.getLogger(MyUserDetailService.class);
    @Autowired
    private AppUserRepository appUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //look up the app user in our database
        AppUser appUser = appUserRepository.findAppUserByUsername(username);
        if (appUser == null) {
            logger.error("App user not found in the database :{}",username);
            throw new UsernameNotFoundException("App user not found in the database.");
        } else {
            logger.info("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }
}
