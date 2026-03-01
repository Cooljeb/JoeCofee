package com.joe.coffee.api.Exception.CafeExceptions;

public class CafeCommercantNotFoundException extends RuntimeException {

    public CafeCommercantNotFoundException(Integer idComm) {
        super("Aucun café trouvé pour le commerçant avec id " + idComm);
    }
}
