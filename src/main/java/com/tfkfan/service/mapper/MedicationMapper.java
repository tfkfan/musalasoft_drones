package com.tfkfan.service.mapper;

import com.tfkfan.domain.Medication;
import com.tfkfan.service.dto.MedicationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Medication} and its DTO {@link MedicationDTO}.
 */
@Mapper(componentModel = "spring")
public interface MedicationMapper extends EntityMapper<MedicationDTO, Medication> {}
