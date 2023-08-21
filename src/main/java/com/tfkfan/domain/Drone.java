package com.tfkfan.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tfkfan.domain.enumeration.State;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.springframework.data.domain.Persistable;

/**
 * A Drone.
 */
@Entity
@Table(name = "drone")
@JsonIgnoreProperties(value = { "new" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Drone implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Size(min = 1, max = 100)
    @Id
    @Column(name = "id", length = 100)
    private String id;

    @Column(name = "weight", nullable = false)
    private Long weight;

    @Column(name = "battery_charge", nullable = false)
    private Integer batteryCharge;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @Transient
    private boolean isPersisted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "drone")
    private Set<MedicationLoad> medicationLoads = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "drones" }, allowSetters = true)
    private Model model;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Set<MedicationLoad> getMedicationLoads() {
        return medicationLoads;
    }

    public void setMedicationLoads(Set<MedicationLoad> medicationLoads) {
        this.medicationLoads = medicationLoads;
    }

    public String getId() {
        return this.id;
    }

    public Drone id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getWeight() {
        return this.weight;
    }

    public Drone weight(Long weight) {
        this.setWeight(weight);
        return this;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Integer getBatteryCharge() {
        return this.batteryCharge;
    }

    public Drone batteryCharge(Integer batteryCharge) {
        this.setBatteryCharge(batteryCharge);
        return this;
    }

    public void setBatteryCharge(Integer batteryCharge) {
        this.batteryCharge = batteryCharge;
    }

    public State getState() {
        return this.state;
    }

    public Drone state(State state) {
        this.setState(state);
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Drone setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Model getModel() {
        return this.model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Drone model(Model model) {
        this.setModel(model);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Drone)) {
            return false;
        }
        return id != null && id.equals(((Drone) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Drone{" +
            "id=" + getId() +
            ", weight=" + getWeight() +
            ", batteryCharge=" + getBatteryCharge() +
            ", state='" + getState() + "'" +
            "}";
    }
}
