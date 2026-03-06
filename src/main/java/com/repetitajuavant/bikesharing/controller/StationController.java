package com.repetitajuavant.bikesharing.controller;

import com.repetitajuavant.bikesharing.model.Station;
import com.repetitajuavant.bikesharing.repository.StationRepository;
import com.repetitajuavant.bikesharing.exception.StationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * StationController - Gestione API REST per le stazioni di bike sharing
 * 
 * Endpoints per:
 * - Visualizzare tutte le stazioni
 * - Visualizzare una stazione specifica
 * - Creare una nuova stazione
 * - Aggiornare i dati di una stazione
 * - Eliminare una stazione (temporaneamente fuori servizio)
 */
@RestController
@RequestMapping("/stations")
@RequiredArgsConstructor
public class StationController {

    private final StationRepository stationRepository;

    /**
     * GET /stations - Ottiene tutte le stazioni
     */
    @GetMapping
    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    /**
     * GET /stations/{id} - Ottiene una stazione specifica
     */
    @GetMapping("/{id}")
    public Station getStation(@PathVariable Integer id) {
        return stationRepository.findById(id)
                .orElseThrow(() -> new StationNotFoundException(id));
    }

    /**
     * POST /stations - Crea una nuova stazione
     * Utile se il comune vuole aggiungere nuove stazioni
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Station createStation(@RequestParam String name) {
        Station station = new Station();
        station.setName(name);
        return stationRepository.save(station);
    }

    /**
     * PUT /stations/{id} - Aggiorna il nome di una stazione
     */
    @PutMapping("/{id}")
    public Station updateStation(@PathVariable Integer id, @RequestParam String name) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new StationNotFoundException(id));
        
        station.setName(name);
        return stationRepository.save(station);
    }

    /**
     * DELETE /stations/{id} - Elimina una stazione
     * (Attenzione: considerate le implicazioni con le bici assegnate)
     */
    @DeleteMapping("/{id}")
    public void deleteStation(@PathVariable Integer id) {
        stationRepository.deleteById(id);
    }
}
