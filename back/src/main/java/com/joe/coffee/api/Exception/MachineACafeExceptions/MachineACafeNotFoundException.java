package com.joe.coffee.api.Exception.MachineACafeExceptions;

public class MachineACafeNotFoundException extends RuntimeException {
    public MachineACafeNotFoundException(Integer id) {
        super("Machine à café avec l'id " + id + " introuvable.");
    }
    public MachineACafeNotFoundException(String machineACafe) {
        super(" la machine à café nommée" + machineACafe + " est introuvable.");
    }
}
