package com.tfkfan.service.mapper;

import com.tfkfan.domain.Medication;
import com.tfkfan.service.dto.MedicationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Medication} and its DTO {@link MedicationDTO}.
 */
@Mapper(componentModel = "spring")
public interface MedicationMapper extends EntityMapper<MedicationDTO, Medication> {
    default Medication toEntityWithRequiredFields(MedicationDTO dto, Boolean isRequired) {
        if (isRequired) {
            if (dto.getName() == null) throw new RuntimeException("Name is required");
            if (dto.getPicture() == null) throw new RuntimeException("Picture is required");
            if (dto.getWeight() == null) throw new RuntimeException("Weight is required");
        }
        return this.toEntity(dto);
    }
}
