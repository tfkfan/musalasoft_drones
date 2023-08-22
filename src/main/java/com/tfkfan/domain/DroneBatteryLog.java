package com.tfkfan.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.Instant;

/**
 * A Drone.
 */
@Entity
@Table(name = "drone_battery_log")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DroneBatteryLog extends AbstractAuditLogEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "battery_charge", nullable = false)
    @Max(100)
    @Min(0)
    private Integer batteryCharge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drone_id")
    private Drone drone;

    public DroneBatteryLog() {}

    public DroneBatteryLog(String managedBy, Instant timestamp, Integer batteryCharge, Drone drone) {
        super(managedBy, timestamp);
        this.batteryCharge = batteryCharge;
        this.drone = drone;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBatteryCharge() {
        return batteryCharge;
    }

    public void setBatteryCharge(Integer batteryCharge) {
        this.batteryCharge = batteryCharge;
    }

    public Drone getDrone() {
        return drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }
}
