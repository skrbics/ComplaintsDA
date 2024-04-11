package si.eclectic.complaints.da.service.mapper;

import org.mapstruct.*;
import si.eclectic.complaints.da.domain.CbComplaintType;
import si.eclectic.complaints.da.service.dto.CbComplaintTypeDTO;

/**
 * Mapper for the entity {@link CbComplaintType} and its DTO {@link CbComplaintTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CbComplaintTypeMapper extends EntityMapper<CbComplaintTypeDTO, CbComplaintType> {}
