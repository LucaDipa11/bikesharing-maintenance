package com.repetitajuavant.bikesharing.controller;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;
import com.repetitajuavant.bikesharing.model.Technician;
import com.repetitajuavant.bikesharing.service.TechnicianService;

// Controller REST per gestione tecnici
@RestController
@RequestMapping("/technicians")
@RequiredArgsConstructor
public class TechnicianController {

    private final TechnicianService technicianService;

    // Endpoint per ottenere tutti i tecnici
    @GetMapping
    public List<Technician> getAll() {
        return technicianService.getAllTechnicians();
    }

    // Endpoint per ottenere tecnico per id
    @GetMapping("/{id}")
    public Technician getById(@PathVariable Integer id) {
        return technicianService.getTechnicianById(id);
    }

}