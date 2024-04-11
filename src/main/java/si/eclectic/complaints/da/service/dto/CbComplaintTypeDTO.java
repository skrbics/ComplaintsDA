package si.eclectic.complaints.da.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link si.eclectic.complaints.da.domain.CbComplaintType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CbComplaintTypeDTO implements Serializable {

    private Long id;

    private String complaintTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComplaintTypeName() {
        return complaintTypeName;
    }

    public void setComplaintTypeName(String complaintTypeName) {
        this.complaintTypeName = complaintTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CbComplaintTypeDTO)) {
            return false;
        }

        CbComplaintTypeDTO cbComplaintTypeDTO = (CbComplaintTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cbComplaintTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CbComplaintTypeDTO{" +
            "id=" + getId() +
            ", complaintTypeName='" + getComplaintTypeName() + "'" +
            "}";
    }
}
