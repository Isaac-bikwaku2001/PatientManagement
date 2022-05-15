package com.example.gestionpat;

import com.example.gestionpat.entities.Patient;
import com.example.gestionpat.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class GestionPatApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionPatApplication.class, args);
    }

    //@Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){
        return args -> {};
    }
}
