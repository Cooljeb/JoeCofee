package com.joe.coffee.api.Dto.Out;

/**
 * DTO de sortie d'une machine à Café
 * @param id
 * @param nomCommercial
 * @param referenceCommerciale
 * @param description
 * @param marque
 */
public record MachineACafeDtoOut(

        Long id,
        String nomCommercial,
        String referenceCommerciale,
        String description,
        MarqueDtoOut marque
) {
}
