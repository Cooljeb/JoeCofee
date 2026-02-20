package com.joe.coffee.api.Dto.Out;

    /**
     * DTO de sortie pour une consommation
     */
    public record ConsommationDtoOut(
            Long codeConsommation,
            Byte reglageBroyeur,
            Byte reglageIntensite,
            Long cafeId,
            Long machineACafeId
    ) {

}
