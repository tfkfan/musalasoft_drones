package com.tfkfan.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tfkfan.domain.enumeration.State;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A Drone.
 */
@Entity
@Table(name = "drone")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "new" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Drone implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Size(min = 1, max = 100)
    @Id
    @Column(name = "id", length = 100)
    private String id;

    @Column(name = "weight")
    private Long weight;

    @Column(name = "battery_charge")
    private Integer batteryCharge;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    @Transient
    private boolean isPersisted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "code")
    @ManyToOne(fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "code" }, allowSetters = true)
    private Set<Medication> ids = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "code")
    @ManyToOne(fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "drones" }, allowSetters = true)
    private Model id;

    // jhipster-needle-entity-add-field - JHipster will add fields here

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

    public Set<Medication> getIds() {
        return this.ids;
    }

    public void setIds(Set<Medication> medications) {
        if (this.ids != null) {
            this.ids.forEach(i -> i.setCode(null));
        }
        if (medications != null) {
            medications.forEach(i -> i.setCode(this));
        }
        this.ids = medications;
    }

    public Drone ids(Set<Medication> medications) {
        this.setIds(medications);
        return this;
    }

    public Drone addId(Medication medication) {
        this.ids.add(medication);
        medication.setCode(this);
        return this;
    }

    public Drone removeId(Medication medication) {
        this.ids.remove(medication);
        medication.setCode(null);
        return this;
    }

    public Model getId() {
        return this.id;
    }

    public void setId(Model model) {
        this.id = model;
    }

    public Drone id(Model model) {
        this.setId(model);
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
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
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
