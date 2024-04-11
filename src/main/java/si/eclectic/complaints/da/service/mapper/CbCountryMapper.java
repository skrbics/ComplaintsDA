package si.eclectic.complaints.da.service.mapper;

import org.mapstruct.*;
import si.eclectic.complaints.da.domain.CbCountry;
import si.eclectic.complaints.da.service.dto.CbCountryDTO;

/**
 * Mapper for the entity {@link CbCountry} and its DTO {@link CbCountryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CbCountryMapper extends EntityMapper<CbCountryDTO, CbCountry> {}
