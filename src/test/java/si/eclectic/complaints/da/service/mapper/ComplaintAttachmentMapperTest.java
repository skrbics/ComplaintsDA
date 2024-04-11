package si.eclectic.complaints.da.service.mapper;

import static si.eclectic.complaints.da.domain.ComplaintAttachmentAsserts.*;
import static si.eclectic.complaints.da.domain.ComplaintAttachmentTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComplaintAttachmentMapperTest {

    private ComplaintAttachmentMapper complaintAttachmentMapper;

    @BeforeEach
    void setUp() {
        complaintAttachmentMapper = new ComplaintAttachmentMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getComplaintAttachmentSample1();
        var actual = complaintAttachmentMapper.toEntity(complaintAttachmentMapper.toDto(expected));
        assertComplaintAttachmentAllPropertiesEquals(expected, actual);
    }
}
