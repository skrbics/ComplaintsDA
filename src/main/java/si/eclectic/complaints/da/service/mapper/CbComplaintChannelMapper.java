package si.eclectic.complaints.da.service.mapper;

import org.mapstruct.*;
import si.eclectic.complaints.da.domain.CbComplaintChannel;
import si.eclectic.complaints.da.service.dto.CbComplaintChannelDTO;

/**
 * Mapper for the entity {@link CbComplaintChannel} and its DTO {@link CbComplaintChannelDTO}.
 */
@Mapper(componentModel = "spring")
public interface CbComplaintChannelMapper extends EntityMapper<CbComplaintChannelDTO, CbComplaintChannel> {}
