package si.eclectic.complaints.da.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CbComplaintPhase.
 */
@Entity
@Table(name = "cb_complaint_phase")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CbComplaintPhase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ordinal_no")
    private Integer ordinalNo;

    @Column(name = "complaint_phase_name")
    private String complaintPhaseName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cbComplaintPhase")
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

    public CbComplaintPhase id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrdinalNo() {
        return this.ordinalNo;
    }

    public CbComplaintPhase ordinalNo(Integer ordinalNo) {
        this.setOrdinalNo(ordinalNo);
        return this;
    }

    public void setOrdinalNo(Integer ordinalNo) {
        this.ordinalNo = ordinalNo;
    }

    public String getComplaintPhaseName() {
        return this.complaintPhaseName;
    }

    public CbComplaintPhase complaintPhaseName(String complaintPhaseName) {
        this.setComplaintPhaseName(complaintPhaseName);
        return this;
    }

    public void setComplaintPhaseName(String complaintPhaseName) {
        this.complaintPhaseName = complaintPhaseName;
    }

    public Set<Complaint> getComplaints() {
        return this.complaints;
    }

    public void setComplaints(Set<Complaint> complaints) {
        if (this.complaints != null) {
            this.complaints.forEach(i -> i.setCbComplaintPhase(null));
        }
        if (complaints != null) {
            complaints.forEach(i -> i.setCbComplaintPhase(this));
        }
        this.complaints = complaints;
    }

    public CbComplaintPhase complaints(Set<Complaint> complaints) {
        this.setComplaints(complaints);
        return this;
    }

    public CbComplaintPhase addComplaint(Complaint complaint) {
        this.complaints.add(complaint);
        complaint.setCbComplaintPhase(this);
        return this;
    }

    public CbComplaintPhase removeComplaint(Complaint complaint) {
        this.complaints.remove(complaint);
        complaint.setCbComplaintPhase(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CbComplaintPhase)) {
            return false;
        }
        return getId() != null && getId().equals(((CbComplaintPhase) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CbComplaintPhase{" +
            "id=" + getId() +
            ", ordinalNo=" + getOrdinalNo() +
            ", complaintPhaseName='" + getComplaintPhaseName() + "'" +
            "}";
    }
}
