package com.joe.coffee.api.Dto.In;

import com.joe.coffee.api.Enum.LabelCafe;
import com.joe.coffee.api.Enum.TypeCafe;
import jakarta.validation.constraints.*;

/**
 * Record correspondant au DTO d'entrée d'un café
 * @param nomCafe
 * @param description
 * @param intensite
 * @param typeCafe
 * @param labelCafe
 */
public record CafeDtoIn(
        @NotBlank(message = "Le nom du café est obligatoire")
        @Size(min = 3, max = 50, message = "Le nom du café doit contenir entre 3 et 50 caractères")
        String nomCafe,

        @NotBlank(message = "La description est obligatoire")
        @Size(min = 5, max = 255, message = "La description doit contenir entre 5 et 255 caractères")
        String description,

        @NotNull(message = "L'intensité est obligatoire")
        @Min(value = 1, message = "L'intensité minimale est 1")
        @Max(value = 10, message = "L'intensité maximale est 10")
        Integer intensite,

        @NotNull(message = "Le type de café est obligatoire")
        TypeCafe typeCafe,

        LabelCafe labelCafe
) {
}
