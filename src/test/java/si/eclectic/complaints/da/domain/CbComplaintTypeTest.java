package si.eclectic.complaints.da.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.complaints.da.domain.CbComplaintTypeTestSamples.*;
import static si.eclectic.complaints.da.domain.ComplaintTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class CbComplaintTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CbComplaintType.class);
        CbComplaintType cbComplaintType1 = getCbComplaintTypeSample1();
        CbComplaintType cbComplaintType2 = new CbComplaintType();
        assertThat(cbComplaintType1).isNotEqualTo(cbComplaintType2);

        cbComplaintType2.setId(cbComplaintType1.getId());
        assertThat(cbComplaintType1).isEqualTo(cbComplaintType2);

        cbComplaintType2 = getCbComplaintTypeSample2();
        assertThat(cbComplaintType1).isNotEqualTo(cbComplaintType2);
    }

    @Test
    void complaintTest() throws Exception {
        CbComplaintType cbComplaintType = getCbComplaintTypeRandomSampleGenerator();
        Complaint complaintBack = getComplaintRandomSampleGenerator();

        cbComplaintType.addComplaint(complaintBack);
        assertThat(cbComplaintType.getComplaints()).containsOnly(complaintBack);
        assertThat(complaintBack.getCbComplaintType()).isEqualTo(cbComplaintType);

        cbComplaintType.removeComplaint(complaintBack);
        assertThat(cbComplaintType.getComplaints()).doesNotContain(complaintBack);
        assertThat(complaintBack.getCbComplaintType()).isNull();

        cbComplaintType.complaints(new HashSet<>(Set.of(complaintBack)));
        assertThat(cbComplaintType.getComplaints()).containsOnly(complaintBack);
        assertThat(complaintBack.getCbComplaintType()).isEqualTo(cbComplaintType);

        cbComplaintType.setComplaints(new HashSet<>());
        assertThat(cbComplaintType.getComplaints()).doesNotContain(complaintBack);
        assertThat(complaintBack.getCbComplaintType()).isNull();
    }
}
