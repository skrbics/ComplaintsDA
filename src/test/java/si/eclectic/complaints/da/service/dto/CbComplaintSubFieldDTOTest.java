package si.eclectic.complaints.da.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class CbComplaintSubFieldDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CbComplaintSubFieldDTO.class);
        CbComplaintSubFieldDTO cbComplaintSubFieldDTO1 = new CbComplaintSubFieldDTO();
        cbComplaintSubFieldDTO1.setId(1L);
        CbComplaintSubFieldDTO cbComplaintSubFieldDTO2 = new CbComplaintSubFieldDTO();
        assertThat(cbComplaintSubFieldDTO1).isNotEqualTo(cbComplaintSubFieldDTO2);
        cbComplaintSubFieldDTO2.setId(cbComplaintSubFieldDTO1.getId());
        assertThat(cbComplaintSubFieldDTO1).isEqualTo(cbComplaintSubFieldDTO2);
        cbComplaintSubFieldDTO2.setId(2L);
        assertThat(cbComplaintSubFieldDTO1).isNotEqualTo(cbComplaintSubFieldDTO2);
        cbComplaintSubFieldDTO1.setId(null);
        assertThat(cbComplaintSubFieldDTO1).isNotEqualTo(cbComplaintSubFieldDTO2);
    }
}
