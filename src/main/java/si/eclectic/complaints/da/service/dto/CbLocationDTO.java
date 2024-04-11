package si.eclectic.complaints.da.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link si.eclectic.complaints.da.domain.CbLocation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CbLocationDTO implements Serializable {

    private Long id;

    private String locationName;

    private CbCityDTO cbCity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public CbCityDTO getCbCity() {
        return cbCity;
    }

    public void setCbCity(CbCityDTO cbCity) {
        this.cbCity = cbCity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CbLocationDTO)) {
            return false;
        }

        CbLocationDTO cbLocationDTO = (CbLocationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cbLocationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CbLocationDTO{" +
            "id=" + getId() +
            ", locationName='" + getLocationName() + "'" +
            ", cbCity=" + getCbCity() +
            "}";
    }
}
