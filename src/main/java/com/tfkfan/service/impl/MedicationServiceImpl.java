package com.tfkfan.service.impl;

import com.tfkfan.domain.Drone;
import com.tfkfan.domain.Medication;
import com.tfkfan.domain.enumeration.State;
import com.tfkfan.repository.DroneRepository;
import com.tfkfan.repository.MedicationRepository;
import com.tfkfan.service.DroneService;
import com.tfkfan.service.MedicationService;
import com.tfkfan.service.dto.DroneDTO;
import com.tfkfan.service.dto.LoadDTO;
import com.tfkfan.service.dto.MedicationDTO;
import com.tfkfan.service.mapper.MedicationMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Medication}.
 */
@Service
@Transactional
public class MedicationServiceImpl implements MedicationService {

    private final Logger log = LoggerFactory.getLogger(MedicationServiceImpl.class);

    private final MedicationRepository medicationRepository;

    private final MedicationMapper medicationMapper;

    private final DroneRepository droneRepository;

    private final DroneService droneService;

    public MedicationServiceImpl(
        MedicationRepository medicationRepository,
        MedicationMapper medicationMapper,
        DroneRepository droneRepository,
        DroneService droneService
    ) {
        this.medicationRepository = medicationRepository;
        this.medicationMapper = medicationMapper;
        this.droneRepository = droneRepository;
        this.droneService = droneService;
    }

    @Override
    public MedicationDTO save(MedicationDTO medicationDTO) {
        log.debug("Request to save Medication : {}", medicationDTO);
        Medication medication = medicationMapper.toEntity(medicationDTO);
        medication = medicationRepository.save(medication);
        return medicationMapper.toDto(medication);
    }

    @Override
    public DroneDTO load(String droneSerialNumber, LoadDTO loadDTO) {
        final Drone drone = droneRepository.findById(droneSerialNumber).orElseThrow(() -> new RuntimeException("Drone not found"));

        if (!State.IDLE.equals(drone.getState())) throw new RuntimeException("Drone is not available for load");

        drone.setState(State.LOADING);
        droneRepository.saveAndFlush(drone);

        drone
            .getMedications()
            .addAll(
                loadDTO
                    .getMedications()
                    .stream()
                    .map(e -> {
                        Medication medication = medicationMapper.toEntity(e);
                        medication.setDrone(drone);
                        return medication;
                    })
                    .toList()
            );

        long currentWeight = drone.getWeight() + drone.getMedications().stream().map(Medication::getWeight).reduce(0L, Long::sum);

        if (currentWeight > drone.getModel().getWeightLimit()) throw new RuntimeException("Drone is overloaded");

        drone.setState(State.LOADED);
        return droneService.updateEntity(drone);
    }

    @Override
    public MedicationDTO update(MedicationDTO medicationDTO) {
        log.debug("Request to update Medication : {}", medicationDTO);
        Medication medication = medicationMapper.toEntity(medicationDTO);
        medication.setIsPersisted();
        medication = medicationRepository.save(medication);
        return medicationMapper.toDto(medication);
    }

    @Override
    public Optional<MedicationDTO> partialUpdate(MedicationDTO medicationDTO) {
        log.debug("Request to partially update Medication : {}", medicationDTO);

        return medicationRepository
            .findById(medicationDTO.getCode())
            .map(existingMedication -> {
                medicationMapper.partialUpdate(existingMedication, medicationDTO);

                return existingMedication;
            })
            .map(medicationRepository::save)
            .map(medicationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Medications");
        return medicationRepository.findAll(pageable).map(medicationMapper::toDto);
    }

    @Override
    public List<MedicationDTO> findByDroneId(String id) {
        Drone drone = droneRepository.findById(id).orElseThrow(() -> new RuntimeException("Drone with the given serial number not found"));

        return drone.getMedications().stream().map(medicationMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MedicationDTO> findOne(String id) {
        log.debug("Request to get Medication : {}", id);
        return medicationRepository.findById(id).map(medicationMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Medication : {}", id);
        medicationRepository.deleteById(id);
    }
}
