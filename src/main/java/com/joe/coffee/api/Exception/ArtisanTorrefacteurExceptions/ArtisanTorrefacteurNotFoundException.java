package com.joe.coffee.api.Exception.ArtisanTorrefacteurExceptions;

public class ArtisanTorrefacteurNotFoundException extends RuntimeException {

    public ArtisanTorrefacteurNotFoundException(Integer id) {

        super("Artisan Torréfacteur avec l'id " + id + " introuvable.");

    }
    public ArtisanTorrefacteurNotFoundException(String at) {

        super("Artisan Torréfacteur avec le nom " + at + " introuvable.");

    }
}
