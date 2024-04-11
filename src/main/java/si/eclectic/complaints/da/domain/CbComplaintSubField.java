package si.eclectic.complaints.da.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CbComplaintSubField.
 */
@Entity
@Table(name = "cb_complaint_sub_field")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CbComplaintSubField implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "complaint_sub_field_name")
    private String complaintSubFieldName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cbComplaintSubField")
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

    public CbComplaintSubField id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComplaintSubFieldName() {
        return this.complaintSubFieldName;
    }

    public CbComplaintSubField complaintSubFieldName(String complaintSubFieldName) {
        this.setComplaintSubFieldName(complaintSubFieldName);
        return this;
    }

    public void setComplaintSubFieldName(String complaintSubFieldName) {
        this.complaintSubFieldName = complaintSubFieldName;
    }

    public Set<Complaint> getComplaints() {
        return this.complaints;
    }

    public void setComplaints(Set<Complaint> complaints) {
        if (this.complaints != null) {
            this.complaints.forEach(i -> i.setCbComplaintSubField(null));
        }
        if (complaints != null) {
            complaints.forEach(i -> i.setCbComplaintSubField(this));
        }
        this.complaints = complaints;
    }

    public CbComplaintSubField complaints(Set<Complaint> complaints) {
        this.setComplaints(complaints);
        return this;
    }

    public CbComplaintSubField addComplaint(Complaint complaint) {
        this.complaints.add(complaint);
        complaint.setCbComplaintSubField(this);
        return this;
    }

    public CbComplaintSubField removeComplaint(Complaint complaint) {
        this.complaints.remove(complaint);
        complaint.setCbComplaintSubField(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CbComplaintSubField)) {
            return false;
        }
        return getId() != null && getId().equals(((CbComplaintSubField) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CbComplaintSubField{" +
            "id=" + getId() +
            ", complaintSubFieldName='" + getComplaintSubFieldName() + "'" +
            "}";
    }
}
