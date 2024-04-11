package si.eclectic.complaints.da.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class OperatorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperatorDTO.class);
        OperatorDTO operatorDTO1 = new OperatorDTO();
        operatorDTO1.setId(1L);
        OperatorDTO operatorDTO2 = new OperatorDTO();
        assertThat(operatorDTO1).isNotEqualTo(operatorDTO2);
        operatorDTO2.setId(operatorDTO1.getId());
        assertThat(operatorDTO1).isEqualTo(operatorDTO2);
        operatorDTO2.setId(2L);
        assertThat(operatorDTO1).isNotEqualTo(operatorDTO2);
        operatorDTO1.setId(null);
        assertThat(operatorDTO1).isNotEqualTo(operatorDTO2);
    }
}
