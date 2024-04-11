package si.eclectic.complaints.da.service.mapper;

import org.mapstruct.*;
import si.eclectic.complaints.da.domain.Applicant;
import si.eclectic.complaints.da.domain.CbApplicantCapacityType;
import si.eclectic.complaints.da.domain.CbComplaintChannel;
import si.eclectic.complaints.da.domain.CbComplaintField;
import si.eclectic.complaints.da.domain.CbComplaintPhase;
import si.eclectic.complaints.da.domain.CbComplaintSubField;
import si.eclectic.complaints.da.domain.CbComplaintType;
import si.eclectic.complaints.da.domain.Complaint;
import si.eclectic.complaints.da.service.dto.ApplicantDTO;
import si.eclectic.complaints.da.service.dto.CbApplicantCapacityTypeDTO;
import si.eclectic.complaints.da.service.dto.CbComplaintChannelDTO;
import si.eclectic.complaints.da.service.dto.CbComplaintFieldDTO;
import si.eclectic.complaints.da.service.dto.CbComplaintPhaseDTO;
import si.eclectic.complaints.da.service.dto.CbComplaintSubFieldDTO;
import si.eclectic.complaints.da.service.dto.CbComplaintTypeDTO;
import si.eclectic.complaints.da.service.dto.ComplaintDTO;

/**
 * Mapper for the entity {@link Complaint} and its DTO {@link ComplaintDTO}.
 */
@Mapper(componentModel = "spring")
public interface ComplaintMapper extends EntityMapper<ComplaintDTO, Complaint> {
    @Mapping(target = "applicant", source = "applicant", qualifiedByName = "applicantId")
    @Mapping(target = "cbComplaintField", source = "cbComplaintField", qualifiedByName = "cbComplaintFieldId")
    @Mapping(target = "cbComplaintSubField", source = "cbComplaintSubField", qualifiedByName = "cbComplaintSubFieldId")
    @Mapping(target = "cbComplaintType", source = "cbComplaintType", qualifiedByName = "cbComplaintTypeId")
    @Mapping(target = "cbComplaintChannel", source = "cbComplaintChannel", qualifiedByName = "cbComplaintChannelId")
    @Mapping(target = "cbApplicantCapacityType", source = "cbApplicantCapacityType", qualifiedByName = "cbApplicantCapacityTypeId")
    @Mapping(target = "cbComplaintPhase", source = "cbComplaintPhase", qualifiedByName = "cbComplaintPhaseId")
    ComplaintDTO toDto(Complaint s);

    @Named("applicantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApplicantDTO toDtoApplicantId(Applicant applicant);

    @Named("cbComplaintFieldId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CbComplaintFieldDTO toDtoCbComplaintFieldId(CbComplaintField cbComplaintField);

    @Named("cbComplaintSubFieldId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CbComplaintSubFieldDTO toDtoCbComplaintSubFieldId(CbComplaintSubField cbComplaintSubField);

    @Named("cbComplaintTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CbComplaintTypeDTO toDtoCbComplaintTypeId(CbComplaintType cbComplaintType);

    @Named("cbComplaintChannelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CbComplaintChannelDTO toDtoCbComplaintChannelId(CbComplaintChannel cbComplaintChannel);

    @Named("cbApplicantCapacityTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CbApplicantCapacityTypeDTO toDtoCbApplicantCapacityTypeId(CbApplicantCapacityType cbApplicantCapacityType);

    @Named("cbComplaintPhaseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CbComplaintPhaseDTO toDtoCbComplaintPhaseId(CbComplaintPhase cbComplaintPhase);
}
