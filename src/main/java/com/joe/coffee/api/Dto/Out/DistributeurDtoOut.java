package com.joe.coffee.api.Dto.Out;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO de sortie d'un distributeur
 */
@Schema(description = "Représentation détaillée d'un distributeur pour les retours API")
public record DistributeurDtoOut(

        @Schema(description = "Identifiant unique du distributeur", example = "1")
        Long id,

        @Schema(description = "Nom du distributeur", example = "Cafés de Paris")
        String nom,

        @Schema(description = "Adresse du distributeur", example = "12 rue du Café, Paris")
        String adresse,

        @Schema(description = "Email du distributeur", example = "contact@cafes-paris.fr")
        String email,

        @Schema(description = "Téléphone du distributeur", example = "0102030405")
        String telephone,

        @Schema(description = "Site internet du distributeur", example = "https://www.cafes-paris.fr")
        String siteInternet,

        @Schema(description = "Nom du groupe de distribution", example = "Coopérative U")
        String nomDuGroupeDeDistribution
) {
}
