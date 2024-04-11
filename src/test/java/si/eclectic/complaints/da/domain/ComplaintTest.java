package si.eclectic.complaints.da.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.complaints.da.domain.ApplicantTestSamples.*;
import static si.eclectic.complaints.da.domain.CbApplicantCapacityTypeTestSamples.*;
import static si.eclectic.complaints.da.domain.CbComplaintChannelTestSamples.*;
import static si.eclectic.complaints.da.domain.CbComplaintFieldTestSamples.*;
import static si.eclectic.complaints.da.domain.CbComplaintPhaseTestSamples.*;
import static si.eclectic.complaints.da.domain.CbComplaintSubFieldTestSamples.*;
import static si.eclectic.complaints.da.domain.CbComplaintTypeTestSamples.*;
import static si.eclectic.complaints.da.domain.ComplaintAttachmentTestSamples.*;
import static si.eclectic.complaints.da.domain.ComplaintTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class ComplaintTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Complaint.class);
        Complaint complaint1 = getComplaintSample1();
        Complaint complaint2 = new Complaint();
        assertThat(complaint1).isNotEqualTo(complaint2);

        complaint2.setId(complaint1.getId());
        assertThat(complaint1).isEqualTo(complaint2);

        complaint2 = getComplaintSample2();
        assertThat(complaint1).isNotEqualTo(complaint2);
    }

    @Test
    void applicantTest() throws Exception {
        Complaint complaint = getComplaintRandomSampleGenerator();
        Applicant applicantBack = getApplicantRandomSampleGenerator();

        complaint.setApplicant(applicantBack);
        assertThat(complaint.getApplicant()).isEqualTo(applicantBack);

        complaint.applicant(null);
        assertThat(complaint.getApplicant()).isNull();
    }

    @Test
    void cbComplaintFieldTest() throws Exception {
        Complaint complaint = getComplaintRandomSampleGenerator();
        CbComplaintField cbComplaintFieldBack = getCbComplaintFieldRandomSampleGenerator();

        complaint.setCbComplaintField(cbComplaintFieldBack);
        assertThat(complaint.getCbComplaintField()).isEqualTo(cbComplaintFieldBack);

        complaint.cbComplaintField(null);
        assertThat(complaint.getCbComplaintField()).isNull();
    }

    @Test
    void cbComplaintSubFieldTest() throws Exception {
        Complaint complaint = getComplaintRandomSampleGenerator();
        CbComplaintSubField cbComplaintSubFieldBack = getCbComplaintSubFieldRandomSampleGenerator();

        complaint.setCbComplaintSubField(cbComplaintSubFieldBack);
        assertThat(complaint.getCbComplaintSubField()).isEqualTo(cbComplaintSubFieldBack);

        complaint.cbComplaintSubField(null);
        assertThat(complaint.getCbComplaintSubField()).isNull();
    }

    @Test
    void cbComplaintTypeTest() throws Exception {
        Complaint complaint = getComplaintRandomSampleGenerator();
        CbComplaintType cbComplaintTypeBack = getCbComplaintTypeRandomSampleGenerator();

        complaint.setCbComplaintType(cbComplaintTypeBack);
        assertThat(complaint.getCbComplaintType()).isEqualTo(cbComplaintTypeBack);

        complaint.cbComplaintType(null);
        assertThat(complaint.getCbComplaintType()).isNull();
    }

    @Test
    void cbComplaintChannelTest() throws Exception {
        Complaint complaint = getComplaintRandomSampleGenerator();
        CbComplaintChannel cbComplaintChannelBack = getCbComplaintChannelRandomSampleGenerator();

        complaint.setCbComplaintChannel(cbComplaintChannelBack);
        assertThat(complaint.getCbComplaintChannel()).isEqualTo(cbComplaintChannelBack);

        complaint.cbComplaintChannel(null);
        assertThat(complaint.getCbComplaintChannel()).isNull();
    }

    @Test
    void cbApplicantCapacityTypeTest() throws Exception {
        Complaint complaint = getComplaintRandomSampleGenerator();
        CbApplicantCapacityType cbApplicantCapacityTypeBack = getCbApplicantCapacityTypeRandomSampleGenerator();

        complaint.setCbApplicantCapacityType(cbApplicantCapacityTypeBack);
        assertThat(complaint.getCbApplicantCapacityType()).isEqualTo(cbApplicantCapacityTypeBack);

        complaint.cbApplicantCapacityType(null);
        assertThat(complaint.getCbApplicantCapacityType()).isNull();
    }

    @Test
    void cbComplaintPhaseTest() throws Exception {
        Complaint complaint = getComplaintRandomSampleGenerator();
        CbComplaintPhase cbComplaintPhaseBack = getCbComplaintPhaseRandomSampleGenerator();

        complaint.setCbComplaintPhase(cbComplaintPhaseBack);
        assertThat(complaint.getCbComplaintPhase()).isEqualTo(cbComplaintPhaseBack);

        complaint.cbComplaintPhase(null);
        assertThat(complaint.getCbComplaintPhase()).isNull();
    }

    @Test
    void complaintAttachmentTest() throws Exception {
        Complaint complaint = getComplaintRandomSampleGenerator();
        ComplaintAttachment complaintAttachmentBack = getComplaintAttachmentRandomSampleGenerator();

        complaint.addComplaintAttachment(complaintAttachmentBack);
        assertThat(complaint.getComplaintAttachments()).containsOnly(complaintAttachmentBack);
        assertThat(complaintAttachmentBack.getComplaint()).isEqualTo(complaint);

        complaint.removeComplaintAttachment(complaintAttachmentBack);
        assertThat(complaint.getComplaintAttachments()).doesNotContain(complaintAttachmentBack);
        assertThat(complaintAttachmentBack.getComplaint()).isNull();

        complaint.complaintAttachments(new HashSet<>(Set.of(complaintAttachmentBack)));
        assertThat(complaint.getComplaintAttachments()).containsOnly(complaintAttachmentBack);
        assertThat(complaintAttachmentBack.getComplaint()).isEqualTo(complaint);

        complaint.setComplaintAttachments(new HashSet<>());
        assertThat(complaint.getComplaintAttachments()).doesNotContain(complaintAttachmentBack);
        assertThat(complaintAttachmentBack.getComplaint()).isNull();
    }
}
