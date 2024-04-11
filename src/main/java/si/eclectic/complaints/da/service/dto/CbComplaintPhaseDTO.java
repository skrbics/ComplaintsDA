package si.eclectic.complaints.da.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link si.eclectic.complaints.da.domain.CbComplaintPhase} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CbComplaintPhaseDTO implements Serializable {

    private Long id;

    private Integer ordinalNo;

    private String complaintPhaseName;

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

    public String getComplaintPhaseName() {
        return complaintPhaseName;
    }

    public void setComplaintPhaseName(String complaintPhaseName) {
        this.complaintPhaseName = complaintPhaseName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CbComplaintPhaseDTO)) {
            return false;
        }

        CbComplaintPhaseDTO cbComplaintPhaseDTO = (CbComplaintPhaseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cbComplaintPhaseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CbComplaintPhaseDTO{" +
            "id=" + getId() +
            ", ordinalNo=" + getOrdinalNo() +
            ", complaintPhaseName='" + getComplaintPhaseName() + "'" +
            "}";
    }
}
