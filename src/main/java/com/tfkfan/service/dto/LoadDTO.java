package com.tfkfan.service.dto;

import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link com.tfkfan.domain.Drone} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LoadDTO implements Serializable {

    @NotEmpty(message = "Add medications before loading to drone")
    private List<DroneMedicationLoadDTO> medicationLoads;

    public List<DroneMedicationLoadDTO> getMedicationLoads() {
        return medicationLoads;
    }

    public void setMedicationLoads(List<DroneMedicationLoadDTO> medicationLoads) {
        this.medicationLoads = medicationLoads;
    }
}
