package si.eclectic.complaints.da.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class CbComplaintPhaseDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CbComplaintPhaseDTO.class);
        CbComplaintPhaseDTO cbComplaintPhaseDTO1 = new CbComplaintPhaseDTO();
        cbComplaintPhaseDTO1.setId(1L);
        CbComplaintPhaseDTO cbComplaintPhaseDTO2 = new CbComplaintPhaseDTO();
        assertThat(cbComplaintPhaseDTO1).isNotEqualTo(cbComplaintPhaseDTO2);
        cbComplaintPhaseDTO2.setId(cbComplaintPhaseDTO1.getId());
        assertThat(cbComplaintPhaseDTO1).isEqualTo(cbComplaintPhaseDTO2);
        cbComplaintPhaseDTO2.setId(2L);
        assertThat(cbComplaintPhaseDTO1).isNotEqualTo(cbComplaintPhaseDTO2);
        cbComplaintPhaseDTO1.setId(null);
        assertThat(cbComplaintPhaseDTO1).isNotEqualTo(cbComplaintPhaseDTO2);
    }
}
