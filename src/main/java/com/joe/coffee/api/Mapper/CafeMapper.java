package com.joe.coffee.api.Mapper;

import com.joe.coffee.api.Dto.In.CafeDtoIn;
import com.joe.coffee.api.Dto.Out.CafeDtoOut;
import com.joe.coffee.api.Entity.Cafe;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Mapper d'un café
 */
@Mapper(componentModel = "spring")
public interface CafeMapper {

    Cafe toEntity(CafeDtoIn dto);

    CafeDtoOut toDto(Cafe entity);

    void updateEntityFromDto(CafeDtoIn dto, @MappingTarget Cafe entity);
}
