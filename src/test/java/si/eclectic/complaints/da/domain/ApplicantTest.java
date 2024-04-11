package si.eclectic.complaints.da.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.complaints.da.domain.AddressTestSamples.*;
import static si.eclectic.complaints.da.domain.ApplicantTestSamples.*;
import static si.eclectic.complaints.da.domain.ComplaintTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class ApplicantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Applicant.class);
        Applicant applicant1 = getApplicantSample1();
        Applicant applicant2 = new Applicant();
        assertThat(applicant1).isNotEqualTo(applicant2);

        applicant2.setId(applicant1.getId());
        assertThat(applicant1).isEqualTo(applicant2);

        applicant2 = getApplicantSample2();
        assertThat(applicant1).isNotEqualTo(applicant2);
    }

    @Test
    void addressTest() throws Exception {
        Applicant applicant = getApplicantRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        applicant.setAddress(addressBack);
        assertThat(applicant.getAddress()).isEqualTo(addressBack);

        applicant.address(null);
        assertThat(applicant.getAddress()).isNull();
    }

    @Test
    void complaintTest() throws Exception {
        Applicant applicant = getApplicantRandomSampleGenerator();
        Complaint complaintBack = getComplaintRandomSampleGenerator();

        applicant.addComplaint(complaintBack);
        assertThat(applicant.getComplaints()).containsOnly(complaintBack);
        assertThat(complaintBack.getApplicant()).isEqualTo(applicant);

        applicant.removeComplaint(complaintBack);
        assertThat(applicant.getComplaints()).doesNotContain(complaintBack);
        assertThat(complaintBack.getApplicant()).isNull();

        applicant.complaints(new HashSet<>(Set.of(complaintBack)));
        assertThat(applicant.getComplaints()).containsOnly(complaintBack);
        assertThat(complaintBack.getApplicant()).isEqualTo(applicant);

        applicant.setComplaints(new HashSet<>());
        assertThat(applicant.getComplaints()).doesNotContain(complaintBack);
        assertThat(complaintBack.getApplicant()).isNull();
    }
}
