package com.joe.coffee.api.Exceptions;

import java.time.LocalDateTime;

/**
 * Record général des messages d'erreur
 * @param status envoi le type d'erreur
 * @param message correspondant à l'anomalie
 * @param timestamp horodatage
 */
public record ErrorResponse(
        int status,
        String message,
        LocalDateTime timestamp
) {
}
