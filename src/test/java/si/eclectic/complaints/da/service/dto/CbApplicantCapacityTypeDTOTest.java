package si.eclectic.complaints.da.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class CbApplicantCapacityTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CbApplicantCapacityTypeDTO.class);
        CbApplicantCapacityTypeDTO cbApplicantCapacityTypeDTO1 = new CbApplicantCapacityTypeDTO();
        cbApplicantCapacityTypeDTO1.setId(1L);
        CbApplicantCapacityTypeDTO cbApplicantCapacityTypeDTO2 = new CbApplicantCapacityTypeDTO();
        assertThat(cbApplicantCapacityTypeDTO1).isNotEqualTo(cbApplicantCapacityTypeDTO2);
        cbApplicantCapacityTypeDTO2.setId(cbApplicantCapacityTypeDTO1.getId());
        assertThat(cbApplicantCapacityTypeDTO1).isEqualTo(cbApplicantCapacityTypeDTO2);
        cbApplicantCapacityTypeDTO2.setId(2L);
        assertThat(cbApplicantCapacityTypeDTO1).isNotEqualTo(cbApplicantCapacityTypeDTO2);
        cbApplicantCapacityTypeDTO1.setId(null);
        assertThat(cbApplicantCapacityTypeDTO1).isNotEqualTo(cbApplicantCapacityTypeDTO2);
    }
}
