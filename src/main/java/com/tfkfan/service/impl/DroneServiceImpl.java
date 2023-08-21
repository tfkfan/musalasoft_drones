package com.tfkfan.service.impl;

import com.tfkfan.config.Constants;
import com.tfkfan.domain.Drone;
import com.tfkfan.domain.Medication;
import com.tfkfan.domain.enumeration.State;
import com.tfkfan.repository.DroneRepository;
import com.tfkfan.repository.MedicationRepository;
import com.tfkfan.service.DroneService;
import com.tfkfan.service.MedicationService;
import com.tfkfan.service.dto.DroneDTO;
import com.tfkfan.service.dto.LoadDTO;
import com.tfkfan.service.mapper.DroneMapper;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Drone}.
 */
@Service
@Transactional
public class DroneServiceImpl implements DroneService {

    private final Logger log = LoggerFactory.getLogger(DroneServiceImpl.class);

    private final DroneRepository droneRepository;

    private final DroneMapper droneMapper;

    public DroneServiceImpl(DroneRepository droneRepository, DroneMapper droneMapper) {
        this.droneRepository = droneRepository;
        this.droneMapper = droneMapper;
    }

    @Override
    public DroneDTO register(DroneDTO droneDTO) {
        log.debug("Request to save Drone : {}", droneDTO);
        Drone drone = droneMapper.toEntity(droneDTO);
        drone.setState(State.IDLE);
        drone.setWeight(0L);
        drone = droneRepository.save(drone);
        return droneMapper.toDto(drone);
    }

    @Override
    public DroneDTO update(DroneDTO droneDTO) {
        log.debug("Request to update Drone : {}", droneDTO);
        Drone drone = droneMapper.toEntity(droneDTO);
        drone.setIsPersisted();
        drone = droneRepository.save(drone);
        return droneMapper.toDto(drone);
    }

    @Override
    public DroneDTO updateEntity(Drone drone) {
        drone.setIsPersisted();
        drone = droneRepository.save(drone);
        return droneMapper.toDto(drone);
    }

    @Override
    public Optional<DroneDTO> partialUpdate(DroneDTO droneDTO) {
        log.debug("Request to partially update Drone : {}", droneDTO);

        return droneRepository
            .findById(droneDTO.getId())
            .map(existingDrone -> {
                droneMapper.partialUpdate(existingDrone, droneDTO);

                return existingDrone;
            })
            .map(droneRepository::save)
            .map(droneMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DroneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Drones");
        return droneRepository.findAll(pageable).map(droneMapper::toDto);
    }

    @Override
    public List<DroneDTO> findAllAvailable() {
        log.debug("Request to get all available Drones");
        return droneRepository.findAllByState(State.IDLE).stream().map(droneMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DroneDTO> findOne(String id) {
        log.debug("Request to get Drone : {}", id);
        return droneRepository.findById(id).map(droneMapper::toDto);
    }

    @Override
    public DroneDTO findOneRequired(String id) {
        log.debug("Request to get Drone : {}", id);
        return droneRepository
            .findById(id)
            .map(droneMapper::toDto)
            .orElseThrow(() -> new RuntimeException("Drone with the given serial number not found"));
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Drone : {}", id);
        droneRepository.deleteById(id);
    }
}
