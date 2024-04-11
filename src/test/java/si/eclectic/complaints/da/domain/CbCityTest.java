package si.eclectic.complaints.da.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.complaints.da.domain.AddressTestSamples.*;
import static si.eclectic.complaints.da.domain.CbCityTestSamples.*;
import static si.eclectic.complaints.da.domain.CbLocationTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class CbCityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CbCity.class);
        CbCity cbCity1 = getCbCitySample1();
        CbCity cbCity2 = new CbCity();
        assertThat(cbCity1).isNotEqualTo(cbCity2);

        cbCity2.setId(cbCity1.getId());
        assertThat(cbCity1).isEqualTo(cbCity2);

        cbCity2 = getCbCitySample2();
        assertThat(cbCity1).isNotEqualTo(cbCity2);
    }

    @Test
    void addressTest() throws Exception {
        CbCity cbCity = getCbCityRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        cbCity.addAddress(addressBack);
        assertThat(cbCity.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getCbCity()).isEqualTo(cbCity);

        cbCity.removeAddress(addressBack);
        assertThat(cbCity.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getCbCity()).isNull();

        cbCity.addresses(new HashSet<>(Set.of(addressBack)));
        assertThat(cbCity.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getCbCity()).isEqualTo(cbCity);

        cbCity.setAddresses(new HashSet<>());
        assertThat(cbCity.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getCbCity()).isNull();
    }

    @Test
    void cbLocationTest() throws Exception {
        CbCity cbCity = getCbCityRandomSampleGenerator();
        CbLocation cbLocationBack = getCbLocationRandomSampleGenerator();

        cbCity.addCbLocation(cbLocationBack);
        assertThat(cbCity.getCbLocations()).containsOnly(cbLocationBack);
        assertThat(cbLocationBack.getCbCity()).isEqualTo(cbCity);

        cbCity.removeCbLocation(cbLocationBack);
        assertThat(cbCity.getCbLocations()).doesNotContain(cbLocationBack);
        assertThat(cbLocationBack.getCbCity()).isNull();

        cbCity.cbLocations(new HashSet<>(Set.of(cbLocationBack)));
        assertThat(cbCity.getCbLocations()).containsOnly(cbLocationBack);
        assertThat(cbLocationBack.getCbCity()).isEqualTo(cbCity);

        cbCity.setCbLocations(new HashSet<>());
        assertThat(cbCity.getCbLocations()).doesNotContain(cbLocationBack);
        assertThat(cbLocationBack.getCbCity()).isNull();
    }
}
