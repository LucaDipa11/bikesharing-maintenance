package com.repetitajuavant.bikesharing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler - Gestisce le eccezioni lanciate dall'applicazione
 * 
 * Fornisce risposte HTTP standardizzate con informazioni di errore
 * per tutti gli endpoint REST dell'applicazione
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gestisce BikeNotFoundException quando una bici non viene trovata
     */
    @ExceptionHandler(BikeNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleBikeNotFound(
            BikeNotFoundException ex,
            WebRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    /**
     * Gestisce StationNotFoundException quando una stazione non viene trovata
     */
    @ExceptionHandler(StationNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleStationNotFound(
            StationNotFoundException ex,
            WebRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    /**
     * Gestisce TechnicianNotFoundException quando un tecnico non viene trovato
     */
    @ExceptionHandler(TechnicianNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTechnicianNotFound(
            TechnicianNotFoundException ex,
            WebRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    /**
     * Gestisce TicketNotFoundException quando un ticket non viene trovato
     */
    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTicketNotFound(
            TicketNotFoundException ex,
            WebRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    /**
     * Gestisce eccezioni generiche RuntimeException
     * Fallback per eventuali altre eccezioni non mappate
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(
            RuntimeException ex,
            WebRequest request) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, 
                ex.getMessage() != null ? ex.getMessage() : "Errore interno del server", 
                request);
    }

    /**
     * Gestisce eccezioni generiche
     * Fallback finale per qualsiasi eccezione non prevista
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(
            Exception ex,
            WebRequest request) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Si è verificato un errore non previsto", 
                request);
    }

    /**
     * Metodo di utilità per costruire risposte di errore coerenti
     */
    private ResponseEntity<Map<String, Object>> buildErrorResponse(
            HttpStatus status,
            String message,
            WebRequest request) {
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", status.value());
        errorResponse.put("error", status.getReasonPhrase());
        errorResponse.put("message", message);
        errorResponse.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(errorResponse, status);
    }
}
