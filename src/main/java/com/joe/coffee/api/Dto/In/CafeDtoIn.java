package com.joe.coffee.api.Dto.In;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.joe.coffee.api.Enum.LabelCafe;
import com.joe.coffee.api.Enum.TypeCafe;
import com.joe.coffee.api.Utils.LabelCafeDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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

        @JsonSetter(nulls = Nulls.SKIP)// si absent, ne touche pas à la valeur
        @JsonDeserialize(using = LabelCafeDeserializer.class) // transforme "" en null
        @Nullable
        @Schema(
                description = "Label du café",
                example = "BIO"
        )
        LabelCafe labelCafe,

        @NotNull(message = "L'identifiant du commerçant est obligatoire")
        @Schema(description = "Identifiant du commerçant qui vend le café",
                example = "2"
        )
        Integer commercant
) {
}
