package si.eclectic.complaints.da.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link si.eclectic.complaints.da.domain.CbCity} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CbCityDTO implements Serializable {

    private Long id;

    private String name;

    private String zip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CbCityDTO)) {
            return false;
        }

        CbCityDTO cbCityDTO = (CbCityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cbCityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CbCityDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", zip='" + getZip() + "'" +
            "}";
    }
}
