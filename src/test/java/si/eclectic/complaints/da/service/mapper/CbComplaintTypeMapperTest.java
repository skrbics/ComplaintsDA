package si.eclectic.complaints.da.service.mapper;

import static si.eclectic.complaints.da.domain.CbComplaintTypeAsserts.*;
import static si.eclectic.complaints.da.domain.CbComplaintTypeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CbComplaintTypeMapperTest {

    private CbComplaintTypeMapper cbComplaintTypeMapper;

    @BeforeEach
    void setUp() {
        cbComplaintTypeMapper = new CbComplaintTypeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCbComplaintTypeSample1();
        var actual = cbComplaintTypeMapper.toEntity(cbComplaintTypeMapper.toDto(expected));
        assertCbComplaintTypeAllPropertiesEquals(expected, actual);
    }
}
