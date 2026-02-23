package com.joe.coffee.api.Exception.MarqueException;

public class MarqueNotFoundException extends RuntimeException {

    public MarqueNotFoundException(Long id) {
        super("Marque avec l'id " + id + " introuvable.");
    }
    public MarqueNotFoundException(String marque) {
        super("la marque " + marque + " est n'a pas été trouvé.");
    }
}