package si.eclectic.complaints.da.service.mapper;

import org.mapstruct.*;
import si.eclectic.complaints.da.domain.CbComplaintPhase;
import si.eclectic.complaints.da.service.dto.CbComplaintPhaseDTO;

/**
 * Mapper for the entity {@link CbComplaintPhase} and its DTO {@link CbComplaintPhaseDTO}.
 */
@Mapper(componentModel = "spring")
public interface CbComplaintPhaseMapper extends EntityMapper<CbComplaintPhaseDTO, CbComplaintPhase> {}
