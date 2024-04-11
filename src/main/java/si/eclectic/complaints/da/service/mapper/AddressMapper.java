package si.eclectic.complaints.da.service.mapper;

import org.mapstruct.*;
import si.eclectic.complaints.da.domain.Address;
import si.eclectic.complaints.da.domain.CbCity;
import si.eclectic.complaints.da.domain.CbCountry;
import si.eclectic.complaints.da.service.dto.AddressDTO;
import si.eclectic.complaints.da.service.dto.CbCityDTO;
import si.eclectic.complaints.da.service.dto.CbCountryDTO;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
    @Mapping(target = "cbCity", source = "cbCity", qualifiedByName = "cbCityId")
    @Mapping(target = "cbCountry", source = "cbCountry", qualifiedByName = "cbCountryId")
    AddressDTO toDto(Address s);

    @Named("cbCityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CbCityDTO toDtoCbCityId(CbCity cbCity);

    @Named("cbCountryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CbCountryDTO toDtoCbCountryId(CbCountry cbCountry);
}
