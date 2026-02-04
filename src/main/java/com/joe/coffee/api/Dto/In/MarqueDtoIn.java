package com.joe.coffee.api.Dto.In;

import jakarta.validation.constraints.NotBlank;

/**
 * Record correspondant au DTO d'entrée d'une marque
 * @param Marque
 */
public record MarqueDtoIn(
        @NotBlank
        String Marque
) {
}
