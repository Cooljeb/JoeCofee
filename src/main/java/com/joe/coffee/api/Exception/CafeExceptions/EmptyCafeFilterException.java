package com.joe.coffee.api.Exception.CafeExceptions;

public class EmptyCafeFilterException extends RuntimeException {

    public EmptyCafeFilterException() {
        super("Filtres sur le label et type vides !");
    }
}
