package si.eclectic.complaints.da.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class CbCityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CbCityDTO.class);
        CbCityDTO cbCityDTO1 = new CbCityDTO();
        cbCityDTO1.setId(1L);
        CbCityDTO cbCityDTO2 = new CbCityDTO();
        assertThat(cbCityDTO1).isNotEqualTo(cbCityDTO2);
        cbCityDTO2.setId(cbCityDTO1.getId());
        assertThat(cbCityDTO1).isEqualTo(cbCityDTO2);
        cbCityDTO2.setId(2L);
        assertThat(cbCityDTO1).isNotEqualTo(cbCityDTO2);
        cbCityDTO1.setId(null);
        assertThat(cbCityDTO1).isNotEqualTo(cbCityDTO2);
    }
}
