package com.tfkfan.service.impl;

import com.tfkfan.domain.Medication;
import com.tfkfan.repository.MedicationRepository;
import com.tfkfan.service.MedicationService;
import com.tfkfan.service.dto.MedicationDTO;
import com.tfkfan.service.mapper.MedicationMapper;
import java.util.Optional;
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

    public MedicationServiceImpl(MedicationRepository medicationRepository, MedicationMapper medicationMapper) {
        this.medicationRepository = medicationRepository;
        this.medicationMapper = medicationMapper;
    }

    @Override
    public MedicationDTO save(MedicationDTO medicationDTO) {
        log.debug("Request to save Medication : {}", medicationDTO);
        Medication medication = medicationMapper.toEntity(medicationDTO);
        medication = medicationRepository.save(medication);
        return medicationMapper.toDto(medication);
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
