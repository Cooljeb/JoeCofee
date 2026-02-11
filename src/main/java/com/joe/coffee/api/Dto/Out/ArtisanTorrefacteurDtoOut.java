package com.joe.coffee.api.Dto.Out;

/**
 * DTO d'entrée d'un artisan torréfacteur
 */
public record ArtisanTorrefacteurDtoOut(

        Long id,
        String nom,
        String anneeCreation
) {
}
