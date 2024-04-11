package si.eclectic.complaints.da.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.complaints.da.domain.CbComplaintFieldTestSamples.*;
import static si.eclectic.complaints.da.domain.ComplaintTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class CbComplaintFieldTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CbComplaintField.class);
        CbComplaintField cbComplaintField1 = getCbComplaintFieldSample1();
        CbComplaintField cbComplaintField2 = new CbComplaintField();
        assertThat(cbComplaintField1).isNotEqualTo(cbComplaintField2);

        cbComplaintField2.setId(cbComplaintField1.getId());
        assertThat(cbComplaintField1).isEqualTo(cbComplaintField2);

        cbComplaintField2 = getCbComplaintFieldSample2();
        assertThat(cbComplaintField1).isNotEqualTo(cbComplaintField2);
    }

    @Test
    void complaintTest() throws Exception {
        CbComplaintField cbComplaintField = getCbComplaintFieldRandomSampleGenerator();
        Complaint complaintBack = getComplaintRandomSampleGenerator();

        cbComplaintField.addComplaint(complaintBack);
        assertThat(cbComplaintField.getComplaints()).containsOnly(complaintBack);
        assertThat(complaintBack.getCbComplaintField()).isEqualTo(cbComplaintField);

        cbComplaintField.removeComplaint(complaintBack);
        assertThat(cbComplaintField.getComplaints()).doesNotContain(complaintBack);
        assertThat(complaintBack.getCbComplaintField()).isNull();

        cbComplaintField.complaints(new HashSet<>(Set.of(complaintBack)));
        assertThat(cbComplaintField.getComplaints()).containsOnly(complaintBack);
        assertThat(complaintBack.getCbComplaintField()).isEqualTo(cbComplaintField);

        cbComplaintField.setComplaints(new HashSet<>());
        assertThat(cbComplaintField.getComplaints()).doesNotContain(complaintBack);
        assertThat(complaintBack.getCbComplaintField()).isNull();
    }
}
