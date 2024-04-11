package si.eclectic.complaints.da.service.mapper;

import static si.eclectic.complaints.da.domain.ApplicantAsserts.*;
import static si.eclectic.complaints.da.domain.ApplicantTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicantMapperTest {

    private ApplicantMapper applicantMapper;

    @BeforeEach
    void setUp() {
        applicantMapper = new ApplicantMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getApplicantSample1();
        var actual = applicantMapper.toEntity(applicantMapper.toDto(expected));
        assertApplicantAllPropertiesEquals(expected, actual);
    }
}
