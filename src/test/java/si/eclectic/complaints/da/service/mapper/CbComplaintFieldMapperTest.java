package si.eclectic.complaints.da.service.mapper;

import static si.eclectic.complaints.da.domain.CbComplaintFieldAsserts.*;
import static si.eclectic.complaints.da.domain.CbComplaintFieldTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CbComplaintFieldMapperTest {

    private CbComplaintFieldMapper cbComplaintFieldMapper;

    @BeforeEach
    void setUp() {
        cbComplaintFieldMapper = new CbComplaintFieldMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCbComplaintFieldSample1();
        var actual = cbComplaintFieldMapper.toEntity(cbComplaintFieldMapper.toDto(expected));
        assertCbComplaintFieldAllPropertiesEquals(expected, actual);
    }
}
