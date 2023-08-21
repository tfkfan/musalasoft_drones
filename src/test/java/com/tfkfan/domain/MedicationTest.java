package com.tfkfan.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tfkfan.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MedicationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medication.class);
        Medication medication1 = new Medication();
        medication1.setCode("id1");
        Medication medication2 = new Medication();
        medication2.setCode(medication1.getCode());
        assertThat(medication1).isEqualTo(medication2);
        medication2.setCode("id2");
        assertThat(medication1).isNotEqualTo(medication2);
        medication1.setCode(null);
        assertThat(medication1).isNotEqualTo(medication2);
    }
}
