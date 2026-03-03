package com.joe.coffee.api.Exception.ArtisanTorrefacteurExceptions;

public class DeleteLinkCafeArtisanTorrefacteurException extends RuntimeException {
    public DeleteLinkCafeArtisanTorrefacteurException() {
        super("Impossible de supprimer cet artisan : des cafés sont rattachés");
    }
}
