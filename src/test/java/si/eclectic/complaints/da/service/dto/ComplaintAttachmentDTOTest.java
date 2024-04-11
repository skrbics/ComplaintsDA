package si.eclectic.complaints.da.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class ComplaintAttachmentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComplaintAttachmentDTO.class);
        ComplaintAttachmentDTO complaintAttachmentDTO1 = new ComplaintAttachmentDTO();
        complaintAttachmentDTO1.setId(1L);
        ComplaintAttachmentDTO complaintAttachmentDTO2 = new ComplaintAttachmentDTO();
        assertThat(complaintAttachmentDTO1).isNotEqualTo(complaintAttachmentDTO2);
        complaintAttachmentDTO2.setId(complaintAttachmentDTO1.getId());
        assertThat(complaintAttachmentDTO1).isEqualTo(complaintAttachmentDTO2);
        complaintAttachmentDTO2.setId(2L);
        assertThat(complaintAttachmentDTO1).isNotEqualTo(complaintAttachmentDTO2);
        complaintAttachmentDTO1.setId(null);
        assertThat(complaintAttachmentDTO1).isNotEqualTo(complaintAttachmentDTO2);
    }
}
