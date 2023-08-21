package com.tfkfan.web.rest;

import com.tfkfan.service.DroneService;
import com.tfkfan.service.dto.DroneDTO;
import com.tfkfan.service.dto.LoadDTO;
import com.tfkfan.service.dto.MedicationLoadDTO;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

/**
 * REST controller for managing {@link com.tfkfan.domain.Drone}.
 */
@RestController
@RequestMapping("/api/drones")
public class DroneResource {

    private final Logger log = LoggerFactory.getLogger(DroneResource.class);

    private static final String ENTITY_NAME = "droneFactoryDrone";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DroneService droneService;

    public DroneResource(DroneService droneService) {
        this.droneService = droneService;
    }

    @PostMapping
    public ResponseEntity<DroneDTO> registerDrone(@Valid @RequestBody DroneDTO droneDTO) throws URISyntaxException {
        log.debug("REST request to save Drone : {}", droneDTO);
        DroneDTO result = droneService.register(droneDTO);
        return ResponseEntity
            .created(new URI("/api/drones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    @PostMapping("/load/{id}")
    public ResponseEntity<DroneDTO> loadDrone(@PathVariable String id, @Valid @RequestBody LoadDTO dto) {
        log.debug("REST request to load Drone {} : {}", id, dto);
        DroneDTO result = droneService.load(id, dto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<List<MedicationLoadDTO>> getDroneBurden(@PathVariable String id) {
        return ResponseEntity.ok().body(droneService.findByDroneId(id));
    }

    @GetMapping("/available")
    public ResponseEntity<List<DroneDTO>> getAvailableDrones() {
        return ResponseEntity.ok().body(droneService.findAllAvailable());
    }

    @GetMapping("/charge/{id}")
    public ResponseEntity<Integer> getDroneBatteryCharge(@PathVariable String id) {
        return ResponseEntity.ok().body(droneService.getDrone(id).getBatteryCharge());
    }
}
