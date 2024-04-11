package si.eclectic.complaints.da.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.complaints.da.domain.CbAttachmentTypeTestSamples.*;
import static si.eclectic.complaints.da.domain.ComplaintAttachmentTestSamples.*;
import static si.eclectic.complaints.da.domain.ComplaintTestSamples.*;

import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class ComplaintAttachmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComplaintAttachment.class);
        ComplaintAttachment complaintAttachment1 = getComplaintAttachmentSample1();
        ComplaintAttachment complaintAttachment2 = new ComplaintAttachment();
        assertThat(complaintAttachment1).isNotEqualTo(complaintAttachment2);

        complaintAttachment2.setId(complaintAttachment1.getId());
        assertThat(complaintAttachment1).isEqualTo(complaintAttachment2);

        complaintAttachment2 = getComplaintAttachmentSample2();
        assertThat(complaintAttachment1).isNotEqualTo(complaintAttachment2);
    }

    @Test
    void complaintTest() throws Exception {
        ComplaintAttachment complaintAttachment = getComplaintAttachmentRandomSampleGenerator();
        Complaint complaintBack = getComplaintRandomSampleGenerator();

        complaintAttachment.setComplaint(complaintBack);
        assertThat(complaintAttachment.getComplaint()).isEqualTo(complaintBack);

        complaintAttachment.complaint(null);
        assertThat(complaintAttachment.getComplaint()).isNull();
    }

    @Test
    void cbAttachmentTypeTest() throws Exception {
        ComplaintAttachment complaintAttachment = getComplaintAttachmentRandomSampleGenerator();
        CbAttachmentType cbAttachmentTypeBack = getCbAttachmentTypeRandomSampleGenerator();

        complaintAttachment.setCbAttachmentType(cbAttachmentTypeBack);
        assertThat(complaintAttachment.getCbAttachmentType()).isEqualTo(cbAttachmentTypeBack);

        complaintAttachment.cbAttachmentType(null);
        assertThat(complaintAttachment.getCbAttachmentType()).isNull();
    }
}
