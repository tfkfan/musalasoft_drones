package com.tfkfan.service.dto;

import jakarta.validation.constraints.NotEmpty;

/**
 * A DTO for the {@link com.tfkfan.domain.Drone} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MedicationLoadDTO {

    @NotEmpty(message = "Field is required")
    private Long quantity;

    @NotEmpty(message = "Field is required")
    private MedicationDTO medication;

    @NotEmpty(message = "Field is required")
    private DroneDTO drone;

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

    public DroneDTO getDrone() {
        return drone;
    }

    public void setDrone(DroneDTO drone) {
        this.drone = drone;
    }
}
