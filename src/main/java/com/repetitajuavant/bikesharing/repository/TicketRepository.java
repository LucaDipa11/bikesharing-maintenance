package com.repetitajuavant.bikesharing.repository;

import com.repetitajuavant.bikesharing.enums.TicketStatus;
import com.repetitajuavant.bikesharing.model.Technician;
import com.repetitajuavant.bikesharing.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

// Repository per gestione ticket
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    // Recupera ticket aperti
    List<Ticket> findByStatus(TicketStatus status);

    // Recupera ticket per mese
    List<Ticket> findByCreatedDateBetween(LocalDate start, LocalDate end);

    // Conta ticket per tecnico in un giorno
    long countByTechnicianAndCreatedDate(Technician tech, LocalDate date);
}