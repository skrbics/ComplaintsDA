package si.eclectic.complaints.da.service.mapper;

import static si.eclectic.complaints.da.domain.CbAttachmentTypeAsserts.*;
import static si.eclectic.complaints.da.domain.CbAttachmentTypeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CbAttachmentTypeMapperTest {

    private CbAttachmentTypeMapper cbAttachmentTypeMapper;

    @BeforeEach
    void setUp() {
        cbAttachmentTypeMapper = new CbAttachmentTypeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCbAttachmentTypeSample1();
        var actual = cbAttachmentTypeMapper.toEntity(cbAttachmentTypeMapper.toDto(expected));
        assertCbAttachmentTypeAllPropertiesEquals(expected, actual);
    }
}
