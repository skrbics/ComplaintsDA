package si.eclectic.complaints.da.service.mapper;

import org.mapstruct.*;
import si.eclectic.complaints.da.domain.CbAttachmentType;
import si.eclectic.complaints.da.domain.Complaint;
import si.eclectic.complaints.da.domain.ComplaintAttachment;
import si.eclectic.complaints.da.service.dto.CbAttachmentTypeDTO;
import si.eclectic.complaints.da.service.dto.ComplaintAttachmentDTO;
import si.eclectic.complaints.da.service.dto.ComplaintDTO;

/**
 * Mapper for the entity {@link ComplaintAttachment} and its DTO {@link ComplaintAttachmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface ComplaintAttachmentMapper extends EntityMapper<ComplaintAttachmentDTO, ComplaintAttachment> {
    @Mapping(target = "complaint", source = "complaint", qualifiedByName = "complaintId")
    @Mapping(target = "cbAttachmentType", source = "cbAttachmentType", qualifiedByName = "cbAttachmentTypeId")
    ComplaintAttachmentDTO toDto(ComplaintAttachment s);

    @Named("complaintId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ComplaintDTO toDtoComplaintId(Complaint complaint);

    @Named("cbAttachmentTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CbAttachmentTypeDTO toDtoCbAttachmentTypeId(CbAttachmentType cbAttachmentType);
}
