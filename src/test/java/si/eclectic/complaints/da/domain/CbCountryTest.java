package si.eclectic.complaints.da.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.complaints.da.domain.AddressTestSamples.*;
import static si.eclectic.complaints.da.domain.CbCountryTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class CbCountryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CbCountry.class);
        CbCountry cbCountry1 = getCbCountrySample1();
        CbCountry cbCountry2 = new CbCountry();
        assertThat(cbCountry1).isNotEqualTo(cbCountry2);

        cbCountry2.setId(cbCountry1.getId());
        assertThat(cbCountry1).isEqualTo(cbCountry2);

        cbCountry2 = getCbCountrySample2();
        assertThat(cbCountry1).isNotEqualTo(cbCountry2);
    }

    @Test
    void addressTest() throws Exception {
        CbCountry cbCountry = getCbCountryRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        cbCountry.addAddress(addressBack);
        assertThat(cbCountry.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getCbCountry()).isEqualTo(cbCountry);

        cbCountry.removeAddress(addressBack);
        assertThat(cbCountry.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getCbCountry()).isNull();

        cbCountry.addresses(new HashSet<>(Set.of(addressBack)));
        assertThat(cbCountry.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getCbCountry()).isEqualTo(cbCountry);

        cbCountry.setAddresses(new HashSet<>());
        assertThat(cbCountry.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getCbCountry()).isNull();
    }
}
