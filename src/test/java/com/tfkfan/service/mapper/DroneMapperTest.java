package com.tfkfan.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DroneMapperTest {

    private DroneMapper droneMapper;

    @BeforeEach
    public void setUp() {
        droneMapper = new DroneMapperImpl();
    }
}
