package com.repetitajuavant.bikesharing.exception;

/**
 * TechnicianNotFoundException - Eccezione custom quando un tecnico non viene trovato
 */
public class TechnicianNotFoundException extends RuntimeException {
    public TechnicianNotFoundException(Integer id) {
        super("Tecnico non trovato con ID: " + id);
    }
}
