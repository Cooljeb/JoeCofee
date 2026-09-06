package com.joe.coffee.api.Dto.In;

import jakarta.validation.constraints.NotNull;

/**
 * DTO d'entrée pour créer ou mettre à jour une consommation
 */
public record ConsommationDtoIn(

        @NotNull
        Byte reglageBroyeur,
        @NotNull
        Byte reglageIntensite,
        @NotNull
        Integer cafeId,
        @NotNull
        Integer machineACafeId
) {
}
