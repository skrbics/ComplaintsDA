package si.eclectic.complaints.da.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class CbAttachmentTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CbAttachmentTypeDTO.class);
        CbAttachmentTypeDTO cbAttachmentTypeDTO1 = new CbAttachmentTypeDTO();
        cbAttachmentTypeDTO1.setId(1L);
        CbAttachmentTypeDTO cbAttachmentTypeDTO2 = new CbAttachmentTypeDTO();
        assertThat(cbAttachmentTypeDTO1).isNotEqualTo(cbAttachmentTypeDTO2);
        cbAttachmentTypeDTO2.setId(cbAttachmentTypeDTO1.getId());
        assertThat(cbAttachmentTypeDTO1).isEqualTo(cbAttachmentTypeDTO2);
        cbAttachmentTypeDTO2.setId(2L);
        assertThat(cbAttachmentTypeDTO1).isNotEqualTo(cbAttachmentTypeDTO2);
        cbAttachmentTypeDTO1.setId(null);
        assertThat(cbAttachmentTypeDTO1).isNotEqualTo(cbAttachmentTypeDTO2);
    }
}
