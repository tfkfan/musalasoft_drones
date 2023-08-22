package com.tfkfan.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

/**
 * Base abstract class for entities which will hold definitions for created, last modified, created by,
 * last modified by attributes.
 */
@MappedSuperclass
@JsonIgnoreProperties(value = { "managedBy", "timestamp" }, allowGetters = true)
public abstract class AbstractAuditLogEntity<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public abstract T getId();

    @Column(name = "managed_by", length = 50, nullable = false)
    private String managedBy;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    public AbstractAuditLogEntity() {}

    public AbstractAuditLogEntity(String managedBy, Instant timestamp) {
        this.managedBy = managedBy;
        this.timestamp = timestamp;
    }

    public String getManagedBy() {
        return managedBy;
    }

    public void setManagedBy(String lastModifiedBy) {
        this.managedBy = lastModifiedBy;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
