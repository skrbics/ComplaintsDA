package si.eclectic.complaints.da.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link si.eclectic.complaints.da.domain.CbComplaintField} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CbComplaintFieldDTO implements Serializable {

    private Long id;

    private String complaintFieldName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComplaintFieldName() {
        return complaintFieldName;
    }

    public void setComplaintFieldName(String complaintFieldName) {
        this.complaintFieldName = complaintFieldName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CbComplaintFieldDTO)) {
            return false;
        }

        CbComplaintFieldDTO cbComplaintFieldDTO = (CbComplaintFieldDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cbComplaintFieldDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CbComplaintFieldDTO{" +
            "id=" + getId() +
            ", complaintFieldName='" + getComplaintFieldName() + "'" +
            "}";
    }
}
