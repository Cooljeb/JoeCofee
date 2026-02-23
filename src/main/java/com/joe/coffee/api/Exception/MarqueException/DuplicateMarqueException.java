package com.joe.coffee.api.Exception.MarqueException;

public class DuplicateMarqueException extends RuntimeException {
    public DuplicateMarqueException(String marque) {
        super("Une marque avec le nom '" + marque + "' existe déjà.");
    }
}
