package com.tfkfan.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.tfkfan.domain.Drone} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RegisterDroneDTO implements Serializable {

    @NotNull(message = "Serial number is required")
    @Size(min = 1, max = 100)
    private String serialNumber;

    @NotNull(message = "Weight is required")
    private Long weight;

    @NotNull(message = "Battery charge is required")
    private Integer batteryCharge;

    @NotNull(message = "Model is required")
    private RegisterModelDTO model;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Integer getBatteryCharge() {
        return batteryCharge;
    }

    public void setBatteryCharge(Integer batteryCharge) {
        this.batteryCharge = batteryCharge;
    }

    public RegisterModelDTO getModel() {
        return model;
    }

    public void setModel(RegisterModelDTO model) {
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegisterDroneDTO droneDTO)) {
            return false;
        }

        if (this.serialNumber == null) {
            return false;
        }
        return Objects.equals(this.serialNumber, droneDTO.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.serialNumber);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DroneDTO{" +
            "id='" + getSerialNumber() + "'" +
            ", weight=" + getWeight() +
            ", batteryCharge=" + getBatteryCharge() +
            ", model=" + getModel() +
            "}";
    }
}
