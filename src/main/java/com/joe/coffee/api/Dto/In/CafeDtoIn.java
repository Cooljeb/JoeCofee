package com.joe.coffee.api.Dto.In;

import com.joe.coffee.api.Enum.LabelCafe;
import com.joe.coffee.api.Enum.TypeCafe;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Record correspondant au DTO d'entrée d'un café
 * @param nomCafe
 * @param description
 * @param intensite
 * @param typeCafe
 * @param labelCafe
 */
public record CafeDtoIn(
        @NotBlank
        String nomCafe,

        @NotBlank
        String description,

        @NotNull
        Integer intensite,

        @NotNull
        TypeCafe typeCafe,

        LabelCafe labelCafe
) {
}
