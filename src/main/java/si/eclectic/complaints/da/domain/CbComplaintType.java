package si.eclectic.complaints.da.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CbComplaintType.
 */
@Entity
@Table(name = "cb_complaint_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CbComplaintType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "complaint_type_name")
    private String complaintTypeName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cbComplaintType")
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

    public CbComplaintType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComplaintTypeName() {
        return this.complaintTypeName;
    }

    public CbComplaintType complaintTypeName(String complaintTypeName) {
        this.setComplaintTypeName(complaintTypeName);
        return this;
    }

    public void setComplaintTypeName(String complaintTypeName) {
        this.complaintTypeName = complaintTypeName;
    }

    public Set<Complaint> getComplaints() {
        return this.complaints;
    }

    public void setComplaints(Set<Complaint> complaints) {
        if (this.complaints != null) {
            this.complaints.forEach(i -> i.setCbComplaintType(null));
        }
        if (complaints != null) {
            complaints.forEach(i -> i.setCbComplaintType(this));
        }
        this.complaints = complaints;
    }

    public CbComplaintType complaints(Set<Complaint> complaints) {
        this.setComplaints(complaints);
        return this;
    }

    public CbComplaintType addComplaint(Complaint complaint) {
        this.complaints.add(complaint);
        complaint.setCbComplaintType(this);
        return this;
    }

    public CbComplaintType removeComplaint(Complaint complaint) {
        this.complaints.remove(complaint);
        complaint.setCbComplaintType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CbComplaintType)) {
            return false;
        }
        return getId() != null && getId().equals(((CbComplaintType) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CbComplaintType{" +
            "id=" + getId() +
            ", complaintTypeName='" + getComplaintTypeName() + "'" +
            "}";
    }
}
