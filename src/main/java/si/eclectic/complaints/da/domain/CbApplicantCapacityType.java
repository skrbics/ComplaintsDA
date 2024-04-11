package si.eclectic.complaints.da.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CbApplicantCapacityType.
 */
@Entity
@Table(name = "cb_applicant_capacity_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CbApplicantCapacityType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "applicant_capacity_type_name")
    private String applicantCapacityTypeName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cbApplicantCapacityType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "applicant",
            "cbComplaintField",
            "cbComplaintSubField",
            "cbComplaintType",
            "cbComplaintChannel",
            "cbApplicantCapacityType",
            "cbComplaintPhase",
            "complaintAttachments",
        },
        allowSetters = true
    )
    private Set<Complaint> complaints = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CbApplicantCapacityType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicantCapacityTypeName() {
        return this.applicantCapacityTypeName;
    }

    public CbApplicantCapacityType applicantCapacityTypeName(String applicantCapacityTypeName) {
        this.setApplicantCapacityTypeName(applicantCapacityTypeName);
        return this;
    }

    public void setApplicantCapacityTypeName(String applicantCapacityTypeName) {
        this.applicantCapacityTypeName = applicantCapacityTypeName;
    }

    public Set<Complaint> getComplaints() {
        return this.complaints;
    }

    public void setComplaints(Set<Complaint> complaints) {
        if (this.complaints != null) {
            this.complaints.forEach(i -> i.setCbApplicantCapacityType(null));
        }
        if (complaints != null) {
            complaints.forEach(i -> i.setCbApplicantCapacityType(this));
        }
        this.complaints = complaints;
    }

    public CbApplicantCapacityType complaints(Set<Complaint> complaints) {
        this.setComplaints(complaints);
        return this;
    }

    public CbApplicantCapacityType addComplaint(Complaint complaint) {
        this.complaints.add(complaint);
        complaint.setCbApplicantCapacityType(this);
        return this;
    }

    public CbApplicantCapacityType removeComplaint(Complaint complaint) {
        this.complaints.remove(complaint);
        complaint.setCbApplicantCapacityType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CbApplicantCapacityType)) {
            return false;
        }
        return getId() != null && getId().equals(((CbApplicantCapacityType) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CbApplicantCapacityType{" +
            "id=" + getId() +
            ", applicantCapacityTypeName='" + getApplicantCapacityTypeName() + "'" +
            "}";
    }
}
