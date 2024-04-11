package si.eclectic.complaints.da.service.mapper;

import static si.eclectic.complaints.da.domain.CbApplicantCapacityTypeAsserts.*;
import static si.eclectic.complaints.da.domain.CbApplicantCapacityTypeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CbApplicantCapacityTypeMapperTest {

    private CbApplicantCapacityTypeMapper cbApplicantCapacityTypeMapper;

    @BeforeEach
    void setUp() {
        cbApplicantCapacityTypeMapper = new CbApplicantCapacityTypeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCbApplicantCapacityTypeSample1();
        var actual = cbApplicantCapacityTypeMapper.toEntity(cbApplicantCapacityTypeMapper.toDto(expected));
        assertCbApplicantCapacityTypeAllPropertiesEquals(expected, actual);
    }
}
