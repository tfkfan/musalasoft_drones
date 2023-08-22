package com.tfkfan.service.impl;

import com.tfkfan.config.Constants;
import com.tfkfan.domain.Drone;
import com.tfkfan.domain.MedicationLoad;
import com.tfkfan.domain.enumeration.State;
import com.tfkfan.exception.BatteryChargeIsLowException;
import com.tfkfan.exception.DroneIsBusyException;
import com.tfkfan.exception.DroneIsOverloadedException;
import com.tfkfan.exception.DroneNotFoundException;
import com.tfkfan.repository.DroneRepository;
import com.tfkfan.repository.MedicationRepository;
import com.tfkfan.service.DroneApiService;
import com.tfkfan.service.DroneService;
import com.tfkfan.service.dto.DroneDTO;
import com.tfkfan.service.dto.LoadDTO;
import com.tfkfan.service.dto.MedicationLoadDTO;
import com.tfkfan.service.dto.RegisterDroneDTO;
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
public class DroneApiServiceImpl implements DroneApiService {

    private final Logger log = LoggerFactory.getLogger(DroneApiServiceImpl.class);

    private final DroneRepository droneRepository;

    private final DroneMapper droneMapper;
    private final MedicationRepository medicationRepository;

    private final MedicationMapper medicationMapper;

    private final MedicationLoadMapper medicationLoadMapper;

    private final DroneService droneService;

    public DroneApiServiceImpl(
        MedicationRepository medicationRepository,
        MedicationMapper medicationMapper,
        MedicationLoadMapper medicationLoadMapper,
        DroneRepository droneRepository,
        DroneMapper droneMapper,
        DroneService droneService
    ) {
        this.medicationRepository = medicationRepository;
        this.medicationMapper = medicationMapper;
        this.medicationLoadMapper = medicationLoadMapper;
        this.droneRepository = droneRepository;
        this.droneMapper = droneMapper;
        this.droneService = droneService;
    }

    @Override
    public DroneDTO register(RegisterDroneDTO droneDTO) {
        log.debug("Request to save Drone : {}", droneDTO);
        Drone drone = droneMapper.toEntity(droneDTO);
        drone.setState(State.IDLE);
        drone = droneRepository.save(drone);
        return droneMapper.toDto(drone);
    }

    @Override
    public DroneDTO load(String droneSerialNumber, LoadDTO loadDTO) {
        final Drone drone = droneRepository.findById(droneSerialNumber).orElseThrow(DroneNotFoundException::new);

        if (!State.IDLE.equals(drone.getState())) throw new DroneIsBusyException();

        if (drone.getBatteryCharge() < Constants.LOW_CHARGE_THRESHOLD) throw new BatteryChargeIsLowException();

        drone.setState(State.LOADING);
        /* Incorrect if need to rollback that lines due to exception below(if drone overloaded) in main 'load' method.
         Possibly nested transaction is a quick solution but hibernate and standard jpa instruments
         don't support this option and save-points. As It seems manual transaction management is required here
         */
        droneService.update(drone);

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

        if (currentWeight > drone.getModel().getWeightLimit()) throw new DroneIsOverloadedException();

        drone.setState(State.LOADED);
        return droneMapper.toDto(droneRepository.save(drone));
    }

    @Override
    public DroneDTO getDrone(String serialNumber) {
        log.debug("Request to get Drone : {}", serialNumber);
        return droneRepository.findById(serialNumber).map(droneMapper::toDto).orElseThrow(DroneNotFoundException::new);
    }

    @Override
    public List<MedicationLoadDTO> findByDroneId(String serialNumber) {
        Drone drone = droneRepository.findById(serialNumber).orElseThrow(DroneNotFoundException::new);
        return drone.getMedicationLoads().stream().map(medicationLoadMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DroneDTO> findAllAvailable() {
        log.debug("Request to get all available Drones");
        return droneRepository
            .findAllByStateAndBatteryChargeGreaterThanEqual(State.IDLE, Constants.LOW_CHARGE_THRESHOLD)
            .stream()
            .map(droneMapper::toDto)
            .collect(Collectors.toList());
    }
}
