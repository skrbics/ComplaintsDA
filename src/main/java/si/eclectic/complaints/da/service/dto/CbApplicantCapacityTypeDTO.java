package si.eclectic.complaints.da.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link si.eclectic.complaints.da.domain.CbApplicantCapacityType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CbApplicantCapacityTypeDTO implements Serializable {

    private Long id;

    private String applicantCapacityTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicantCapacityTypeName() {
        return applicantCapacityTypeName;
    }

    public void setApplicantCapacityTypeName(String applicantCapacityTypeName) {
        this.applicantCapacityTypeName = applicantCapacityTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CbApplicantCapacityTypeDTO)) {
            return false;
        }

        CbApplicantCapacityTypeDTO cbApplicantCapacityTypeDTO = (CbApplicantCapacityTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cbApplicantCapacityTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CbApplicantCapacityTypeDTO{" +
            "id=" + getId() +
            ", applicantCapacityTypeName='" + getApplicantCapacityTypeName() + "'" +
            "}";
    }
}
