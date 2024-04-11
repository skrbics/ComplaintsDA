package si.eclectic.complaints.da.service.mapper;

import static si.eclectic.complaints.da.domain.CbComplaintPhaseAsserts.*;
import static si.eclectic.complaints.da.domain.CbComplaintPhaseTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CbComplaintPhaseMapperTest {

    private CbComplaintPhaseMapper cbComplaintPhaseMapper;

    @BeforeEach
    void setUp() {
        cbComplaintPhaseMapper = new CbComplaintPhaseMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCbComplaintPhaseSample1();
        var actual = cbComplaintPhaseMapper.toEntity(cbComplaintPhaseMapper.toDto(expected));
        assertCbComplaintPhaseAllPropertiesEquals(expected, actual);
    }
}
