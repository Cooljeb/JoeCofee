package com.joe.coffee.api.Mapper;

import com.joe.coffee.api.Dto.In.ArtisanTorrefacteurDtoIn;
import com.joe.coffee.api.Dto.Out.ArtisanTorrefacteurDtoOut;
import com.joe.coffee.api.Entity.ArtisanTorrefacteur;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Mapper d'un café
 */
@Mapper(componentModel = "spring")
public interface ArtisanTorrefacteurMapper {

    ArtisanTorrefacteur toEntity(ArtisanTorrefacteurDtoIn dto);

    ArtisanTorrefacteurDtoOut toDto(ArtisanTorrefacteur entity);

    void updateEntityFromDto(ArtisanTorrefacteurDtoIn dto, @MappingTarget ArtisanTorrefacteur entity);
}
