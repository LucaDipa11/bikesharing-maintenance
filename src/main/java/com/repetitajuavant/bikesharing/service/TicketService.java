package com.repetitajuavant.bikesharing.service;

import com.repetitajuavant.bikesharing.enums.IssueType;
import com.repetitajuavant.bikesharing.enums.TicketStatus;
import com.repetitajuavant.bikesharing.model.Bike;
import com.repetitajuavant.bikesharing.model.Ticket;
import com.repetitajuavant.bikesharing.repository.BikeRepository;
import com.repetitajuavant.bikesharing.repository.TicketRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;

// Servizio gestione ticket
@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final BikeRepository bikeRepository;

    // Crea nuovo ticket di manutenzione
    public Ticket createTicket(Integer bikeId, IssueType issueType) {

        Bike bike = bikeRepository.findById(bikeId).orElseThrow();

        Ticket ticket = new Ticket();
        ticket.setBike(bike);
        ticket.setIssueType(issueType);
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCreatedDate(LocalDate.now());

        return ticketRepository.save(ticket);
    }

    // Aggiorna stato ticket
    public Ticket updateStatus(Integer ticketId, TicketStatus status) {

        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        ticket.setStatus(status);

        if (status == TicketStatus.COMPLETED) {
            ticket.setCompletedDate(LocalDate.now());
        }

        return ticketRepository.save(ticket);
    }
}