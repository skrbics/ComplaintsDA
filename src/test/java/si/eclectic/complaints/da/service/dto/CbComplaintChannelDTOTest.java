package si.eclectic.complaints.da.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class CbComplaintChannelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CbComplaintChannelDTO.class);
        CbComplaintChannelDTO cbComplaintChannelDTO1 = new CbComplaintChannelDTO();
        cbComplaintChannelDTO1.setId(1L);
        CbComplaintChannelDTO cbComplaintChannelDTO2 = new CbComplaintChannelDTO();
        assertThat(cbComplaintChannelDTO1).isNotEqualTo(cbComplaintChannelDTO2);
        cbComplaintChannelDTO2.setId(cbComplaintChannelDTO1.getId());
        assertThat(cbComplaintChannelDTO1).isEqualTo(cbComplaintChannelDTO2);
        cbComplaintChannelDTO2.setId(2L);
        assertThat(cbComplaintChannelDTO1).isNotEqualTo(cbComplaintChannelDTO2);
        cbComplaintChannelDTO1.setId(null);
        assertThat(cbComplaintChannelDTO1).isNotEqualTo(cbComplaintChannelDTO2);
    }
}
