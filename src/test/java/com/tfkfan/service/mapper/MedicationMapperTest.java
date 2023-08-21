package com.tfkfan.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MedicationMapperTest {

    private MedicationMapper medicationMapper;

    @BeforeEach
    public void setUp() {
        medicationMapper = new MedicationMapperImpl();
    }
}
