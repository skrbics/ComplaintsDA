package si.eclectic.complaints.da.service.mapper;

import org.mapstruct.*;
import si.eclectic.complaints.da.domain.CbCity;
import si.eclectic.complaints.da.service.dto.CbCityDTO;

/**
 * Mapper for the entity {@link CbCity} and its DTO {@link CbCityDTO}.
 */
@Mapper(componentModel = "spring")
public interface CbCityMapper extends EntityMapper<CbCityDTO, CbCity> {}
