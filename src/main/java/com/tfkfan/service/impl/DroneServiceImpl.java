package com.tfkfan.service.impl;

import com.tfkfan.config.Constants;
import com.tfkfan.domain.Drone;
import com.tfkfan.domain.MedicationLoad;
import com.tfkfan.domain.enumeration.State;
import com.tfkfan.repository.DroneRepository;
import com.tfkfan.repository.MedicationLoadRepository;
import com.tfkfan.repository.MedicationRepository;
import com.tfkfan.service.DroneService;
import com.tfkfan.service.dto.DroneDTO;
import com.tfkfan.service.dto.LoadDTO;
import com.tfkfan.service.dto.MedicationLoadDTO;
import com.tfkfan.service.mapper.DroneMapper;
import com.tfkfan.service.mapper.MedicationLoadMapper;
import com.tfkfan.service.mapper.MedicationMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final MedicationRepository medicationRepository;

    private final MedicationMapper medicationMapper;

    private final MedicationLoadMapper medicationLoadMapper;

    private final MedicationLoadRepository medicationLoadRepository;

    public DroneServiceImpl(
        MedicationRepository medicationRepository,
        MedicationMapper medicationMapper,
        MedicationLoadMapper medicationLoadMapper,
        DroneRepository droneRepository,
        MedicationLoadRepository medicationLoadRepository,
        DroneMapper droneMapper
    ) {
        this.medicationRepository = medicationRepository;
        this.medicationMapper = medicationMapper;
        this.medicationLoadMapper = medicationLoadMapper;
        this.droneRepository = droneRepository;
        this.medicationLoadRepository = medicationLoadRepository;
        this.droneMapper = droneMapper;
    }

    @Override
    public DroneDTO register(DroneDTO droneDTO) {
        log.debug("Request to save Drone : {}", droneDTO);
        Drone drone = droneMapper.toEntity(droneDTO);
        drone.setState(State.IDLE);
        drone = droneRepository.save(drone);
        return droneMapper.toDto(drone);
    }

    @Override
    public DroneDTO load(String droneSerialNumber, LoadDTO loadDTO) {
        final Drone drone = droneRepository.findById(droneSerialNumber).orElseThrow(() -> new RuntimeException("Drone not found"));

        if (!State.IDLE.equals(drone.getState())) throw new RuntimeException("Drone is not available for load");

        if (drone.getBatteryCharge() < Constants.LOW_CHARGE_THRESHOLD) throw new RuntimeException(
            String.format("Battery charge is lower than %s percents", Constants.LOW_CHARGE_THRESHOLD)
        );

        drone.setState(State.LOADING);
        droneRepository.saveAndFlush(drone);

        drone.setMedicationLoads(
            loadDTO
                .getMedicationLoads()
                .stream()
                .map(load ->
                    new MedicationLoad(
                        drone,
                        medicationRepository
                            .findById(load.getMedication().getCode())
                            .orElseGet(() -> medicationMapper.toEntityWithRequiredFields(load.getMedication(), true)),
                        load.getQuantity()
                    )
                )
                .collect(Collectors.toSet())
        );

        long currentWeight =
            drone.getWeight() +
            drone.getMedicationLoads().stream().map(e -> e.getQuantity() * e.getMedication().getWeight()).reduce(0L, Long::sum);

        if (currentWeight > drone.getModel().getWeightLimit()) throw new RuntimeException("Drone is overloaded");

        drone.setState(State.LOADED);
        return droneMapper.toDto(droneRepository.save(drone));
    }

    @Override
    public DroneDTO getDrone(String serialNumber) {
        log.debug("Request to get Drone : {}", serialNumber);
        return droneRepository
            .findById(serialNumber)
            .map(droneMapper::toDto)
            .orElseThrow(() -> new RuntimeException("Drone with the given serial number not found"));
    }

    @Override
    public List<MedicationLoadDTO> findByDroneId(String serialNumber) {
        Drone drone = droneRepository
            .findById(serialNumber)
            .orElseThrow(() -> new RuntimeException("Drone with the given serial number not found"));
        return drone.getMedicationLoads().stream().map(medicationLoadMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DroneDTO> findAllAvailable() {
        log.debug("Request to get all available Drones");
        return droneRepository.findAllByState(State.IDLE).stream().map(droneMapper::toDto).collect(Collectors.toList());
    }
}
