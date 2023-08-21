package com.tfkfan.service.dto;

import com.tfkfan.domain.enumeration.State;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.tfkfan.domain.Drone} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DroneDTO implements Serializable {

    @Size(min = 1, max = 100)
    private String serialNumber;

    private Long weight;

    private Integer batteryCharge;

    private State state;

    private ModelDTO model;

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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public ModelDTO getModel() {
        return model;
    }

    public void setModel(ModelDTO model) {
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DroneDTO droneDTO)) {
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
            ", state='" + getState() + "'" +
            ", model=" + getModel() +
            "}";
    }
}
