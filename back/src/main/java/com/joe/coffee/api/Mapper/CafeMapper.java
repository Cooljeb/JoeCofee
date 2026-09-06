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

    // DTO → Entity
    default Cafe toEntity(CafeDtoIn dto){

            if (dto == null) return null;
            Cafe cafe = new Cafe();
            cafe.setNomCafe(dto.nomCafe());
            cafe.setDescription(dto.description());
            cafe.setLabelCafe(dto.labelCafe()); // Nullable ok
            cafe.setTypeCafe(dto.typeCafe());
            // Le commerçant sera défini dans le service : cafe.setCommercant(...)
            return cafe;
    }

    // Entity → DTO OUT
    @Mapping(source = "commercant.id", target = "commercant")
    @Mapping(source = "commercant.nom", target = "commercantNom")
    @Mapping(expression = "java(getTypeCommercant(cafe))", target = "commercantType")
    CafeDtoOut toDto(Cafe cafe);

    @Mapping(target = "commercant", ignore = true)
    void updateEntityFromDto(CafeDtoIn dto, @MappingTarget Cafe entity);

    // Méthode utilitaire pour déterminer le type de commerçant
    default String getTypeCommercant(Cafe cafe) {
        Class<?> clazz = Hibernate.getClass(cafe.getCommercant());
        return switch (clazz.getSimpleName()) {
            case "ArtisanTorrefacteur" -> "Artisan";
            case "Distributeur" -> "Distributeur";
            default -> "Inconnu";
        };
    }
}
