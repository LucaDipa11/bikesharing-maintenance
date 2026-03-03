package com.repetitajuavant.bikesharing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.repetitajuavant.bikesharing.model.Station;
import java.util.Optional;

// Repository per gestione entità Station
public interface StationRepository extends JpaRepository<Station, Integer> {

    // Recupera stazione tramite nome
    Optional<Station> findByName(String name);

}