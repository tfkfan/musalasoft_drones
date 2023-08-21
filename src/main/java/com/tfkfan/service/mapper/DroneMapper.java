package com.tfkfan.service.mapper;

import com.tfkfan.domain.Drone;
import com.tfkfan.service.dto.DroneDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Drone} and its DTO {@link DroneDTO}.
 */
@Mapper(componentModel = "spring")
public interface DroneMapper extends EntityMapper<DroneDTO, Drone> {
    DroneDTO toDto(Drone s);
}
