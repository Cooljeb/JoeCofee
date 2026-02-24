package com.joe.coffee.api.Exception.ArtisanTorrefacteurExceptions;

public class ArtisanTorrefacteurNotFoundException extends RuntimeException {

    public ArtisanTorrefacteurNotFoundException(Long id) {

        super("Artisan Torréfacteur avec l'id " + id + " introuvable.");

    }
    public ArtisanTorrefacteurNotFoundException(String at) {

        super("Artisan Torréfacteur avec l'id " + at + " introuvable.");

    }
}
