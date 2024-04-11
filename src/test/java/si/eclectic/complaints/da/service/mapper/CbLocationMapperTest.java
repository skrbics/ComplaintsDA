package si.eclectic.complaints.da.service.mapper;

import static si.eclectic.complaints.da.domain.CbLocationAsserts.*;
import static si.eclectic.complaints.da.domain.CbLocationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CbLocationMapperTest {

    private CbLocationMapper cbLocationMapper;

    @BeforeEach
    void setUp() {
        cbLocationMapper = new CbLocationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCbLocationSample1();
        var actual = cbLocationMapper.toEntity(cbLocationMapper.toDto(expected));
        assertCbLocationAllPropertiesEquals(expected, actual);
    }
}
