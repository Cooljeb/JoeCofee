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

        @YearConstraint(min = 1800)
        @Schema(description = "Année de création de l'artisan", example = "2005")
        String anneeCreation
) {
}
