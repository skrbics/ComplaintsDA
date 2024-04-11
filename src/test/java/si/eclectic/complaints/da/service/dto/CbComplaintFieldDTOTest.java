package si.eclectic.complaints.da.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class CbComplaintFieldDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CbComplaintFieldDTO.class);
        CbComplaintFieldDTO cbComplaintFieldDTO1 = new CbComplaintFieldDTO();
        cbComplaintFieldDTO1.setId(1L);
        CbComplaintFieldDTO cbComplaintFieldDTO2 = new CbComplaintFieldDTO();
        assertThat(cbComplaintFieldDTO1).isNotEqualTo(cbComplaintFieldDTO2);
        cbComplaintFieldDTO2.setId(cbComplaintFieldDTO1.getId());
        assertThat(cbComplaintFieldDTO1).isEqualTo(cbComplaintFieldDTO2);
        cbComplaintFieldDTO2.setId(2L);
        assertThat(cbComplaintFieldDTO1).isNotEqualTo(cbComplaintFieldDTO2);
        cbComplaintFieldDTO1.setId(null);
        assertThat(cbComplaintFieldDTO1).isNotEqualTo(cbComplaintFieldDTO2);
    }
}
