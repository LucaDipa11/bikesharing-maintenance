package com.repetitajuavant.bikesharing.exception;

/**
 * BikeNotFoundException - Eccezione custom quando una bicicletta non viene trovata
 */
public class BikeNotFoundException extends RuntimeException {
    public BikeNotFoundException(Integer id) {
        super("Bicicletta non trovata con ID: " + id);
    }
}
