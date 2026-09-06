package com.joe.coffee.api.Dto.In;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO d'entrée d'un distributeur
 */
@Schema(description = "Représentation détaillée d'un distributeur pour les données entrantes de l'API")
public record DistributeurDtoIn(

        @NotBlank(message = "Le nom du distributeur est obligatoire")
        @Size(min = 3, max = 50, message = "Le nom du distributeur doit contenir entre 3 et 50 caractères")
        @Schema(
                description = "Nom du distributeur",
                example = "Cafés de Paris",
                minLength = 3,
                maxLength = 50
        )
        String nom,

        @NotBlank(message = "L'adresse du distributeur est obligatoire")
        @Size(min = 3, max = 255, message = "L'adresse du distributeur doit contenir entre 3 et 255 caractères")
        @Schema(
                description = "Adresse du distributeur",
                example = "12 rue du Café, Paris",
                minLength = 3,
                maxLength = 255
        )
        String adresse,

        @NotBlank(message = "L'email du distributeur est obligatoire")
        @Size(min = 3, max = 80, message = "L'email du distributeur doit contenir entre 3 et 80 caractères")
        @Schema(
                description = "email du distributeur",
                example = "contact@cafes-paris.fr",
                minLength = 3,
                maxLength = 80
        )
        String email,

        @NotBlank(message = "Le téléphone du distributeur est obligatoire")
        @Size(min = 3, max = 30, message = "Le téléphone du distributeur doit contenir entre 3 et 30 caractères")
        @Schema(
                description = "téléphone du distributeur",
                example = "0102030405",
                minLength = 3,
                maxLength = 30
        )
        String telephone,

        @NotBlank(message = "Le site internet du distributeur est obligatoire")
        @Size(min = 3, max = 30, message = "Le site internet du distributeur doit contenir entre 3 et 30 caractères")
        @Schema(
                description = "site internet du distributeur",
                example = "https://www.cafes-paris.fr",
                minLength = 3,
                maxLength = 30
        )
        String siteInternet,

        @NotBlank(message = "Le nom du groupe de distribution est obligatoire")
        @Size(min = 3, max = 50, message = "Le nom du groupe de distribution doit contenir entre 3 et 50 caractères")
        @Schema(
                description = "Nom du groupe de distribution",
                example = "Coopérative U",
                minLength = 3,
                maxLength = 50
        )
        String nomDuGroupeDeDistribution
) {
}
