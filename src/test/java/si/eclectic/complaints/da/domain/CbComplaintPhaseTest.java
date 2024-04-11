package si.eclectic.complaints.da.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.complaints.da.domain.CbComplaintPhaseTestSamples.*;
import static si.eclectic.complaints.da.domain.ComplaintTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class CbComplaintPhaseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CbComplaintPhase.class);
        CbComplaintPhase cbComplaintPhase1 = getCbComplaintPhaseSample1();
        CbComplaintPhase cbComplaintPhase2 = new CbComplaintPhase();
        assertThat(cbComplaintPhase1).isNotEqualTo(cbComplaintPhase2);

        cbComplaintPhase2.setId(cbComplaintPhase1.getId());
        assertThat(cbComplaintPhase1).isEqualTo(cbComplaintPhase2);

        cbComplaintPhase2 = getCbComplaintPhaseSample2();
        assertThat(cbComplaintPhase1).isNotEqualTo(cbComplaintPhase2);
    }

    @Test
    void complaintTest() throws Exception {
        CbComplaintPhase cbComplaintPhase = getCbComplaintPhaseRandomSampleGenerator();
        Complaint complaintBack = getComplaintRandomSampleGenerator();

        cbComplaintPhase.addComplaint(complaintBack);
        assertThat(cbComplaintPhase.getComplaints()).containsOnly(complaintBack);
        assertThat(complaintBack.getCbComplaintPhase()).isEqualTo(cbComplaintPhase);

        cbComplaintPhase.removeComplaint(complaintBack);
        assertThat(cbComplaintPhase.getComplaints()).doesNotContain(complaintBack);
        assertThat(complaintBack.getCbComplaintPhase()).isNull();

        cbComplaintPhase.complaints(new HashSet<>(Set.of(complaintBack)));
        assertThat(cbComplaintPhase.getComplaints()).containsOnly(complaintBack);
        assertThat(complaintBack.getCbComplaintPhase()).isEqualTo(cbComplaintPhase);

        cbComplaintPhase.setComplaints(new HashSet<>());
        assertThat(cbComplaintPhase.getComplaints()).doesNotContain(complaintBack);
        assertThat(complaintBack.getCbComplaintPhase()).isNull();
    }
}
