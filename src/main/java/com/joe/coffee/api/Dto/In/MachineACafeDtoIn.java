package com.joe.coffee.api.Dto.In;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO d'entée d'une machine à Café
 * @param nomCommercial
 * @param referenceCommerciale
 * @param description
 * @param marqueId
 */
public record MachineACafeDtoIn(

        @Schema(description = "Nom commercial de la machine à café", example = "Magnifica S")
        @NotBlank
        String nomCommercial,

        @Schema(description = "Référence commerciale unique de la machine", example = "ECAM22.110.B")
        @NotBlank
        String referenceCommerciale,

        @Schema(description = "Description de la machine à café", example = "Machine automatique avec broyeur intégré")
        @NotBlank
        String description,

        @Schema(description = "Identifiant de la marque associée", example = "1")
        @NotNull
        Integer marqueId


) {
}
