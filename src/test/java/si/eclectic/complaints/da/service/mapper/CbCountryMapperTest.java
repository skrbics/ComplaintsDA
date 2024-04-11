package si.eclectic.complaints.da.service.mapper;

import static si.eclectic.complaints.da.domain.CbCountryAsserts.*;
import static si.eclectic.complaints.da.domain.CbCountryTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CbCountryMapperTest {

    private CbCountryMapper cbCountryMapper;

    @BeforeEach
    void setUp() {
        cbCountryMapper = new CbCountryMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCbCountrySample1();
        var actual = cbCountryMapper.toEntity(cbCountryMapper.toDto(expected));
        assertCbCountryAllPropertiesEquals(expected, actual);
    }
}
