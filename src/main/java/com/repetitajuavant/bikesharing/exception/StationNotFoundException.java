package com.repetitajuavant.bikesharing.exception;

/**
 * StationNotFoundException - Eccezione custom quando una stazione non viene trovata
 */
public class StationNotFoundException extends RuntimeException {
    public StationNotFoundException(Integer id) {
        super("Stazione non trovata con ID: " + id);
    }
}
