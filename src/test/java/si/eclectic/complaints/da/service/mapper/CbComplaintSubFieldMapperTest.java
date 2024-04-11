package si.eclectic.complaints.da.service.mapper;

import static si.eclectic.complaints.da.domain.CbComplaintSubFieldAsserts.*;
import static si.eclectic.complaints.da.domain.CbComplaintSubFieldTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CbComplaintSubFieldMapperTest {

    private CbComplaintSubFieldMapper cbComplaintSubFieldMapper;

    @BeforeEach
    void setUp() {
        cbComplaintSubFieldMapper = new CbComplaintSubFieldMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCbComplaintSubFieldSample1();
        var actual = cbComplaintSubFieldMapper.toEntity(cbComplaintSubFieldMapper.toDto(expected));
        assertCbComplaintSubFieldAllPropertiesEquals(expected, actual);
    }
}
