package si.eclectic.complaints.da.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CbComplaintChannel.
 */
@Entity
@Table(name = "cb_complaint_channel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CbComplaintChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "complaint_channel_name")
    private String complaintChannelName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cbComplaintChannel")
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

    public CbComplaintChannel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComplaintChannelName() {
        return this.complaintChannelName;
    }

    public CbComplaintChannel complaintChannelName(String complaintChannelName) {
        this.setComplaintChannelName(complaintChannelName);
        return this;
    }

    public void setComplaintChannelName(String complaintChannelName) {
        this.complaintChannelName = complaintChannelName;
    }

    public Set<Complaint> getComplaints() {
        return this.complaints;
    }

    public void setComplaints(Set<Complaint> complaints) {
        if (this.complaints != null) {
            this.complaints.forEach(i -> i.setCbComplaintChannel(null));
        }
        if (complaints != null) {
            complaints.forEach(i -> i.setCbComplaintChannel(this));
        }
        this.complaints = complaints;
    }

    public CbComplaintChannel complaints(Set<Complaint> complaints) {
        this.setComplaints(complaints);
        return this;
    }

    public CbComplaintChannel addComplaint(Complaint complaint) {
        this.complaints.add(complaint);
        complaint.setCbComplaintChannel(this);
        return this;
    }

    public CbComplaintChannel removeComplaint(Complaint complaint) {
        this.complaints.remove(complaint);
        complaint.setCbComplaintChannel(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CbComplaintChannel)) {
            return false;
        }
        return getId() != null && getId().equals(((CbComplaintChannel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CbComplaintChannel{" +
            "id=" + getId() +
            ", complaintChannelName='" + getComplaintChannelName() + "'" +
            "}";
    }
}
