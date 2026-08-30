package com.joe.coffee.api.Dto.Out;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO de sortie d'une machine à Cafe
 * @param id
 * @param nomCommercial
 * @param referenceCommerciale
 * @param description
 * @param marque
 */
public record MachineACafeDtoOut(

        @Schema(description = "Identifiant de la machine à café", example = "1")
        Integer id,

        @Schema(description = "Nom commercial de la machine à café", example = "Magnifica S")
        String nomCommercial,

        @Schema(description = "Référence commerciale de la machine", example = "ECAM22.110.B")
        String referenceCommerciale,

        @Schema(description = "Description de la machine à café", example = "Machine automatique avec broyeur intégré")
        String description,

        @Schema(description = "Nom de la marque associée", example = "De'Longhi")
        String marque
) {
}
