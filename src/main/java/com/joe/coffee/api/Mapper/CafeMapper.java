package com.joe.coffee.api.Mapper;

import com.joe.coffee.api.Dto.In.CafeDtoIn;
import com.joe.coffee.api.Dto.Out.CafeDtoOut;
import com.joe.coffee.api.Entity.Cafe;
import org.hibernate.Hibernate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper d'un café
 */
@Mapper(componentModel = "spring")
public interface CafeMapper {

    Cafe toEntity(CafeDtoIn dto);

    // Transformation Entity → DTO OUT en incluant le commerçant
    @Mapping(source = "commercant.id", target = "commercant")
    @Mapping(source = "commercant.nom", target = "commercantNom")
    @Mapping(expression = "java(getTypeCommercant(cafe))", target = "commercantType")
    CafeDtoOut toDto(Cafe cafe);

    void updateEntityFromDto(CafeDtoIn dto, @MappingTarget Cafe entity);
    //méthode utilitaire qui permet déternlminer le type de commerçant

    default String getTypeCommercant(Cafe cafe) {

        // Déproxyer l'objet pour retrouver sa vraie classe
        Class<?> clazz = Hibernate.getClass(cafe.getCommercant());

        return switch (clazz.getSimpleName()) {
            case "ArtisanTorrefacteur" -> "Artisan";
            case "Distributeur" -> "Distributeur";
            default -> "Inconnu";
        };
    }
}
