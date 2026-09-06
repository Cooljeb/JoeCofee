package com.joe.coffee.api.Mapper;

import com.joe.coffee.api.Dto.In.MarqueDtoIn;
import com.joe.coffee.api.Dto.Out.MarqueDtoOut;
import com.joe.coffee.api.Entity.Marque;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Mapper d'une marque
 */
@Mapper(componentModel = "spring")
public interface MarqueMapper {

    Marque toEntity(MarqueDtoIn dto);

    MarqueDtoOut toDto(Marque entity);

    void updateEntityFromDto(MarqueDtoIn dto, @MappingTarget Marque entity);
}
