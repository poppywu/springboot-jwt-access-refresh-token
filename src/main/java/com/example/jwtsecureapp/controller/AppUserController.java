package com.example.jwtsecureapp.controller;

import com.example.jwtsecureapp.domain.AppUser;
import com.example.jwtsecureapp.domain.Role;
import com.example.jwtsecureapp.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/public")
public class AppUserController {
    @Autowired
    private AppUserService appUserService;

    @GetMapping(path = "/logs")
    public String getLogs() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("./logs/hello.txt"),StandardCharsets.US_ASCII);
        return lines.toString();
    }

    @GetMapping(path="/hello")
    public String hello(){
        return "Hello";
    }


    @PostMapping(path = "/user/save")
    public ResponseEntity<AppUser> saveAppUsers(@RequestBody AppUser appUser) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(appUserService.saveAppUser(appUser));
    }

    @PostMapping(path = "/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(appUserService.saveRole(role));
    }

    @PostMapping(path = "/role/addtouser")
    public ResponseEntity<?> saveRoleToAppUser(@RequestBody Map<String, String> dataMap) {
        String username = dataMap.get("username");
        String roleName = dataMap.get("roleName");
        appUserService.addRoleToAppUser(username, roleName);
        return ResponseEntity.ok().build();
    }
}
