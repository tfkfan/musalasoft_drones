package com.tfkfan.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.springframework.data.domain.Persistable;

/**
 * A Medication.
 */
@Entity
@Table(name = "medication")
@JsonIgnoreProperties(value = { "new" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Medication implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @Pattern(regexp = "^[A-Z0-9_]+$")
    @Id
    @Column(name = "code", nullable = false)
    private String code;

    @Pattern(regexp = "^[a-zA-Z_0-9-]+$")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "weight", nullable = false)
    private Long weight;

    @Column(name = "picture", nullable = false)
    private String picture;

    @Transient
    private boolean isPersisted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "medication")
    private Set<MedicationLoad> medicationLoads = new HashSet<>();

    public Set<MedicationLoad> getMedicationLoads() {
        return medicationLoads;
    }

    public void setMedicationLoads(Set<MedicationLoad> medicationLoads) {
        this.medicationLoads = medicationLoads;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getCode() {
        return this.code;
    }

    public Medication code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Medication name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWeight() {
        return this.weight;
    }

    public Medication weight(Long weight) {
        this.setWeight(weight);
        return this;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public String getPicture() {
        return this.picture;
    }

    public Medication picture(String picture) {
        this.setPicture(picture);
        return this;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    @Override
    public String getId() {
        return this.code;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Medication setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Medication)) {
            return false;
        }
        return code != null && code.equals(((Medication) o).code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Medication{" +
            "code=" + getCode() +
            ", name='" + getName() + "'" +
            ", weight=" + getWeight() +
            ", picture='" + getPicture() + "'" +
            "}";
    }
}
