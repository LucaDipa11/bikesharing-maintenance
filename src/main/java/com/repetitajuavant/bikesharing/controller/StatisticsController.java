package com.repetitajuavant.bikesharing.controller;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.Map;
import com.repetitajuavant.bikesharing.service.StatisticsService;

// Controller REST per statistiche
@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    // Endpoint per ottenere statistiche mensili
    @GetMapping("/monthly")
    public Map<String, Object> getMonthly(
            @RequestParam int year,
            @RequestParam int month) {

        return statisticsService.getMonthlyStatistics(year, month);
    }
}