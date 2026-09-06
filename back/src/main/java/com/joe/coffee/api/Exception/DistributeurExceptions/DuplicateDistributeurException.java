package com.joe.coffee.api.Exception.DistributeurExceptions;

public class DuplicateDistributeurException extends RuntimeException {

    public DuplicateDistributeurException(String d) {
        super("Un distributeur  avec le nom '" + d + "' existe déjà.");
    }
}

