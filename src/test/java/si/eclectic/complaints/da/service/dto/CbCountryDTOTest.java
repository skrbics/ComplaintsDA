package si.eclectic.complaints.da.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class CbCountryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CbCountryDTO.class);
        CbCountryDTO cbCountryDTO1 = new CbCountryDTO();
        cbCountryDTO1.setId(1L);
        CbCountryDTO cbCountryDTO2 = new CbCountryDTO();
        assertThat(cbCountryDTO1).isNotEqualTo(cbCountryDTO2);
        cbCountryDTO2.setId(cbCountryDTO1.getId());
        assertThat(cbCountryDTO1).isEqualTo(cbCountryDTO2);
        cbCountryDTO2.setId(2L);
        assertThat(cbCountryDTO1).isNotEqualTo(cbCountryDTO2);
        cbCountryDTO1.setId(null);
        assertThat(cbCountryDTO1).isNotEqualTo(cbCountryDTO2);
    }
}
