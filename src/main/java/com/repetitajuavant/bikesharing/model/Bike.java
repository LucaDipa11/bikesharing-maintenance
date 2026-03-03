package com.repetitajuavant.bikesharing.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Numero identificativo della bici
    private String code;

    // Stazione attuale della bici
    @ManyToOne
    private Station station;
}