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
    private String id;

    private Long weight;

    private Integer batteryCharge;

    private State state;

    private ModelDTO id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ModelDTO getId() {
        return id;
    }

    public void setId(ModelDTO id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DroneDTO)) {
            return false;
        }

        DroneDTO droneDTO = (DroneDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, droneDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DroneDTO{" +
            "id='" + getId() + "'" +
            ", weight=" + getWeight() +
            ", batteryCharge=" + getBatteryCharge() +
            ", state='" + getState() + "'" +
            ", id=" + getId() +
            "}";
    }
}
