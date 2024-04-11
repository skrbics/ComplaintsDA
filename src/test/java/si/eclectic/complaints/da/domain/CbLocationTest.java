package si.eclectic.complaints.da.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.complaints.da.domain.CbCityTestSamples.*;
import static si.eclectic.complaints.da.domain.CbLocationTestSamples.*;

import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class CbLocationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CbLocation.class);
        CbLocation cbLocation1 = getCbLocationSample1();
        CbLocation cbLocation2 = new CbLocation();
        assertThat(cbLocation1).isNotEqualTo(cbLocation2);

        cbLocation2.setId(cbLocation1.getId());
        assertThat(cbLocation1).isEqualTo(cbLocation2);

        cbLocation2 = getCbLocationSample2();
        assertThat(cbLocation1).isNotEqualTo(cbLocation2);
    }

    @Test
    void cbCityTest() throws Exception {
        CbLocation cbLocation = getCbLocationRandomSampleGenerator();
        CbCity cbCityBack = getCbCityRandomSampleGenerator();

        cbLocation.setCbCity(cbCityBack);
        assertThat(cbLocation.getCbCity()).isEqualTo(cbCityBack);

        cbLocation.cbCity(null);
        assertThat(cbLocation.getCbCity()).isNull();
    }
}
