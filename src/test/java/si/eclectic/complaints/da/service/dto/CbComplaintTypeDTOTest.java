package si.eclectic.complaints.da.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class CbComplaintTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CbComplaintTypeDTO.class);
        CbComplaintTypeDTO cbComplaintTypeDTO1 = new CbComplaintTypeDTO();
        cbComplaintTypeDTO1.setId(1L);
        CbComplaintTypeDTO cbComplaintTypeDTO2 = new CbComplaintTypeDTO();
        assertThat(cbComplaintTypeDTO1).isNotEqualTo(cbComplaintTypeDTO2);
        cbComplaintTypeDTO2.setId(cbComplaintTypeDTO1.getId());
        assertThat(cbComplaintTypeDTO1).isEqualTo(cbComplaintTypeDTO2);
        cbComplaintTypeDTO2.setId(2L);
        assertThat(cbComplaintTypeDTO1).isNotEqualTo(cbComplaintTypeDTO2);
        cbComplaintTypeDTO1.setId(null);
        assertThat(cbComplaintTypeDTO1).isNotEqualTo(cbComplaintTypeDTO2);
    }
}
