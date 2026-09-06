package com.joe.coffee.api.Exception.MarqueExceptions;

public class MarqueNotFoundException extends RuntimeException {

    public MarqueNotFoundException(Integer id) {
        super("Marque avec l'id " + id + " introuvable.");
    }
    public MarqueNotFoundException(String marque) {
        super("la marque " + marque + " est n'a pas été trouvé.");
    }
}