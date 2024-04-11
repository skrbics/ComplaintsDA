package si.eclectic.complaints.da.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CbComplaintField.
 */
@Entity
@Table(name = "cb_complaint_field")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CbComplaintField implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "complaint_field_name")
    private String complaintFieldName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cbComplaintField")
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

    public CbComplaintField id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComplaintFieldName() {
        return this.complaintFieldName;
    }

    public CbComplaintField complaintFieldName(String complaintFieldName) {
        this.setComplaintFieldName(complaintFieldName);
        return this;
    }

    public void setComplaintFieldName(String complaintFieldName) {
        this.complaintFieldName = complaintFieldName;
    }

    public Set<Complaint> getComplaints() {
        return this.complaints;
    }

    public void setComplaints(Set<Complaint> complaints) {
        if (this.complaints != null) {
            this.complaints.forEach(i -> i.setCbComplaintField(null));
        }
        if (complaints != null) {
            complaints.forEach(i -> i.setCbComplaintField(this));
        }
        this.complaints = complaints;
    }

    public CbComplaintField complaints(Set<Complaint> complaints) {
        this.setComplaints(complaints);
        return this;
    }

    public CbComplaintField addComplaint(Complaint complaint) {
        this.complaints.add(complaint);
        complaint.setCbComplaintField(this);
        return this;
    }

    public CbComplaintField removeComplaint(Complaint complaint) {
        this.complaints.remove(complaint);
        complaint.setCbComplaintField(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CbComplaintField)) {
            return false;
        }
        return getId() != null && getId().equals(((CbComplaintField) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CbComplaintField{" +
            "id=" + getId() +
            ", complaintFieldName='" + getComplaintFieldName() + "'" +
            "}";
    }
}
