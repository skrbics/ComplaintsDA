package si.eclectic.complaints.da.service.mapper;

import org.mapstruct.*;
import si.eclectic.complaints.da.domain.CbComplaintField;
import si.eclectic.complaints.da.service.dto.CbComplaintFieldDTO;

/**
 * Mapper for the entity {@link CbComplaintField} and its DTO {@link CbComplaintFieldDTO}.
 */
@Mapper(componentModel = "spring")
public interface CbComplaintFieldMapper extends EntityMapper<CbComplaintFieldDTO, CbComplaintField> {}
