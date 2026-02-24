package com.joe.coffee.api.Exception.ArtisanTorrefacteurExceptions;

public class DuplicateArtisanTorrefacteurException extends RuntimeException {

    public DuplicateArtisanTorrefacteurException(String at) {
        super("Un Artisan Torréfacteur  avec le nom '" + at + "' existe déjà.");
    }
}
