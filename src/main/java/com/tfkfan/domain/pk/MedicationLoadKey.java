package com.tfkfan.domain.pk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MedicationLoadKey implements Serializable {

    @Column(name = "drone_id")
    private String droneId;

    @Column(name = "medication_code")
    private String medicationCode;

    public MedicationLoadKey() {}

    public MedicationLoadKey(String droneId, String medicationCode) {
        this.droneId = droneId;
        this.medicationCode = medicationCode;
    }

    public String getDroneId() {
        return droneId;
    }

    public void setDroneId(String droneId) {
        this.droneId = droneId;
    }

    public String getMedicationCode() {
        return medicationCode;
    }

    public void setMedicationCode(String medicationCode) {
        this.medicationCode = medicationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicationLoadKey that = (MedicationLoadKey) o;
        return Objects.equals(droneId, that.droneId) && Objects.equals(medicationCode, that.medicationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(droneId, medicationCode);
    }
}
