package com.tfkfan.service.mapper;

import com.tfkfan.domain.Drone;
import com.tfkfan.service.dto.DroneDTO;
import com.tfkfan.service.dto.RegisterDroneDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Drone} and its DTO {@link DroneDTO}.
 */
@Mapper(componentModel = "spring")
public interface DroneMapper extends EntityMapper<DroneDTO, Drone> {
    @Mapping(source = "id", target = "serialNumber")
    DroneDTO toDto(Drone s);

    @Override
    @Mapping(source = "serialNumber", target = "id")
    Drone toEntity(DroneDTO dto);

    @Mapping(source = "serialNumber", target = "id")
    Drone toEntity(RegisterDroneDTO dto);
}
