package com.repetitajuavant.bikesharing.exception;

/**
 * TicketNotFoundException - Eccezione custom quando un ticket non viene trovato
 */
public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(Integer id) {
        super("Ticket non trovato con ID: " + id);
    }
}
