package com.tfkfan.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tfkfan.domain.pk.MedicationLoadKey;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Medication.
 */
@Entity
@Table(name = "medication_load")
@JsonIgnoreProperties(value = { "new" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MedicationLoad implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private MedicationLoadKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("droneId")
    private Drone drone;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("medicationCode")
    private Medication medication;

    @Column(name = "medication_quantity", nullable = false)
    private Long medicationQuantity;

    public MedicationLoad() {}

    public MedicationLoad(Drone drone, Medication medication, Long medicationQuantity) {
        this.drone = drone;
        this.medication = medication;
        this.medicationQuantity = medicationQuantity;
        this.id = new MedicationLoadKey(drone.getId(), medication.getId());
    }

    public MedicationLoadKey getId() {
        return id;
    }

    public void setId(MedicationLoadKey id) {
        this.id = id;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public Long getMedicationQuantity() {
        return medicationQuantity;
    }

    public void setMedicationQuantity(Long medicationQuantity) {
        this.medicationQuantity = medicationQuantity;
    }

    public Drone getDrone() {
        return this.drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    public MedicationLoad drone(Drone drone) {
        this.setDrone(drone);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicationLoad)) {
            return false;
        }
        return id != null && id.equals(((MedicationLoad) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    // prettier-ignore

}
