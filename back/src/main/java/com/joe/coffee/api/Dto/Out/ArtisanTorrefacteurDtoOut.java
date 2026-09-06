package com.joe.coffee.api.Dto.Out;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO d'entrée d'un artisan torréfacteur
 */
@Schema(description = "Représentation détaillée d'un artisan torréfacteur (AT)  pour les retours API")
public record ArtisanTorrefacteurDtoOut(

        @Schema(description = "Identifiant unique d'un AT'", example = "2")
        Integer id,

        @Schema(description = "Nom de l'Artisan", example = "Lyon Torréfaction")
        String nom,

        @Schema(description = "Adresse de l'artisan", example = "5 place du Marché, Lyon")
        String adresse,

        @Schema(description = "Email de l'artisan", example = "artisan@lyon-cafe.fr")
        String email,

        @Schema(description = "Téléphone de l'artisan", example = "0405060708")
        String telephone,

        @Schema(description = "Site internet de l'artisan", example = "https://www.lyon-cafe.fr")
        String siteInternet,

        @Schema(description = "Année de création de l'artisan", example = "2005")
        String anneeCreation
) {
}
