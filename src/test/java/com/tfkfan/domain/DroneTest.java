package com.tfkfan.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tfkfan.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DroneTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Drone.class);
        Drone drone1 = new Drone();
        drone1.setId("id1");
        Drone drone2 = new Drone();
        drone2.setId(drone1.getId());
        assertThat(drone1).isEqualTo(drone2);
        drone2.setId("id2");
        assertThat(drone1).isNotEqualTo(drone2);
        drone1.setId(null);
        assertThat(drone1).isNotEqualTo(drone2);
    }
}
