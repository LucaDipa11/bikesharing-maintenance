package com.repetitajuavant.bikesharing.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.repetitajuavant.bikesharing.model.Technician;
import java.util.List;

// Repository per gestione entità Technician
public interface TechnicianRepository extends JpaRepository<Technician, Integer> {

    // Recupera tecnico per nome
    List<Technician> findByName(String name);

}