package com.repetitajuavant.bikesharing.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.repetitajuavant.bikesharing.model.Bike;
import com.repetitajuavant.bikesharing.model.Station;
import java.util.Optional;
import java.util.List;

// Repository per gestione entità Bike
public interface BikeRepository extends JpaRepository<Bike, Integer> {

    // Recupera bici tramite codice identificativo
    Optional<Bike> findByCode(String code);
    
    // Recupera tutte le bici di una stazione
    List<Bike> findByStation(Station station);

}