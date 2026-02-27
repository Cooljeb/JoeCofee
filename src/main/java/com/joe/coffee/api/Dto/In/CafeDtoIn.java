package com.joe.coffee.api.Dto.In;

import com.joe.coffee.api.Enum.LabelCafe;
import com.joe.coffee.api.Enum.TypeCafe;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

/**
 * Record correspondant au DTO d'entrée d'un café
 * @param nomCafe
 * @param description
 * @param typeCafe
 * @param labelCafe
 */
public record CafeDtoIn(

        @NotBlank(message = "Le nom du café est obligatoire")
        @Size(min = 3, max = 50, message = "Le nom du café doit contenir entre 3 et 50 caractères")
        @Schema(
                description = "Nom du café",
                example = "Colombie Supremo",
                minLength = 3,
                maxLength = 50
        )
        String nomCafe,

        @NotBlank(message = "La description est obligatoire")
        @Size(min = 5, max = 255, message = "La description doit contenir entre 5 et 255 caractères")
        @Schema(
                description = "Description du café",
                example = "Un café doux aux notes de chocolat et noisette",
                minLength = 5,
                maxLength = 255
        )
        String description,

        @NotNull(message = "Le type de café est obligatoire")
        @Schema(
                description = "Type de café",
                example = "ARABICA"
        )
        TypeCafe typeCafe,

        @Schema(
                description = "Label du café",
                example = "BIO"
        )
        LabelCafe labelCafe
) {
}
