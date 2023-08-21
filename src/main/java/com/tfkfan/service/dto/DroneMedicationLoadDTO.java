package com.tfkfan.service.dto;

import jakarta.validation.constraints.NotEmpty;

/**
 * A DTO for the {@link com.tfkfan.domain.Drone} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DroneMedicationLoadDTO {

    @NotEmpty(message = "Field is required")
    private Long quantity;

    @NotEmpty(message = "Field is required")
    private MedicationDTO medication;

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public MedicationDTO getMedication() {
        return medication;
    }

    public void setMedication(MedicationDTO medication) {
        this.medication = medication;
    }
}
