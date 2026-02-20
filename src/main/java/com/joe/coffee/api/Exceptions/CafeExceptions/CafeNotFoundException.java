package com.joe.coffee.api.Exceptions.CafeExceptions;

/**
 * Exception pour un café inconnu de la bdd
 */
public class CafeNotFoundException extends RuntimeException {

    public CafeNotFoundException(Long id) {
        super("Cafe avec l'id " + id + " introuvable.");
    }
}
