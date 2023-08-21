package com.tfkfan.web.rest;

import com.tfkfan.repository.DroneRepository;
import com.tfkfan.service.DroneService;
import com.tfkfan.service.dto.DroneDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.tfkfan.domain.Drone}.
 */
@RestController
@RequestMapping("/api")
public class DroneResource {

    private final Logger log = LoggerFactory.getLogger(DroneResource.class);

    private static final String ENTITY_NAME = "droneFactoryDrone";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DroneService droneService;

    private final DroneRepository droneRepository;

    public DroneResource(DroneService droneService, DroneRepository droneRepository) {
        this.droneService = droneService;
        this.droneRepository = droneRepository;
    }

    /**
     * {@code POST  /drones} : Create a new drone.
     *
     * @param droneDTO the droneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new droneDTO, or with status {@code 400 (Bad Request)} if the drone has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/drones")
    public ResponseEntity<DroneDTO> createDrone(@Valid @RequestBody DroneDTO droneDTO) throws URISyntaxException {
        log.debug("REST request to save Drone : {}", droneDTO);
        if (droneDTO.getId() != null) {
            throw new BadRequestAlertException("A new drone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DroneDTO result = droneService.save(droneDTO);
        return ResponseEntity
            .created(new URI("/api/drones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /drones/:id} : Updates an existing drone.
     *
     * @param id the id of the droneDTO to save.
     * @param droneDTO the droneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated droneDTO,
     * or with status {@code 400 (Bad Request)} if the droneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the droneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/drones/{id}")
    public ResponseEntity<DroneDTO> updateDrone(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody DroneDTO droneDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Drone : {}, {}", id, droneDTO);
        if (droneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, droneDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!droneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DroneDTO result = droneService.update(droneDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, droneDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /drones/:id} : Partial updates given fields of an existing drone, field will ignore if it is null
     *
     * @param id the id of the droneDTO to save.
     * @param droneDTO the droneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated droneDTO,
     * or with status {@code 400 (Bad Request)} if the droneDTO is not valid,
     * or with status {@code 404 (Not Found)} if the droneDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the droneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/drones/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DroneDTO> partialUpdateDrone(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody DroneDTO droneDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Drone partially : {}, {}", id, droneDTO);
        if (droneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, droneDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!droneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DroneDTO> result = droneService.partialUpdate(droneDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, droneDTO.getId())
        );
    }

    /**
     * {@code GET  /drones} : get all the drones.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of drones in body.
     */
    @GetMapping("/drones")
    public ResponseEntity<List<DroneDTO>> getAllDrones(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Drones");
        Page<DroneDTO> page = droneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /drones/:id} : get the "id" drone.
     *
     * @param id the id of the droneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the droneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/drones/{id}")
    public ResponseEntity<DroneDTO> getDrone(@PathVariable String id) {
        log.debug("REST request to get Drone : {}", id);
        Optional<DroneDTO> droneDTO = droneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(droneDTO);
    }

    /**
     * {@code DELETE  /drones/:id} : delete the "id" drone.
     *
     * @param id the id of the droneDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/drones/{id}")
    public ResponseEntity<Void> deleteDrone(@PathVariable String id) {
        log.debug("REST request to delete Drone : {}", id);
        droneService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
