package si.eclectic.complaints.da.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.complaints.da.domain.AddressTestSamples.*;
import static si.eclectic.complaints.da.domain.ApplicantTestSamples.*;
import static si.eclectic.complaints.da.domain.CbCityTestSamples.*;
import static si.eclectic.complaints.da.domain.CbCountryTestSamples.*;

import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class AddressTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Address.class);
        Address address1 = getAddressSample1();
        Address address2 = new Address();
        assertThat(address1).isNotEqualTo(address2);

        address2.setId(address1.getId());
        assertThat(address1).isEqualTo(address2);

        address2 = getAddressSample2();
        assertThat(address1).isNotEqualTo(address2);
    }

    @Test
    void cbCityTest() throws Exception {
        Address address = getAddressRandomSampleGenerator();
        CbCity cbCityBack = getCbCityRandomSampleGenerator();

        address.setCbCity(cbCityBack);
        assertThat(address.getCbCity()).isEqualTo(cbCityBack);

        address.cbCity(null);
        assertThat(address.getCbCity()).isNull();
    }

    @Test
    void cbCountryTest() throws Exception {
        Address address = getAddressRandomSampleGenerator();
        CbCountry cbCountryBack = getCbCountryRandomSampleGenerator();

        address.setCbCountry(cbCountryBack);
        assertThat(address.getCbCountry()).isEqualTo(cbCountryBack);

        address.cbCountry(null);
        assertThat(address.getCbCountry()).isNull();
    }

    @Test
    void applicantTest() throws Exception {
        Address address = getAddressRandomSampleGenerator();
        Applicant applicantBack = getApplicantRandomSampleGenerator();

        address.setApplicant(applicantBack);
        assertThat(address.getApplicant()).isEqualTo(applicantBack);
        assertThat(applicantBack.getAddress()).isEqualTo(address);

        address.applicant(null);
        assertThat(address.getApplicant()).isNull();
        assertThat(applicantBack.getAddress()).isNull();
    }
}
