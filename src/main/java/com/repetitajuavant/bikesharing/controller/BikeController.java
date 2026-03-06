package com.repetitajuavant.bikesharing.controller;

import com.repetitajuavant.bikesharing.model.Bike;
import com.repetitajuavant.bikesharing.model.Station;
import com.repetitajuavant.bikesharing.repository.BikeRepository;
import com.repetitajuavant.bikesharing.repository.StationRepository;
import com.repetitajuavant.bikesharing.exception.BikeNotFoundException;
import com.repetitajuavant.bikesharing.exception.StationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * BikeController - Gestione API REST per le biciclette
 * 
 * Endpoints per:
 * - Visualizzare tutte le biciclette
 * - Visualizzare una bicicletta specifica
 * - Ottenere le biciclette di una stazione
 * - Spostare una bicicletta tra stazioni
 */
@RestController
@RequestMapping("/bikes")
@RequiredArgsConstructor
public class BikeController {

    private final BikeRepository bikeRepository;
    private final StationRepository stationRepository;

    /**
     * GET /bikes - Ottiene tutte le biciclette del sistema
     */
    @GetMapping
    public List<Bike> getAllBikes() {
        return bikeRepository.findAll();
    }

    /**
     * GET /bikes/{id} - Ottiene una bicicletta specifica
     */
    @GetMapping("/{id}")
    public Bike getBike(@PathVariable Integer id) {
        return bikeRepository.findById(id)
                .orElseThrow(() -> new BikeNotFoundException(id));
    }

    /**
     * GET /bikes/station/{stationId} - Ottiene tutte le bici di una stazione
     */
    @GetMapping("/station/{stationId}")
    public List<Bike> getBikesByStation(@PathVariable Integer stationId) {
        Station station = stationRepository.findById(stationId)
                .orElseThrow(() -> new StationNotFoundException(stationId));
        
        return bikeRepository.findByStation(station);
    }

    /**
     * POST /bikes - Crea una nuova bicicletta
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bike createBike(@RequestParam String code, @RequestParam Integer stationId) {
        Station station = stationRepository.findById(stationId)
                .orElseThrow(() -> new StationNotFoundException(stationId));
        
        Bike bike = new Bike();
        bike.setCode(code);
        bike.setStation(station);
        
        return bikeRepository.save(bike);
    }

    /**
     * PUT /bikes/{id}/station - Sposta una bicicletta a una nuova stazione
     * Utile quando una bici viene riparata o riposizionata
     */
    @PutMapping("/{id}/station")
    public Bike moveBikeToStation(@PathVariable Integer id, @RequestParam Integer stationId) {
        Bike bike = bikeRepository.findById(id)
                .orElseThrow(() -> new BikeNotFoundException(id));
        
        Station station = stationRepository.findById(stationId)
                .orElseThrow(() -> new StationNotFoundException(stationId));
        
        bike.setStation(station);
        return bikeRepository.save(bike);
    }

    /**
     * DELETE /bikes/{id} - Elimina una bicicletta (quando è fuori servizio)
     */
    @DeleteMapping("/{id}")
    public void deleteBike(@PathVariable Integer id) {
        bikeRepository.deleteById(id);
    }
}
