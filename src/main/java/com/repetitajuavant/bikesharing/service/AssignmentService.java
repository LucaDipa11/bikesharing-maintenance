package com.repetitajuavant.bikesharing.service;

import com.repetitajuavant.bikesharing.enums.TicketStatus;
import com.repetitajuavant.bikesharing.model.Technician;
import com.repetitajuavant.bikesharing.model.Ticket;
import com.repetitajuavant.bikesharing.repository.TechnicianRepository;
import com.repetitajuavant.bikesharing.repository.TicketRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.*;

// Servizio per assegnazione automatica interventi
@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final TicketRepository ticketRepository;
    private final TechnicianRepository technicianRepository;

    // Assegna ticket aperti ai tecnici disponibili
    public void assignTickets() {

        List<Ticket> openTickets = ticketRepository.findByStatus(TicketStatus.OPEN);
        List<Technician> technicians = technicianRepository.findAll();

        int techIndex = 0;
        int countForTech = 0;

        for (Ticket ticket : openTickets) {

            Technician tech = technicians.get(techIndex);

            // Limite massimo 8 interventi per tecnico al giorno
            if (countForTech == 8) {
                techIndex++;
                countForTech = 0;
            }

            if (techIndex >= technicians.size()) break;

            ticket.setTechnician(tech);
            ticket.setStatus(TicketStatus.ASSIGNED);

            ticketRepository.save(ticket);

            countForTech++;
        }
    }
}