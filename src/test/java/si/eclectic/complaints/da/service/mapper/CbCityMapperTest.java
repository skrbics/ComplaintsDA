package si.eclectic.complaints.da.service.mapper;

import static si.eclectic.complaints.da.domain.CbCityAsserts.*;
import static si.eclectic.complaints.da.domain.CbCityTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CbCityMapperTest {

    private CbCityMapper cbCityMapper;

    @BeforeEach
    void setUp() {
        cbCityMapper = new CbCityMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCbCitySample1();
        var actual = cbCityMapper.toEntity(cbCityMapper.toDto(expected));
        assertCbCityAllPropertiesEquals(expected, actual);
    }
}
