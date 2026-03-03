package com.joe.coffee.api.Exception.MachineACafeExceptions;

public class DuplicateMachineACafeException extends RuntimeException {
    public DuplicateMachineACafeException(String nomMachineACafe) {
        super("Une  machine à café de ce nom'" +nomMachineACafe +"'existe déjà.");
    }
}
