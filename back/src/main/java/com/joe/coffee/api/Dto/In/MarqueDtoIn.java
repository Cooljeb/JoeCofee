package com.joe.coffee.api.Dto.In;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Record correspondant au DTO d'entrée d'une marque
 * @param marque
 */
public record MarqueDtoIn(
        @NotBlank(message = "Le nom de la marque est obligatoire")
        @Size(min = 3, max = 50, message = "Le nom de la marque doit contenir entre 3 et 50 caractères")
        @Schema(
                description = "Nom de la marque",
                example = "DeLonghi",
                minLength = 3,
                maxLength = 50
        )
        String marque
) {
}
