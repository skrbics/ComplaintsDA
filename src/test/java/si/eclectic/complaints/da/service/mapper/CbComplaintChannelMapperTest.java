package si.eclectic.complaints.da.service.mapper;

import static si.eclectic.complaints.da.domain.CbComplaintChannelAsserts.*;
import static si.eclectic.complaints.da.domain.CbComplaintChannelTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CbComplaintChannelMapperTest {

    private CbComplaintChannelMapper cbComplaintChannelMapper;

    @BeforeEach
    void setUp() {
        cbComplaintChannelMapper = new CbComplaintChannelMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCbComplaintChannelSample1();
        var actual = cbComplaintChannelMapper.toEntity(cbComplaintChannelMapper.toDto(expected));
        assertCbComplaintChannelAllPropertiesEquals(expected, actual);
    }
}
