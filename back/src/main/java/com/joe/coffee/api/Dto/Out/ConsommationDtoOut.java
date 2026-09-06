package com.joe.coffee.api.Dto.Out;

    /**
     * DTO de sortie pour une consommation
     */
    public record ConsommationDtoOut(
            Integer codeConsommation,
            Byte reglageBroyeur,
            Byte reglageIntensite,
            Integer cafeId,
            Integer machineACafeId
    ) {

}
