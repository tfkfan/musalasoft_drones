package com.tfkfan.service.impl;

import com.tfkfan.domain.Drone;
import com.tfkfan.repository.DroneRepository;
import com.tfkfan.service.DroneService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;

    public DroneServiceImpl(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(Drone drone) {
        droneRepository.saveAndFlush(drone);
    }
}
