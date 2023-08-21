package com.tfkfan.repository;

import com.tfkfan.domain.MedicationLoad;
import com.tfkfan.domain.pk.MedicationLoadKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Drone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicationLoadRepository extends JpaRepository<MedicationLoad, MedicationLoadKey> {}
