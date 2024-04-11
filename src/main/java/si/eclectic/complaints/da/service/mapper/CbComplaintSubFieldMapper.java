package si.eclectic.complaints.da.service.mapper;

import org.mapstruct.*;
import si.eclectic.complaints.da.domain.CbComplaintSubField;
import si.eclectic.complaints.da.service.dto.CbComplaintSubFieldDTO;

/**
 * Mapper for the entity {@link CbComplaintSubField} and its DTO {@link CbComplaintSubFieldDTO}.
 */
@Mapper(componentModel = "spring")
public interface CbComplaintSubFieldMapper extends EntityMapper<CbComplaintSubFieldDTO, CbComplaintSubField> {}
