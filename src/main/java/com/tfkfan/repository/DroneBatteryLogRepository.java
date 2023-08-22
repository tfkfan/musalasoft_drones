package com.tfkfan.repository;

import com.tfkfan.domain.DroneBatteryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Drone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DroneBatteryLogRepository extends JpaRepository<DroneBatteryLog, Long> {}
