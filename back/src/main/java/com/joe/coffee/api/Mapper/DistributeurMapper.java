package com.joe.coffee.api.Mapper;

import com.joe.coffee.api.Dto.In.DistributeurDtoIn;
import com.joe.coffee.api.Dto.Out.DistributeurDtoOut;
import com.joe.coffee.api.Entity.Distributeur;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Mapper d'un distributeur
 */
@Mapper(componentModel = "spring")
public interface DistributeurMapper {

    Distributeur toEntity(DistributeurDtoIn dto);

    DistributeurDtoOut toDto(Distributeur entity);

    void updateEntityFromDto(DistributeurDtoIn dto, @MappingTarget Distributeur entity);
}
