package si.eclectic.complaints.da.service.mapper;

import org.mapstruct.*;
import si.eclectic.complaints.da.domain.Address;
import si.eclectic.complaints.da.domain.Applicant;
import si.eclectic.complaints.da.service.dto.AddressDTO;
import si.eclectic.complaints.da.service.dto.ApplicantDTO;

/**
 * Mapper for the entity {@link Applicant} and its DTO {@link ApplicantDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApplicantMapper extends EntityMapper<ApplicantDTO, Applicant> {
    @Mapping(target = "address", source = "address", qualifiedByName = "addressId")
    ApplicantDTO toDto(Applicant s);

    @Named("addressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressDTO toDtoAddressId(Address address);
}
