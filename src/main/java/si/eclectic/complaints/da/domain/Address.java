package si.eclectic.complaints.da.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "house_no")
    private String houseNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "addresses", "cbLocations" }, allowSetters = true)
    private CbCity cbCity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "addresses" }, allowSetters = true)
    private CbCountry cbCountry;

    @JsonIgnoreProperties(value = { "address", "complaints" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "address")
    private Applicant applicant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Address id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return this.street;
    }

    public Address street(String street) {
        this.setStreet(street);
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNo() {
        return this.houseNo;
    }

    public Address houseNo(String houseNo) {
        this.setHouseNo(houseNo);
        return this;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public CbCity getCbCity() {
        return this.cbCity;
    }

    public void setCbCity(CbCity cbCity) {
        this.cbCity = cbCity;
    }

    public Address cbCity(CbCity cbCity) {
        this.setCbCity(cbCity);
        return this;
    }

    public CbCountry getCbCountry() {
        return this.cbCountry;
    }

    public void setCbCountry(CbCountry cbCountry) {
        this.cbCountry = cbCountry;
    }

    public Address cbCountry(CbCountry cbCountry) {
        this.setCbCountry(cbCountry);
        return this;
    }

    public Applicant getApplicant() {
        return this.applicant;
    }

    public void setApplicant(Applicant applicant) {
        if (this.applicant != null) {
            this.applicant.setAddress(null);
        }
        if (applicant != null) {
            applicant.setAddress(this);
        }
        this.applicant = applicant;
    }

    public Address applicant(Applicant applicant) {
        this.setApplicant(applicant);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return getId() != null && getId().equals(((Address) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", street='" + getStreet() + "'" +
            ", houseNo='" + getHouseNo() + "'" +
            "}";
    }
}
