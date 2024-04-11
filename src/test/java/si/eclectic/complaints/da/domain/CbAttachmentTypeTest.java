package si.eclectic.complaints.da.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.complaints.da.domain.CbAttachmentTypeTestSamples.*;
import static si.eclectic.complaints.da.domain.ComplaintAttachmentTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class CbAttachmentTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CbAttachmentType.class);
        CbAttachmentType cbAttachmentType1 = getCbAttachmentTypeSample1();
        CbAttachmentType cbAttachmentType2 = new CbAttachmentType();
        assertThat(cbAttachmentType1).isNotEqualTo(cbAttachmentType2);

        cbAttachmentType2.setId(cbAttachmentType1.getId());
        assertThat(cbAttachmentType1).isEqualTo(cbAttachmentType2);

        cbAttachmentType2 = getCbAttachmentTypeSample2();
        assertThat(cbAttachmentType1).isNotEqualTo(cbAttachmentType2);
    }

    @Test
    void complaintAttachmentTest() throws Exception {
        CbAttachmentType cbAttachmentType = getCbAttachmentTypeRandomSampleGenerator();
        ComplaintAttachment complaintAttachmentBack = getComplaintAttachmentRandomSampleGenerator();

        cbAttachmentType.addComplaintAttachment(complaintAttachmentBack);
        assertThat(cbAttachmentType.getComplaintAttachments()).containsOnly(complaintAttachmentBack);
        assertThat(complaintAttachmentBack.getCbAttachmentType()).isEqualTo(cbAttachmentType);

        cbAttachmentType.removeComplaintAttachment(complaintAttachmentBack);
        assertThat(cbAttachmentType.getComplaintAttachments()).doesNotContain(complaintAttachmentBack);
        assertThat(complaintAttachmentBack.getCbAttachmentType()).isNull();

        cbAttachmentType.complaintAttachments(new HashSet<>(Set.of(complaintAttachmentBack)));
        assertThat(cbAttachmentType.getComplaintAttachments()).containsOnly(complaintAttachmentBack);
        assertThat(complaintAttachmentBack.getCbAttachmentType()).isEqualTo(cbAttachmentType);

        cbAttachmentType.setComplaintAttachments(new HashSet<>());
        assertThat(cbAttachmentType.getComplaintAttachments()).doesNotContain(complaintAttachmentBack);
        assertThat(complaintAttachmentBack.getCbAttachmentType()).isNull();
    }
}
