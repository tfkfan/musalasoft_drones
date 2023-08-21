package com.tfkfan.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tfkfan.IntegrationTest;
import com.tfkfan.domain.Drone;
import com.tfkfan.domain.enumeration.State;
import com.tfkfan.repository.DroneRepository;
import com.tfkfan.service.dto.DroneDTO;
import com.tfkfan.service.mapper.DroneMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DroneResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DroneResourceIT {

    private static final Long DEFAULT_WEIGHT = 1L;
    private static final Long UPDATED_WEIGHT = 2L;

    private static final Integer DEFAULT_BATTERY_CHARGE = 1;
    private static final Integer UPDATED_BATTERY_CHARGE = 2;

    private static final State DEFAULT_STATE = State.IDLE;
    private static final State UPDATED_STATE = State.LOADING;

    private static final String ENTITY_API_URL = "/api/drones";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private DroneMapper droneMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDroneMockMvc;

    private Drone drone;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Drone createEntity(EntityManager em) {
        Drone drone = new Drone().weight(DEFAULT_WEIGHT).batteryCharge(DEFAULT_BATTERY_CHARGE).state(DEFAULT_STATE);
        return drone;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Drone createUpdatedEntity(EntityManager em) {
        Drone drone = new Drone().weight(UPDATED_WEIGHT).batteryCharge(UPDATED_BATTERY_CHARGE).state(UPDATED_STATE);
        return drone;
    }

    @BeforeEach
    public void initTest() {
        drone = createEntity(em);
    }

    @Test
    @Transactional
    void createDrone() throws Exception {
        int databaseSizeBeforeCreate = droneRepository.findAll().size();
        // Create the Drone
        DroneDTO droneDTO = droneMapper.toDto(drone);
        restDroneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droneDTO)))
            .andExpect(status().isCreated());

        // Validate the Drone in the database
        List<Drone> droneList = droneRepository.findAll();
        assertThat(droneList).hasSize(databaseSizeBeforeCreate + 1);
        Drone testDrone = droneList.get(droneList.size() - 1);
        assertThat(testDrone.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testDrone.getBatteryCharge()).isEqualTo(DEFAULT_BATTERY_CHARGE);
        assertThat(testDrone.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    void createDroneWithExistingId() throws Exception {
        // Create the Drone with an existing ID
        drone.setId("existing_id");
        DroneDTO droneDTO = droneMapper.toDto(drone);

        int databaseSizeBeforeCreate = droneRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDroneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Drone in the database
        List<Drone> droneList = droneRepository.findAll();
        assertThat(droneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDrones() throws Exception {
        // Initialize the database
        drone.setId(UUID.randomUUID().toString());
        droneRepository.saveAndFlush(drone);

        // Get all the droneList
        restDroneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drone.getId())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].batteryCharge").value(hasItem(DEFAULT_BATTERY_CHARGE)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }

    @Test
    @Transactional
    void getDrone() throws Exception {
        // Initialize the database
        drone.setId(UUID.randomUUID().toString());
        droneRepository.saveAndFlush(drone);

        // Get the drone
        restDroneMockMvc
            .perform(get(ENTITY_API_URL_ID, drone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(drone.getId()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.intValue()))
            .andExpect(jsonPath("$.batteryCharge").value(DEFAULT_BATTERY_CHARGE))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDrone() throws Exception {
        // Get the drone
        restDroneMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDrone() throws Exception {
        // Initialize the database
        drone.setId(UUID.randomUUID().toString());
        droneRepository.saveAndFlush(drone);

        int databaseSizeBeforeUpdate = droneRepository.findAll().size();

        // Update the drone
        Drone updatedDrone = droneRepository.findById(drone.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDrone are not directly saved in db
        em.detach(updatedDrone);
        updatedDrone.weight(UPDATED_WEIGHT).batteryCharge(UPDATED_BATTERY_CHARGE).state(UPDATED_STATE);
        DroneDTO droneDTO = droneMapper.toDto(updatedDrone);

        restDroneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, droneDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(droneDTO))
            )
            .andExpect(status().isOk());

        // Validate the Drone in the database
        List<Drone> droneList = droneRepository.findAll();
        assertThat(droneList).hasSize(databaseSizeBeforeUpdate);
        Drone testDrone = droneList.get(droneList.size() - 1);
        assertThat(testDrone.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testDrone.getBatteryCharge()).isEqualTo(UPDATED_BATTERY_CHARGE);
        assertThat(testDrone.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    void putNonExistingDrone() throws Exception {
        int databaseSizeBeforeUpdate = droneRepository.findAll().size();
        drone.setId(UUID.randomUUID().toString());

        // Create the Drone
        DroneDTO droneDTO = droneMapper.toDto(drone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDroneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, droneDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(droneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Drone in the database
        List<Drone> droneList = droneRepository.findAll();
        assertThat(droneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDrone() throws Exception {
        int databaseSizeBeforeUpdate = droneRepository.findAll().size();
        drone.setId(UUID.randomUUID().toString());

        // Create the Drone
        DroneDTO droneDTO = droneMapper.toDto(drone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDroneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(droneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Drone in the database
        List<Drone> droneList = droneRepository.findAll();
        assertThat(droneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDrone() throws Exception {
        int databaseSizeBeforeUpdate = droneRepository.findAll().size();
        drone.setId(UUID.randomUUID().toString());

        // Create the Drone
        DroneDTO droneDTO = droneMapper.toDto(drone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDroneMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(droneDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Drone in the database
        List<Drone> droneList = droneRepository.findAll();
        assertThat(droneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDroneWithPatch() throws Exception {
        // Initialize the database
        drone.setId(UUID.randomUUID().toString());
        droneRepository.saveAndFlush(drone);

        int databaseSizeBeforeUpdate = droneRepository.findAll().size();

        // Update the drone using partial update
        Drone partialUpdatedDrone = new Drone();
        partialUpdatedDrone.setId(drone.getId());

        partialUpdatedDrone.weight(UPDATED_WEIGHT).state(UPDATED_STATE);

        restDroneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDrone.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDrone))
            )
            .andExpect(status().isOk());

        // Validate the Drone in the database
        List<Drone> droneList = droneRepository.findAll();
        assertThat(droneList).hasSize(databaseSizeBeforeUpdate);
        Drone testDrone = droneList.get(droneList.size() - 1);
        assertThat(testDrone.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testDrone.getBatteryCharge()).isEqualTo(DEFAULT_BATTERY_CHARGE);
        assertThat(testDrone.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    void fullUpdateDroneWithPatch() throws Exception {
        // Initialize the database
        drone.setId(UUID.randomUUID().toString());
        droneRepository.saveAndFlush(drone);

        int databaseSizeBeforeUpdate = droneRepository.findAll().size();

        // Update the drone using partial update
        Drone partialUpdatedDrone = new Drone();
        partialUpdatedDrone.setId(drone.getId());

        partialUpdatedDrone.weight(UPDATED_WEIGHT).batteryCharge(UPDATED_BATTERY_CHARGE).state(UPDATED_STATE);

        restDroneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDrone.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDrone))
            )
            .andExpect(status().isOk());

        // Validate the Drone in the database
        List<Drone> droneList = droneRepository.findAll();
        assertThat(droneList).hasSize(databaseSizeBeforeUpdate);
        Drone testDrone = droneList.get(droneList.size() - 1);
        assertThat(testDrone.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testDrone.getBatteryCharge()).isEqualTo(UPDATED_BATTERY_CHARGE);
        assertThat(testDrone.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    void patchNonExistingDrone() throws Exception {
        int databaseSizeBeforeUpdate = droneRepository.findAll().size();
        drone.setId(UUID.randomUUID().toString());

        // Create the Drone
        DroneDTO droneDTO = droneMapper.toDto(drone);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDroneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, droneDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(droneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Drone in the database
        List<Drone> droneList = droneRepository.findAll();
        assertThat(droneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDrone() throws Exception {
        int databaseSizeBeforeUpdate = droneRepository.findAll().size();
        drone.setId(UUID.randomUUID().toString());

        // Create the Drone
        DroneDTO droneDTO = droneMapper.toDto(drone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDroneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(droneDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Drone in the database
        List<Drone> droneList = droneRepository.findAll();
        assertThat(droneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDrone() throws Exception {
        int databaseSizeBeforeUpdate = droneRepository.findAll().size();
        drone.setId(UUID.randomUUID().toString());

        // Create the Drone
        DroneDTO droneDTO = droneMapper.toDto(drone);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDroneMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(droneDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Drone in the database
        List<Drone> droneList = droneRepository.findAll();
        assertThat(droneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDrone() throws Exception {
        // Initialize the database
        drone.setId(UUID.randomUUID().toString());
        droneRepository.saveAndFlush(drone);

        int databaseSizeBeforeDelete = droneRepository.findAll().size();

        // Delete the drone
        restDroneMockMvc
            .perform(delete(ENTITY_API_URL_ID, drone.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Drone> droneList = droneRepository.findAll();
        assertThat(droneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
