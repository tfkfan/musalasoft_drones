package com.tfkfan.repository;

import com.tfkfan.domain.Drone;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Drone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {}
