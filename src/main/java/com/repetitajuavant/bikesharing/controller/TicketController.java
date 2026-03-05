package com.repetitajuavant.bikesharing.controller;

import com.repetitajuavant.bikesharing.enums.IssueType;
import com.repetitajuavant.bikesharing.enums.TicketStatus;
import com.repetitajuavant.bikesharing.repository.TicketRepository;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.repetitajuavant.bikesharing.service.*;
import com.repetitajuavant.bikesharing.model.*;

import java.util.List;

// Controller REST per gestione ticket
@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final AssignmentService assignmentService;
    private final TicketRepository ticketRepository;

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
    // Endpoint creazione ticket
    @PostMapping
    public Ticket create(@RequestParam Integer bikeId,
                         @RequestParam IssueType issueType) {
        return ticketService.createTicket(bikeId, issueType);
    }

    // Endpoint aggiornamento stato
    @PutMapping("/{id}/status")
    public Ticket updateStatus(@PathVariable Integer id,
                               @RequestParam TicketStatus status) {
        return ticketService.updateStatus(id, status);
    }

    // Endpoint per assegnazione automatica
    @PostMapping("/assign")
    public void assign() {
        assignmentService.assignTickets();
    }
}