package com.tfkfan.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tfkfan.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MedicationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicationDTO.class);
        MedicationDTO medicationDTO1 = new MedicationDTO();
        medicationDTO1.setCode("id1");
        MedicationDTO medicationDTO2 = new MedicationDTO();
        assertThat(medicationDTO1).isNotEqualTo(medicationDTO2);
        medicationDTO2.setCode(medicationDTO1.getCode());
        assertThat(medicationDTO1).isEqualTo(medicationDTO2);
        medicationDTO2.setCode("id2");
        assertThat(medicationDTO1).isNotEqualTo(medicationDTO2);
        medicationDTO1.setCode(null);
        assertThat(medicationDTO1).isNotEqualTo(medicationDTO2);
    }
}
