package com.joe.coffee.api.Exception.DistributeurExceptions;

public class DistributeurNotFoundException extends RuntimeException {

    public DistributeurNotFoundException(Long id) {

        super("Distributeur avec l'id " + id + " introuvable.");

    }
    public DistributeurNotFoundException(String d) {

        super("Distributeur avec le nom " + d + " introuvable.");

    }
}