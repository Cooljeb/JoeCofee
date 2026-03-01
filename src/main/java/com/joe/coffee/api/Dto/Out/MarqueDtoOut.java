package com.joe.coffee.api.Dto.Out;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Record correspondant au DTO de sortie d'une marque
 * @param id
 * @param marque
 */
@Schema(description = "Représentation détaillée d'une marque pour les retours API")
public record MarqueDtoOut(

        @Schema(description = "Identifiant unique d'une marque", example = "1")
        Integer id,

        @Schema(description = "Nom de la marque", example = "DeLonghi")
        String marque
) {
}
