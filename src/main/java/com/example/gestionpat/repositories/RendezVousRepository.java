package com.example.gestionpat.repositories;

import com.example.gestionpat.entities.RendezVous;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    Page<RendezVous> findByStatusContains(String kw, Pageable pageable);
}
