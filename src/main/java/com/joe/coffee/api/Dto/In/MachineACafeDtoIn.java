package com.joe.coffee.api.Dto.In;


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

        @NotBlank
        String nomCommercial,

        @NotBlank
        String referenceCommerciale,
        @NotBlank
        String description,

        @NotNull
        Integer marqueId


) {
}
