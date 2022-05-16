package com.example.gestionpat;

import com.example.gestionpat.entities.Patient;
import com.example.gestionpat.repositories.PatientRepository;
import com.example.gestionpat.security.service.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class GestionPatApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionPatApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //@Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){
        return args -> {};
    }

    //@Bean
    CommandLineRunner savedUsers(SecurityService securityService){
        return args -> {
            securityService.saveNewUser("sec", "sec2022", "sec2022");
            securityService.saveNewUser("admin", "admin2022", "admin2022");

            securityService.saveNewRole("USER", "");
            securityService.saveNewRole("ADMIN", "");

            securityService.addRoleToUser("admin", "ADMIN");
            securityService.addRoleToUser("admin", "USER");
            securityService.addRoleToUser("sec", "USER");
        };
    }
}
