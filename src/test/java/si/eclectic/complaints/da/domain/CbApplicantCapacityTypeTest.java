package si.eclectic.complaints.da.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.complaints.da.domain.CbApplicantCapacityTypeTestSamples.*;
import static si.eclectic.complaints.da.domain.ComplaintTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class CbApplicantCapacityTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CbApplicantCapacityType.class);
        CbApplicantCapacityType cbApplicantCapacityType1 = getCbApplicantCapacityTypeSample1();
        CbApplicantCapacityType cbApplicantCapacityType2 = new CbApplicantCapacityType();
        assertThat(cbApplicantCapacityType1).isNotEqualTo(cbApplicantCapacityType2);

        cbApplicantCapacityType2.setId(cbApplicantCapacityType1.getId());
        assertThat(cbApplicantCapacityType1).isEqualTo(cbApplicantCapacityType2);

        cbApplicantCapacityType2 = getCbApplicantCapacityTypeSample2();
        assertThat(cbApplicantCapacityType1).isNotEqualTo(cbApplicantCapacityType2);
    }

    @Test
    void complaintTest() throws Exception {
        CbApplicantCapacityType cbApplicantCapacityType = getCbApplicantCapacityTypeRandomSampleGenerator();
        Complaint complaintBack = getComplaintRandomSampleGenerator();

        cbApplicantCapacityType.addComplaint(complaintBack);
        assertThat(cbApplicantCapacityType.getComplaints()).containsOnly(complaintBack);
        assertThat(complaintBack.getCbApplicantCapacityType()).isEqualTo(cbApplicantCapacityType);

        cbApplicantCapacityType.removeComplaint(complaintBack);
        assertThat(cbApplicantCapacityType.getComplaints()).doesNotContain(complaintBack);
        assertThat(complaintBack.getCbApplicantCapacityType()).isNull();

        cbApplicantCapacityType.complaints(new HashSet<>(Set.of(complaintBack)));
        assertThat(cbApplicantCapacityType.getComplaints()).containsOnly(complaintBack);
        assertThat(complaintBack.getCbApplicantCapacityType()).isEqualTo(cbApplicantCapacityType);

        cbApplicantCapacityType.setComplaints(new HashSet<>());
        assertThat(cbApplicantCapacityType.getComplaints()).doesNotContain(complaintBack);
        assertThat(complaintBack.getCbApplicantCapacityType()).isNull();
    }
}
