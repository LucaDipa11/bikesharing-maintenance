package com.repetitajuavant.bikesharing.service;

import com.repetitajuavant.bikesharing.enums.TicketStatus;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import com.repetitajuavant.bikesharing.model.*;
import com.repetitajuavant.bikesharing.repository.TicketRepository;

// Servizio per calcolo statistiche mensili
@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final TicketRepository ticketRepository;

    // Calcola statistiche per mese specifico
    public Map<String, Object> getMonthlyStatistics(int year, int month) {

        YearMonth yearMonth = YearMonth.of(year, month);

        // Calcola primo giorno del mese
        LocalDate start = yearMonth.atDay(1);

        // Calcola ultimo giorno del mese
        LocalDate end = yearMonth.atEndOfMonth();

        List<Ticket> tickets = ticketRepository.findByCreatedDateBetween(start, end);

        Map<String, Object> stats = new HashMap<>();

        // Totale ticket nel mese
        stats.put("totalTickets", tickets.size());

        // Ticket completati
        long completed = tickets.stream()
                .filter(t -> t.getStatus() == TicketStatus.COMPLETED)
                .count();

        stats.put("completedTickets", completed);

        // Ticket ancora aperti
        long open = tickets.stream()
                .filter(t -> t.getStatus() != TicketStatus.COMPLETED)
                .count();

        stats.put("openTickets", open);

        return stats;
    }
}