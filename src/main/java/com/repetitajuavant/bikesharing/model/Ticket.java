package com.repetitajuavant.bikesharing.model;

import jakarta.persistence.*;
import lombok.*;
import com.repetitajuavant.bikesharing.enums.IssueType;
import com.repetitajuavant.bikesharing.enums.TicketStatus;

import java.time.LocalDate;

@Entity
@Data
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Bici associata al problema
    @ManyToOne
    private Bike bike;

    // Tipo di problema segnalato
    @Enumerated(EnumType.STRING)
    private IssueType issueType;

    // Stato corrente del ticket
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    // Tecnico assegnato
    @ManyToOne
    private Technician technician;

    // Data creazione ticket
    private LocalDate createdDate;

    // Data completamento intervento
    private LocalDate completedDate;
}