package com.joe.coffee.api.Mapper;

import com.joe.coffee.api.Dto.In.ConsommationDtoIn;
import com.joe.coffee.api.Entity.Consommation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


/**
 * Mapper d'une consommation
 */
@Mapper(componentModel = "spring")
public interface ConsommationMapper {

    @Mapping(source = "cafe.id", target = "cafeId")
    @Mapping(source = "machineACafe.id", target = "machineACafeId")
    ConsommationDtoIn toDto(Consommation entity);

    // Pas de toEntity direct, on gère l'ID dans le service
    //donc pas de toEntity directement

    void updateEntityFromDto(ConsommationDtoIn dto, @MappingTarget Consommation entity);
}
