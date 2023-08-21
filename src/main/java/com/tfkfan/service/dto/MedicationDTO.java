package com.tfkfan.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.tfkfan.domain.Medication} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MedicationDTO implements Serializable {

    @Pattern(regexp = "^[A-Z0-9_]+$")
    private String code;

    @Pattern(regexp = "^[a-zA-Z_0-9-]+$")
    private String name;

    private Long weight;

    private String picture;

    private DroneDTO code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public DroneDTO getCode() {
        return code;
    }

    public void setCode(DroneDTO code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicationDTO)) {
            return false;
        }

        MedicationDTO medicationDTO = (MedicationDTO) o;
        if (this.code == null) {
            return false;
        }
        return Objects.equals(this.code, medicationDTO.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.code);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedicationDTO{" +
            "code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", weight=" + getWeight() +
            ", picture='" + getPicture() + "'" +
            ", code=" + getCode() +
            "}";
    }
}
