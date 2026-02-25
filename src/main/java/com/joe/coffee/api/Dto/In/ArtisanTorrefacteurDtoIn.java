package com.joe.coffee.api.Dto.In;

import com.joe.coffee.api.Utils.YearConstraint.YearConstraint;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO d'entrée d'un artisan torréfacteur
 */
public record ArtisanTorrefacteurDtoIn(

        @NotBlank(message = "Le nom de l'artisan est obligatoire")
        @Size(min = 3, max = 50, message = "Le nom de l'artisan doit contenir entre 3 et 50 caractères")
        @Schema(
                description = "Nom de l'artisan",
                example = "Lyon Torréfaction",
                minLength = 3,
                maxLength = 50
        )
        String nom,

        @NotBlank(message = "L'adresse de l'artisan est obligatoire")
        @Size(min = 3, max = 255, message = "L'adresse de l'artisan doit contenir entre 3 et 255 caractères")
        @Schema(
                description = "Adresse de l'artisan",
                example = "5 place du Marché, Lyon",
                minLength = 3,
                maxLength = 255
        )
        String adresse,

        @NotBlank(message = "L'email de l'artisan est obligatoire")
        @Size(min = 3, max = 80, message = "L'email de l'artisan doit contenir entre 3 et 80 caractères")
        @Schema(
                description = "email de l'artisan",
                example = "artisan@lyon-cafe.fr",
                minLength = 3,
                maxLength = 80
        )
        String email,

        @NotBlank(message = "Le téléphone de l'artisan est obligatoire")
        @Size(min = 3, max = 30, message = "Le téléphone de l'artisan doit contenir entre 3 et 30 caractères")
        @Schema(
                description = "téléphone de l'artisan",
                example = "0405060708",
                minLength = 3,
                maxLength = 30
        )
        String telephone,

        @NotBlank(message = "Le site internet de l'artisan est obligatoire")
        @Size(min = 3, max = 30, message = "Le site internet de l'artisan doit contenir entre 3 et 30 caractères")
        @Schema(
                description = "site internet de l'artisan",
                example = "https://www.lyon-cafe.fr",
                minLength = 3,
                maxLength = 30
        )
        String siteInternet,

        @YearConstraint(min = 1800)
        @Schema(description = "Année de création de l'artisan", example = "2005")
        String anneeCreation
) {
}
