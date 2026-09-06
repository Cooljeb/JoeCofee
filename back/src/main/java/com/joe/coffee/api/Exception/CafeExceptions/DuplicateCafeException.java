package com.joe.coffee.api.Exception.CafeExceptions;

public class DuplicateCafeException  extends RuntimeException {
    public DuplicateCafeException(String nomCafe) {
        super("Un café avec le nom '" + nomCafe + "' existe déjà.");
    }
}
