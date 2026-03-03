package com.repetitajuavant.bikesharing.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Nome della stazione
    private String name;
}