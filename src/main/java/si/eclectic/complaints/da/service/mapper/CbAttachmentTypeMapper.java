package si.eclectic.complaints.da.service.mapper;

import org.mapstruct.*;
import si.eclectic.complaints.da.domain.CbAttachmentType;
import si.eclectic.complaints.da.service.dto.CbAttachmentTypeDTO;

/**
 * Mapper for the entity {@link CbAttachmentType} and its DTO {@link CbAttachmentTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CbAttachmentTypeMapper extends EntityMapper<CbAttachmentTypeDTO, CbAttachmentType> {}
