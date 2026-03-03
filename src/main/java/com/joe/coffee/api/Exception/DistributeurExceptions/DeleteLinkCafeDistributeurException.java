package com.joe.coffee.api.Exception.DistributeurExceptions;

public class DeleteLinkCafeDistributeurException extends RuntimeException {
    public DeleteLinkCafeDistributeurException() {
        super( "Impossible de supprimer ce distributeur : des cafés sont rattachés");
    }
}
