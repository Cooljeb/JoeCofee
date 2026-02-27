package com.joe.coffee.api.Dto.Out;

import com.joe.coffee.api.Enum.LabelCafe;
import com.joe.coffee.api.Enum.TypeCafe;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Record correspondant au DTO de sortie d'un café
 * @param id
 * @param nomCafe
 * @param description
 * @param typeCafe
 * @param labelCafe
 */
@Schema(description = "Représentation détaillée d'un café pour les retours API")
public record CafeDtoOut(

        @Schema(description = "Identifiant unique du café", example = "1")
        Long id,

        @Schema(description = "Nom du café", example = "Colombie Supremo")
        String nomCafe,

        @Schema(description = "Description du café", example = "Un café doux aux notes de chocolat et noisette")
        String description,

        @Schema(description = "type du café", example = "ARABICA")
        TypeCafe typeCafe,

        @Schema(description = "label du café", example = "BIO")
        LabelCafe labelCafe
) {
}
