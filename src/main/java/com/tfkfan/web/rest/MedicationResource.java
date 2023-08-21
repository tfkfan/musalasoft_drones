package com.tfkfan.web.rest;

import com.tfkfan.repository.MedicationRepository;
import com.tfkfan.service.MedicationService;
import com.tfkfan.service.dto.MedicationDTO;
import com.tfkfan.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.tfkfan.domain.Medication}.
 */
@RestController
@RequestMapping("/api")
public class MedicationResource {

    private final Logger log = LoggerFactory.getLogger(MedicationResource.class);

    private static final String ENTITY_NAME = "droneFactoryMedication";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicationService medicationService;

    private final MedicationRepository medicationRepository;

    public MedicationResource(MedicationService medicationService, MedicationRepository medicationRepository) {
        this.medicationService = medicationService;
        this.medicationRepository = medicationRepository;
    }

    /**
     * {@code POST  /medications} : Create a new medication.
     *
     * @param medicationDTO the medicationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicationDTO, or with status {@code 400 (Bad Request)} if the medication has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medications")
    public ResponseEntity<MedicationDTO> createMedication(@Valid @RequestBody MedicationDTO medicationDTO) throws URISyntaxException {
        log.debug("REST request to save Medication : {}", medicationDTO);
        if (medicationDTO.getCode() != null) {
            throw new BadRequestAlertException("A new medication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicationDTO result = medicationService.save(medicationDTO);
        return ResponseEntity
            .created(new URI("/api/medications/" + result.getCode()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getCode()))
            .body(result);
    }

    /**
     * {@code PUT  /medications/:code} : Updates an existing medication.
     *
     * @param code the id of the medicationDTO to save.
     * @param medicationDTO the medicationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicationDTO,
     * or with status {@code 400 (Bad Request)} if the medicationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medications/{code}")
    public ResponseEntity<MedicationDTO> updateMedication(
        @PathVariable(value = "code", required = false) final String code,
        @Valid @RequestBody MedicationDTO medicationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Medication : {}, {}", code, medicationDTO);
        if (medicationDTO.getCode() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(code, medicationDTO.getCode())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medicationRepository.existsById(code)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MedicationDTO result = medicationService.update(medicationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicationDTO.getCode()))
            .body(result);
    }

    /**
     * {@code PATCH  /medications/:code} : Partial updates given fields of an existing medication, field will ignore if it is null
     *
     * @param code the id of the medicationDTO to save.
     * @param medicationDTO the medicationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicationDTO,
     * or with status {@code 400 (Bad Request)} if the medicationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the medicationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the medicationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/medications/{code}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MedicationDTO> partialUpdateMedication(
        @PathVariable(value = "code", required = false) final String code,
        @NotNull @RequestBody MedicationDTO medicationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Medication partially : {}, {}", code, medicationDTO);
        if (medicationDTO.getCode() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(code, medicationDTO.getCode())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medicationRepository.existsById(code)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MedicationDTO> result = medicationService.partialUpdate(medicationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicationDTO.getCode())
        );
    }

    /**
     * {@code GET  /medications} : get all the medications.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medications in body.
     */
    @GetMapping("/medications")
    public ResponseEntity<List<MedicationDTO>> getAllMedications(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Medications");
        Page<MedicationDTO> page = medicationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /medications/:id} : get the "id" medication.
     *
     * @param id the id of the medicationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medications/{id}")
    public ResponseEntity<MedicationDTO> getMedication(@PathVariable String id) {
        log.debug("REST request to get Medication : {}", id);
        Optional<MedicationDTO> medicationDTO = medicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicationDTO);
    }

    /**
     * {@code DELETE  /medications/:id} : delete the "id" medication.
     *
     * @param id the id of the medicationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medications/{id}")
    public ResponseEntity<Void> deleteMedication(@PathVariable String id) {
        log.debug("REST request to delete Medication : {}", id);
        medicationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
