package com.tfkfan.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tfkfan.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DroneDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DroneDTO.class);
        DroneDTO droneDTO1 = new DroneDTO();
        droneDTO1.setSerialNumber("id1");
        DroneDTO droneDTO2 = new DroneDTO();
        assertThat(droneDTO1).isNotEqualTo(droneDTO2);
        droneDTO2.setSerialNumber(droneDTO1.getSerialNumber());
        assertThat(droneDTO1).isEqualTo(droneDTO2);
        droneDTO2.setSerialNumber("id2");
        assertThat(droneDTO1).isNotEqualTo(droneDTO2);
        droneDTO1.setSerialNumber(null);
        assertThat(droneDTO1).isNotEqualTo(droneDTO2);
    }
}
