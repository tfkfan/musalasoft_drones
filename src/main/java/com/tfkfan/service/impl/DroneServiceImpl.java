package com.tfkfan.service.impl;

import com.tfkfan.domain.Drone;
import com.tfkfan.repository.DroneRepository;
import com.tfkfan.service.DroneService;
import com.tfkfan.service.dto.DroneDTO;
import com.tfkfan.service.mapper.DroneMapper;
import java.util.Optional;
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
    public DroneDTO save(DroneDTO droneDTO) {
        log.debug("Request to save Drone : {}", droneDTO);
        Drone drone = droneMapper.toEntity(droneDTO);
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
    @Transactional(readOnly = true)
    public Optional<DroneDTO> findOne(String id) {
        log.debug("Request to get Drone : {}", id);
        return droneRepository.findById(id).map(droneMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Drone : {}", id);
        droneRepository.deleteById(id);
    }
}
