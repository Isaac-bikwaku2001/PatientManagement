package com.example.gestionpat.repositories;

import com.example.gestionpat.entities.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultantRepository extends JpaRepository<Consultation, Long> {}
