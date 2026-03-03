package com.repetitajuavant.bikesharing.config;

import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import com.repetitajuavant.bikesharing.repository.*;
import com.repetitajuavant.bikesharing.model.*;

/**
 * DataLoader - Carica i dati iniziali al avvio dell'applicazione
 * 
 * Popola il database H2 con dati di test:
 * - 5 tecnici di manutenzione
 * - 10 stazioni di bike sharing
 * - 100 biciclette distribuite tra le stazioni
 * 
 * Eseguito una sola volta al startup tramite @PostConstruct
 */
@Component
@RequiredArgsConstructor
public class DataLoader {

    private final TechnicianRepository technicianRepository;
    private final StationRepository stationRepository;
    private final BikeRepository bikeRepository;

    @PostConstruct
    public void load() {
        // Carica i dati solo se il database è vuoto per non duplicarli
        if (stationRepository.count() > 0) {
            return;
        }

        // ========== STAZIONI ==========
        // Creiamo 10 stazioni sparse in città
        String[] stationNames = {
            "Centro",
            "Stazione Ferroviaria",
            "Parco Principale",
            "Università",
            "Ospedale",
            "Municipio",
            "Piazza Roma",
            "Lungarno",
            "Quartiere Nord",
            "Quartiere Sud"
        };

        for (String name : stationNames) {
            Station station = new Station();
            station.setName(name);
            stationRepository.save(station);
        }

        // ========== TECNICI ==========
        // 5 tecnici per gestire la manutenzione (max 8 interventi al giorno ognuno)
        for (int i = 1; i <= 5; i++) {
            Technician tech = new Technician();
            tech.setName("Tecnico " + i);
            technicianRepository.save(tech);
        }

        // ========== BICICLETTE ==========
        // 100 biciclette distribuite equamente tra le 10 stazioni (10 bici per stazione)
        java.util.List<Station> stations = stationRepository.findAll();
        int bikeCounter = 1;
        
        for (Station station : stations) {
            for (int i = 0; i < 10; i++) {
                Bike bike = new Bike();
                bike.setCode("BIKE-" + String.format("%03d", bikeCounter));
                bike.setStation(station);
                bikeRepository.save(bike);
                bikeCounter++;
            }
        }

        System.out.println("DataLoader completato - Caricate 100 biciclette, 10 stazioni e 5 tecnici");
    }
}