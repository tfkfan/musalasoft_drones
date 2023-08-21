package com.tfkfan.service.mapper;

import com.tfkfan.domain.Drone;
import com.tfkfan.domain.Medication;
import com.tfkfan.service.dto.DroneDTO;
import com.tfkfan.service.dto.MedicationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Medication} and its DTO {@link MedicationDTO}.
 */
@Mapper(componentModel = "spring")
public interface MedicationMapper extends EntityMapper<MedicationDTO, Medication> {
    @Mapping(target = "code", source = "code", qualifiedByName = "droneId")
    MedicationDTO toDto(Medication s);

    @Named("droneId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DroneDTO toDtoDroneId(Drone drone);
}
