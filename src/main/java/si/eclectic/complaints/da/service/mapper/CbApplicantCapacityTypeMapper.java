package si.eclectic.complaints.da.service.mapper;

import org.mapstruct.*;
import si.eclectic.complaints.da.domain.CbApplicantCapacityType;
import si.eclectic.complaints.da.service.dto.CbApplicantCapacityTypeDTO;

/**
 * Mapper for the entity {@link CbApplicantCapacityType} and its DTO {@link CbApplicantCapacityTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CbApplicantCapacityTypeMapper extends EntityMapper<CbApplicantCapacityTypeDTO, CbApplicantCapacityType> {}
