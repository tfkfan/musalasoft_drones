package com.tfkfan.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tfkfan.domain.enumeration.State;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.tfkfan.domain.Drone} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LoadDTO implements Serializable {

    @NotEmpty(message = "Add medications before loading to drone")
    private List<MedicationDTO> medications;

    public List<MedicationDTO> getMedications() {
        return medications;
    }

    public void setMedications(List<MedicationDTO> medications) {
        this.medications = medications;
    }
}
