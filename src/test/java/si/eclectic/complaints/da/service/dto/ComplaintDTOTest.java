package si.eclectic.complaints.da.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class ComplaintDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComplaintDTO.class);
        ComplaintDTO complaintDTO1 = new ComplaintDTO();
        complaintDTO1.setId(1L);
        ComplaintDTO complaintDTO2 = new ComplaintDTO();
        assertThat(complaintDTO1).isNotEqualTo(complaintDTO2);
        complaintDTO2.setId(complaintDTO1.getId());
        assertThat(complaintDTO1).isEqualTo(complaintDTO2);
        complaintDTO2.setId(2L);
        assertThat(complaintDTO1).isNotEqualTo(complaintDTO2);
        complaintDTO1.setId(null);
        assertThat(complaintDTO1).isNotEqualTo(complaintDTO2);
    }
}
