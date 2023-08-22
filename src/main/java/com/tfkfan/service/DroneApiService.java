package com.tfkfan.service;

import com.tfkfan.service.dto.DroneDTO;
import com.tfkfan.service.dto.LoadDTO;
import com.tfkfan.service.dto.MedicationLoadDTO;
import com.tfkfan.service.dto.RegisterDroneDTO;
import java.util.List;

/**
 * Service Interface for managing {@link com.tfkfan.domain.Drone}.
 */
public interface DroneApiService {
    DroneDTO register(RegisterDroneDTO droneDTO);

    List<MedicationLoadDTO> findByDroneId(String serialNumber);

    List<DroneDTO> findAllAvailable();

    DroneDTO load(String droneSerialNumber, LoadDTO loadDTO);

    DroneDTO getDrone(String serialNumber);
}
