package com.joe.coffee.api.Dto.Out;

import com.joe.coffee.api.Enum.LabelCafe;
import com.joe.coffee.api.Enum.TypeCafe;

/**
 * Record correspondant au DTO de sortie d'un café
 * @param id
 * @param nomCafe
 * @param description
 * @param intensite
 * @param typeCafe
 * @param labelCafe
 */
public record CafeDtoOut(
        Long id,
        String nomCafe,
        String description,
        Integer intensite,
        TypeCafe typeCafe,
        LabelCafe labelCafe
) {
}
