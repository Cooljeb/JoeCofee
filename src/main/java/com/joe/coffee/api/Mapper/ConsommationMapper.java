package com.joe.coffee.api.Mapper;

import com.joe.coffee.api.Dto.In.ConsommationDtoIn;
import com.joe.coffee.api.Dto.Out.ConsommationDtoOut;
import com.joe.coffee.api.Entity.Consommation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper d'une consommation.
 */
@Mapper(componentModel = "spring")
public interface ConsommationMapper {

    @Mapping(source = "cafe.id", target = "cafeId")
    @Mapping(source = "machineACafe.id", target = "machineACafeId")
    ConsommationDtoOut toDto(Consommation entity);

    @Mapping(target = "codeConsommation", ignore = true)
    @Mapping(target = "cafe", ignore = true)
    @Mapping(target = "machineACafe", ignore = true)
    void updateEntityFromDto(ConsommationDtoIn dto, @MappingTarget Consommation entity);
}
