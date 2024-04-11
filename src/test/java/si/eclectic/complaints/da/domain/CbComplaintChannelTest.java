package si.eclectic.complaints.da.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.complaints.da.domain.CbComplaintChannelTestSamples.*;
import static si.eclectic.complaints.da.domain.ComplaintTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class CbComplaintChannelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CbComplaintChannel.class);
        CbComplaintChannel cbComplaintChannel1 = getCbComplaintChannelSample1();
        CbComplaintChannel cbComplaintChannel2 = new CbComplaintChannel();
        assertThat(cbComplaintChannel1).isNotEqualTo(cbComplaintChannel2);

        cbComplaintChannel2.setId(cbComplaintChannel1.getId());
        assertThat(cbComplaintChannel1).isEqualTo(cbComplaintChannel2);

        cbComplaintChannel2 = getCbComplaintChannelSample2();
        assertThat(cbComplaintChannel1).isNotEqualTo(cbComplaintChannel2);
    }

    @Test
    void complaintTest() throws Exception {
        CbComplaintChannel cbComplaintChannel = getCbComplaintChannelRandomSampleGenerator();
        Complaint complaintBack = getComplaintRandomSampleGenerator();

        cbComplaintChannel.addComplaint(complaintBack);
        assertThat(cbComplaintChannel.getComplaints()).containsOnly(complaintBack);
        assertThat(complaintBack.getCbComplaintChannel()).isEqualTo(cbComplaintChannel);

        cbComplaintChannel.removeComplaint(complaintBack);
        assertThat(cbComplaintChannel.getComplaints()).doesNotContain(complaintBack);
        assertThat(complaintBack.getCbComplaintChannel()).isNull();

        cbComplaintChannel.complaints(new HashSet<>(Set.of(complaintBack)));
        assertThat(cbComplaintChannel.getComplaints()).containsOnly(complaintBack);
        assertThat(complaintBack.getCbComplaintChannel()).isEqualTo(cbComplaintChannel);

        cbComplaintChannel.setComplaints(new HashSet<>());
        assertThat(cbComplaintChannel.getComplaints()).doesNotContain(complaintBack);
        assertThat(complaintBack.getCbComplaintChannel()).isNull();
    }
}
