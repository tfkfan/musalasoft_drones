package com.tfkfan.service.mapper;

import com.tfkfan.domain.Drone;
import com.tfkfan.domain.Medication;
import com.tfkfan.domain.MedicationLoad;
import com.tfkfan.service.dto.DroneDTO;
import com.tfkfan.service.dto.MedicationDTO;
import com.tfkfan.service.dto.MedicationLoadDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Medication} and its DTO {@link MedicationDTO}.
 */
@Mapper(componentModel = "spring")
public interface MedicationLoadMapper extends EntityMapper<MedicationLoadDTO, MedicationLoad> {
    @Named("medicationCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "code", source = "code")
    MedicationDTO toDtoMedicationCode(Medication medication);

    @Named("droneId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DroneDTO toDtoDroneId(Drone drone);
}
