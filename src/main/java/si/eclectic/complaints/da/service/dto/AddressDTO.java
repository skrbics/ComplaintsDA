package si.eclectic.complaints.da.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link si.eclectic.complaints.da.domain.Address} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AddressDTO implements Serializable {

    private Long id;

    private String street;

    private String houseNo;

    private CbCityDTO cbCity;

    private CbCountryDTO cbCountry;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public CbCityDTO getCbCity() {
        return cbCity;
    }

    public void setCbCity(CbCityDTO cbCity) {
        this.cbCity = cbCity;
    }

    public CbCountryDTO getCbCountry() {
        return cbCountry;
    }

    public void setCbCountry(CbCountryDTO cbCountry) {
        this.cbCountry = cbCountry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddressDTO)) {
            return false;
        }

        AddressDTO addressDTO = (AddressDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, addressDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressDTO{" +
            "id=" + getId() +
            ", street='" + getStreet() + "'" +
            ", houseNo='" + getHouseNo() + "'" +
            ", cbCity=" + getCbCity() +
            ", cbCountry=" + getCbCountry() +
            "}";
    }
}
