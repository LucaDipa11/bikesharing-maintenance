package com.repetitajuavant.bikesharing.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
public class Technician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Nome del tecnico
    private String name;

    // Lista interventi assegnati
    @OneToMany(mappedBy = "technician")
    private List<Ticket> tickets;
}