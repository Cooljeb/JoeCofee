package com.joe.coffee.api.Exception.ConsommationExceptions;

/**
 * Exception levée lorsqu'une consommation est introuvable.
 */
public class ConsommationNotFoundException extends RuntimeException {

    public ConsommationNotFoundException(Integer id) {
        super("Consommation avec id " + id + " introuvable");
    }
}
