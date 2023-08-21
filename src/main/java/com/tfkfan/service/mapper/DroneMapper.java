package com.tfkfan.service.mapper;

import com.tfkfan.domain.Drone;
import com.tfkfan.domain.Model;
import com.tfkfan.service.dto.DroneDTO;
import com.tfkfan.service.dto.ModelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Drone} and its DTO {@link DroneDTO}.
 */
@Mapper(componentModel = "spring")
public interface DroneMapper extends EntityMapper<DroneDTO, Drone> {
    @Mapping(target = "id", source = "id", qualifiedByName = "modelId")
    DroneDTO toDto(Drone s);

    @Named("modelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ModelDTO toDtoModelId(Model model);
}
