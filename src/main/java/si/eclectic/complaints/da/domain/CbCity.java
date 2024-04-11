package si.eclectic.complaints.da.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CbCity.
 */
@Entity
@Table(name = "cb_city")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CbCity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "zip")
    private String zip;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cbCity")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cbCity", "cbCountry", "applicant" }, allowSetters = true)
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cbCity")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cbCity" }, allowSetters = true)
    private Set<CbLocation> cbLocations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CbCity id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CbCity name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZip() {
        return this.zip;
    }

    public CbCity zip(String zip) {
        this.setZip(zip);
        return this;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        if (this.addresses != null) {
            this.addresses.forEach(i -> i.setCbCity(null));
        }
        if (addresses != null) {
            addresses.forEach(i -> i.setCbCity(this));
        }
        this.addresses = addresses;
    }

    public CbCity addresses(Set<Address> addresses) {
        this.setAddresses(addresses);
        return this;
    }

    public CbCity addAddress(Address address) {
        this.addresses.add(address);
        address.setCbCity(this);
        return this;
    }

    public CbCity removeAddress(Address address) {
        this.addresses.remove(address);
        address.setCbCity(null);
        return this;
    }

    public Set<CbLocation> getCbLocations() {
        return this.cbLocations;
    }

    public void setCbLocations(Set<CbLocation> cbLocations) {
        if (this.cbLocations != null) {
            this.cbLocations.forEach(i -> i.setCbCity(null));
        }
        if (cbLocations != null) {
            cbLocations.forEach(i -> i.setCbCity(this));
        }
        this.cbLocations = cbLocations;
    }

    public CbCity cbLocations(Set<CbLocation> cbLocations) {
        this.setCbLocations(cbLocations);
        return this;
    }

    public CbCity addCbLocation(CbLocation cbLocation) {
        this.cbLocations.add(cbLocation);
        cbLocation.setCbCity(this);
        return this;
    }

    public CbCity removeCbLocation(CbLocation cbLocation) {
        this.cbLocations.remove(cbLocation);
        cbLocation.setCbCity(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CbCity)) {
            return false;
        }
        return getId() != null && getId().equals(((CbCity) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CbCity{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", zip='" + getZip() + "'" +
            "}";
    }
}
