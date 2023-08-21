package com.tfkfan.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Model.
 */
@Entity
@Table(name = "model")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Model implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "weight_limit")
    private Long weightLimit;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ids", "id" }, allowSetters = true)
    private Set<Drone> drones = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Model id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Model title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getWeightLimit() {
        return this.weightLimit;
    }

    public Model weightLimit(Long weightLimit) {
        this.setWeightLimit(weightLimit);
        return this;
    }

    public void setWeightLimit(Long weightLimit) {
        this.weightLimit = weightLimit;
    }

    public Set<Drone> getDrones() {
        return this.drones;
    }

    public void setDrones(Set<Drone> drones) {
        if (this.drones != null) {
            this.drones.forEach(i -> i.setId(null));
        }
        if (drones != null) {
            drones.forEach(i -> i.setId(this));
        }
        this.drones = drones;
    }

    public Model drones(Set<Drone> drones) {
        this.setDrones(drones);
        return this;
    }

    public Model addDrone(Drone drone) {
        this.drones.add(drone);
        drone.setId(this);
        return this;
    }

    public Model removeDrone(Drone drone) {
        this.drones.remove(drone);
        drone.setId(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Model)) {
            return false;
        }
        return id != null && id.equals(((Model) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Model{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", weightLimit=" + getWeightLimit() +
            "}";
    }
}
