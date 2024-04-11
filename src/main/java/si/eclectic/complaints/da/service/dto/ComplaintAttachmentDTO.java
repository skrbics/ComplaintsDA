package si.eclectic.complaints.da.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link si.eclectic.complaints.da.domain.ComplaintAttachment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ComplaintAttachmentDTO implements Serializable {

    private Long id;

    private Integer ordinalNo;

    private String name;

    private String path;

    private ComplaintDTO complaint;

    private CbAttachmentTypeDTO cbAttachmentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrdinalNo() {
        return ordinalNo;
    }

    public void setOrdinalNo(Integer ordinalNo) {
        this.ordinalNo = ordinalNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ComplaintDTO getComplaint() {
        return complaint;
    }

    public void setComplaint(ComplaintDTO complaint) {
        this.complaint = complaint;
    }

    public CbAttachmentTypeDTO getCbAttachmentType() {
        return cbAttachmentType;
    }

    public void setCbAttachmentType(CbAttachmentTypeDTO cbAttachmentType) {
        this.cbAttachmentType = cbAttachmentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComplaintAttachmentDTO)) {
            return false;
        }

        ComplaintAttachmentDTO complaintAttachmentDTO = (ComplaintAttachmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, complaintAttachmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComplaintAttachmentDTO{" +
            "id=" + getId() +
            ", ordinalNo=" + getOrdinalNo() +
            ", name='" + getName() + "'" +
            ", path='" + getPath() + "'" +
            ", complaint=" + getComplaint() +
            ", cbAttachmentType=" + getCbAttachmentType() +
            "}";
    }
}
