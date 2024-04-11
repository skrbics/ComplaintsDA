package si.eclectic.complaints.da.service.mapper;

import org.mapstruct.*;
import si.eclectic.complaints.da.domain.CbCity;
import si.eclectic.complaints.da.domain.CbLocation;
import si.eclectic.complaints.da.service.dto.CbCityDTO;
import si.eclectic.complaints.da.service.dto.CbLocationDTO;

/**
 * Mapper for the entity {@link CbLocation} and its DTO {@link CbLocationDTO}.
 */
@Mapper(componentModel = "spring")
public interface CbLocationMapper extends EntityMapper<CbLocationDTO, CbLocation> {
    @Mapping(target = "cbCity", source = "cbCity", qualifiedByName = "cbCityId")
    CbLocationDTO toDto(CbLocation s);

    @Named("cbCityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CbCityDTO toDtoCbCityId(CbCity cbCity);
}
