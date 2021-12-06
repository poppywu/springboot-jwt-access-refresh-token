package com.example.jwtsecureapp;

import com.example.jwtsecureapp.domain.AppUser;
import com.example.jwtsecureapp.domain.Role;
import com.example.jwtsecureapp.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class JwtSecureAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtSecureAppApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(AppUserService appUserService) {
        return args -> {
            //create roles and save to db when the application starts
            appUserService.saveRole(new Role(null, "ROLE_USER"));
            appUserService.saveRole(new Role(null, "ROLE_MANAGER"));
            appUserService.saveRole(new Role(null, "ROLE_ADMIN"));
            appUserService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
            //create app user and save to db when the application starts
            appUserService.saveAppUser(new AppUser(null, "John Doe", "john", "1234", new ArrayList<>()));
            appUserService.saveAppUser(new AppUser(null, "Will Smith", "will", "1234", new ArrayList<>()));
            appUserService.saveAppUser(new AppUser(null, "Taylor Swift", "taylor", "1234", new ArrayList<>()));
            appUserService.saveAppUser(new AppUser(null, "Kayne West", "kayne", "1234", new ArrayList<>()));
            //assign roles to each app user on the fly
            appUserService.addRoleToAppUser("john", "ROLE_USER");
            appUserService.addRoleToAppUser("will", "ROLE_MANAGER");
            appUserService.addRoleToAppUser("taylor", "ROLE_ADMIN");
            appUserService.addRoleToAppUser("kayne", "ROLE_SUPER_ADMIN");
            appUserService.addRoleToAppUser("kayne", "ROLE_ADMIN");
            appUserService.addRoleToAppUser("kayne", "ROLE_USER");
        };
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
