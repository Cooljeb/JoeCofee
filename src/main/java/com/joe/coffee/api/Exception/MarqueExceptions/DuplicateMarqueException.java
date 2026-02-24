package com.joe.coffee.api.Exception.MarqueExceptions;

public class DuplicateMarqueException extends RuntimeException {
    public DuplicateMarqueException(String marque) {
        super("Une marque avec le nom '" + marque + "' existe déjà.");
    }
}
