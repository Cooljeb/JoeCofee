package com.joe.coffee.api.Dto.Out;

/**
 * DTO de sortie d'un distributeur
 */
public record DistributeurDtoOut(

        Long id,
        String nom,
        String nomDuGroupeDeDistribution
) {
}
