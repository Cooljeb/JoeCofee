package com.joe.coffee.api.Exception;

import com.joe.coffee.api.Exception.ArtisanTorrefacteurExceptions.ArtisanTorrefacteurNotFoundException;
import com.joe.coffee.api.Exception.ArtisanTorrefacteurExceptions.DeleteLinkCafeArtisanTorrefacteurException;
import com.joe.coffee.api.Exception.ArtisanTorrefacteurExceptions.DuplicateArtisanTorrefacteurException;
import com.joe.coffee.api.Exception.CafeExceptions.CafeCommercantNotFoundException;
import com.joe.coffee.api.Exception.CafeExceptions.CafeNotFoundException;
import com.joe.coffee.api.Exception.CafeExceptions.DuplicateCafeException;
import com.joe.coffee.api.Exception.CafeExceptions.EmptyCafeFilterException;
import com.joe.coffee.api.Exception.DistributeurExceptions.DeleteLinkCafeDistributeurException;
import com.joe.coffee.api.Exception.DistributeurExceptions.DistributeurNotFoundException;
import com.joe.coffee.api.Exception.DistributeurExceptions.DuplicateDistributeurException;
import com.joe.coffee.api.Exception.MachineACafeExceptions.DuplicateMachineACafeException;
import com.joe.coffee.api.Exception.MachineACafeExceptions.MachineACafeNotFoundException;
import com.joe.coffee.api.Exception.MarqueExceptions.DuplicateMarqueException;
import com.joe.coffee.api.Exception.MarqueExceptions.MarqueNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // CAFE

    @ExceptionHandler(CafeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCafeNotFound(CafeNotFoundException ex) {
        log.warn("Erreur 404 : {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CafeCommercantNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCafeCommercantNotFound(CafeCommercantNotFoundException ex) {
        log.warn("Erreur 404 : {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyCafeFilterException.class)
    public ResponseEntity<ErrorResponse> handleEmptyCafeFilter(EmptyCafeFilterException ex) {
        log.warn("Erreur 400 : {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateCafeException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateCafe(DuplicateCafeException ex) {
        log.warn("Erreur 409 : {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    // MARQUE

    @ExceptionHandler(MarqueNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMarqueNotFound(MarqueNotFoundException ex) {
        log.warn("Erreur 404 : {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateMarqueException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateMarque(DuplicateMarqueException ex) {
        log.warn("Erreur 409 : {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ArtisanTorrefacteurNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleArtisanTorrefacteurNotFound(ArtisanTorrefacteurNotFoundException ex) {
        log.warn("Erreur 404 : {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateArtisanTorrefacteurException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateArtisanTorrefacteur(DuplicateArtisanTorrefacteurException ex) {
        log.warn("Erreur 409 : {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DeleteLinkCafeArtisanTorrefacteurException.class)
    public ResponseEntity<ErrorResponse> handleDeleteLinkCafeArtisanTorrefacteur(DeleteLinkCafeArtisanTorrefacteurException ex) {
        log.warn("Erreur 409 : {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DistributeurNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDistributeurNotFound(DistributeurNotFoundException ex) {
        log.warn("Erreur 404 : {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateDistributeurException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateDistributeur(DuplicateDistributeurException ex) {
        log.warn("Erreur 409 : {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DeleteLinkCafeDistributeurException.class)
    public ResponseEntity<ErrorResponse> handleDeleteLinkCafeDistributeur(DeleteLinkCafeDistributeurException ex) {
        log.warn("Erreur 409 : {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MachineACafeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMachineACafeNotFound(MachineACafeNotFoundException ex) {
        log.warn("Erreur 404 : {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateMachineACafeException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateMachineACafe(DuplicateMachineACafeException ex) {
        log.warn("Erreur 409 : {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

//    @ExceptionHandler(AutreException.class)
//    public ResponseEntity<ErrorResponse> handleAutre(AutreException ex) { ... }

    // Validation DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fe -> errors.put(fe.getField(), fe.getDefaultMessage()));

        log.warn("Erreur 400 : Validation échouée - {}", errors);

        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("errors", errors);
        body.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex) {
        log.error("Erreur inattendue: {}", ex.getMessage(), ex);
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Erreur serveur interne",
            LocalDateTime.now()
    );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
