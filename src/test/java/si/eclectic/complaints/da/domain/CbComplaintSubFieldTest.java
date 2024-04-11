package si.eclectic.complaints.da.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.complaints.da.domain.CbComplaintSubFieldTestSamples.*;
import static si.eclectic.complaints.da.domain.ComplaintTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class CbComplaintSubFieldTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CbComplaintSubField.class);
        CbComplaintSubField cbComplaintSubField1 = getCbComplaintSubFieldSample1();
        CbComplaintSubField cbComplaintSubField2 = new CbComplaintSubField();
        assertThat(cbComplaintSubField1).isNotEqualTo(cbComplaintSubField2);

        cbComplaintSubField2.setId(cbComplaintSubField1.getId());
        assertThat(cbComplaintSubField1).isEqualTo(cbComplaintSubField2);

        cbComplaintSubField2 = getCbComplaintSubFieldSample2();
        assertThat(cbComplaintSubField1).isNotEqualTo(cbComplaintSubField2);
    }

    @Test
    void complaintTest() throws Exception {
        CbComplaintSubField cbComplaintSubField = getCbComplaintSubFieldRandomSampleGenerator();
        Complaint complaintBack = getComplaintRandomSampleGenerator();

        cbComplaintSubField.addComplaint(complaintBack);
        assertThat(cbComplaintSubField.getComplaints()).containsOnly(complaintBack);
        assertThat(complaintBack.getCbComplaintSubField()).isEqualTo(cbComplaintSubField);

        cbComplaintSubField.removeComplaint(complaintBack);
        assertThat(cbComplaintSubField.getComplaints()).doesNotContain(complaintBack);
        assertThat(complaintBack.getCbComplaintSubField()).isNull();

        cbComplaintSubField.complaints(new HashSet<>(Set.of(complaintBack)));
        assertThat(cbComplaintSubField.getComplaints()).containsOnly(complaintBack);
        assertThat(complaintBack.getCbComplaintSubField()).isEqualTo(cbComplaintSubField);

        cbComplaintSubField.setComplaints(new HashSet<>());
        assertThat(cbComplaintSubField.getComplaints()).doesNotContain(complaintBack);
        assertThat(complaintBack.getCbComplaintSubField()).isNull();
    }
}
