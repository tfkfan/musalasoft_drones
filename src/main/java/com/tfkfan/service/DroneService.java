package com.tfkfan.service;

import com.tfkfan.domain.Drone;
import com.tfkfan.service.dto.DroneDTO;
import com.tfkfan.service.dto.LoadDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.tfkfan.domain.Drone}.
 */
public interface DroneService {
    /**
     * Save a drone.
     *
     * @param droneDTO the entity to save.
     * @return the persisted entity.
     */
    DroneDTO register(DroneDTO droneDTO);

    /**
     * Updates a drone.
     *
     * @param droneDTO the entity to update.
     * @return the persisted entity.
     */
    DroneDTO update(DroneDTO droneDTO);
    DroneDTO updateEntity(Drone drone);
    /**
     * Partially updates a drone.
     *
     * @param droneDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DroneDTO> partialUpdate(DroneDTO droneDTO);

    /**
     * Get all the drones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DroneDTO> findAll(Pageable pageable);

    List<DroneDTO> findAllAvailable();
    /**
     * Get the "id" drone.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DroneDTO> findOne(String id);

    /**
     * Get the "id" drone.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    DroneDTO findOneRequired(String id);

    /**
     * Delete the "id" drone.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
