package com.joe.coffee.api.Dto.Out;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO d'entrée d'un artisan torréfacteur
 */
@Schema(description = "Représentation détaillée d'un artisan torréfacteur (AT)  pour les retours API")
public record ArtisanTorrefacteurDtoOut(

        @Schema(description = "Identifiant unique d'un AT'", example = "2")
        Long id,

        @Schema(description = "Nom de l'AT'", example = "Lyon Torréfaction")
        String nom,

        @Schema(description = "Année de création de l'artisan", example = "2005")
        String anneeCreation
) {
}
