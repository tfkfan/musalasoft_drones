package com.tfkfan.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tfkfan.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ModelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModelDTO.class);
        ModelDTO modelDTO1 = new ModelDTO();
        modelDTO1.setId(1L);
        ModelDTO modelDTO2 = new ModelDTO();
        assertThat(modelDTO1).isNotEqualTo(modelDTO2);
        modelDTO2.setId(modelDTO1.getId());
        assertThat(modelDTO1).isEqualTo(modelDTO2);
        modelDTO2.setId(2L);
        assertThat(modelDTO1).isNotEqualTo(modelDTO2);
        modelDTO1.setId(null);
        assertThat(modelDTO1).isNotEqualTo(modelDTO2);
    }
}
