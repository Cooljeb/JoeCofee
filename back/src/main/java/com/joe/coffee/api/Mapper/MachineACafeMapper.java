package com.joe.coffee.api.Mapper;

import com.joe.coffee.api.Dto.In.MachineACafeDtoIn;
import com.joe.coffee.api.Dto.Out.MachineACafeDtoOut;
import com.joe.coffee.api.Entity.MachineACafe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface MachineACafeMapper {

    MachineACafe toEntity(MachineACafeDtoIn dto);

    @Mapping(source = "marque.id", target = "marque")
    MachineACafeDtoOut toDto(MachineACafe entity);

    void updateEntityFromDto(MachineACafeDtoIn dto, @MappingTarget MachineACafe entity);
}
