package si.eclectic.complaints.da.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class CbLocationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CbLocationDTO.class);
        CbLocationDTO cbLocationDTO1 = new CbLocationDTO();
        cbLocationDTO1.setId(1L);
        CbLocationDTO cbLocationDTO2 = new CbLocationDTO();
        assertThat(cbLocationDTO1).isNotEqualTo(cbLocationDTO2);
        cbLocationDTO2.setId(cbLocationDTO1.getId());
        assertThat(cbLocationDTO1).isEqualTo(cbLocationDTO2);
        cbLocationDTO2.setId(2L);
        assertThat(cbLocationDTO1).isNotEqualTo(cbLocationDTO2);
        cbLocationDTO1.setId(null);
        assertThat(cbLocationDTO1).isNotEqualTo(cbLocationDTO2);
    }
}
