package com.tfkfan.service.impl;

import com.tfkfan.config.Constants;
import com.tfkfan.domain.DroneBatteryLog;
import com.tfkfan.repository.DroneBatteryLogRepository;
import com.tfkfan.repository.DroneRepository;
import com.tfkfan.service.DroneAuditService;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DroneAuditServiceImpl implements DroneAuditService {

    private final Logger log = LoggerFactory.getLogger(DroneAuditServiceImpl.class);
    private final DroneRepository droneRepository;
    private final DroneBatteryLogRepository droneBatteryLogRepository;

    public DroneAuditServiceImpl(DroneRepository droneRepository, DroneBatteryLogRepository droneBatteryLogRepository) {
        this.droneRepository = droneRepository;
        this.droneBatteryLogRepository = droneBatteryLogRepository;
    }

    //TODO not sure is that useful but slf4j logs is enough, also needs to clear table sometime (depends on business logic)
    @Scheduled(fixedRateString = "${application.battery-log-interval}")
    public void logBatteryChargeProcessor() {
        log.debug("Log battery processing started");
        droneRepository
            .findAll()
            .forEach(drone -> {
                try {
                    droneBatteryLogRepository.save(new DroneBatteryLog(Constants.SYSTEM, Instant.now(), drone.getBatteryCharge(), drone));
                } catch (Exception e) {
                    log.error("Error during drone battery scan");
                }
            });
        log.debug("Log battery processing finished");
    }
}
