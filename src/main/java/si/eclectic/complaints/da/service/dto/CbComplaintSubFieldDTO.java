package si.eclectic.complaints.da.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link si.eclectic.complaints.da.domain.CbComplaintSubField} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CbComplaintSubFieldDTO implements Serializable {

    private Long id;

    private String complaintSubFieldName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComplaintSubFieldName() {
        return complaintSubFieldName;
    }

    public void setComplaintSubFieldName(String complaintSubFieldName) {
        this.complaintSubFieldName = complaintSubFieldName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CbComplaintSubFieldDTO)) {
            return false;
        }

        CbComplaintSubFieldDTO cbComplaintSubFieldDTO = (CbComplaintSubFieldDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cbComplaintSubFieldDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CbComplaintSubFieldDTO{" +
            "id=" + getId() +
            ", complaintSubFieldName='" + getComplaintSubFieldName() + "'" +
            "}";
    }
}
