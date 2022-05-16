package com.example.gestionpat.security.service;

import com.example.gestionpat.security.entities.AppRole;
import com.example.gestionpat.security.entities.AppUser;
import com.example.gestionpat.security.repositories.AppRoleRepository;
import com.example.gestionpat.security.repositories.AppUserRepository;
import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class SecurityServiceImpl implements SecurityService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveNewUser(String username, String password, String rePassword) {
        if(!password.equals(rePassword)){
            throw new RuntimeException("Password Not match");
        }
        String hashpassword = passwordEncoder.encode(password);
        AppUser appUser = new AppUser();
        appUser.setUserId(UUID.randomUUID().toString());
        appUser.setUsername(username);
        appUser.setPassword(hashpassword);
        appUser.setActive(true);
        AppUser savedUser = appUserRepository.save(appUser);
        return savedUser;
    }

    @Override
    public AppRole saveNewRole(String roleName, String description) {
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        if(appRole != null) throw new RuntimeException(roleName + " exists");
        appRole = new AppRole();
        appRole.setRoleName(roleName);
        appRole.setDescription(description);
        AppRole savedRole = appRoleRepository.save(appRole);
        return savedRole;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser == null) throw new RuntimeException(username + " not found");
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        if(appRole == null) throw new RuntimeException(roleName + " not found");
        appUser.getAppRoles().add(appRole);
    }

    @Override
    public void removeRoleFromUser(String username, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser == null) throw new RuntimeException(username + " not found");
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        if(appRole == null) throw new RuntimeException(roleName + " not found");
        appUser.getAppRoles().remove(appRole);
    }

    @Override
    public AppUser loadByUserName(String username) {
        return appUserRepository.findByUsername(username);
    }
}
