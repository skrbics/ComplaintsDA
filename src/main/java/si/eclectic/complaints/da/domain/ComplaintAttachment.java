package si.eclectic.complaints.da.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ComplaintAttachment.
 */
@Entity
@Table(name = "complaint_attachment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ComplaintAttachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ordinal_no")
    private Integer ordinalNo;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Complaint complaint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "complaintAttachments" }, allowSetters = true)
    private CbAttachmentType cbAttachmentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ComplaintAttachment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrdinalNo() {
        return this.ordinalNo;
    }

    public ComplaintAttachment ordinalNo(Integer ordinalNo) {
        this.setOrdinalNo(ordinalNo);
        return this;
    }

    public void setOrdinalNo(Integer ordinalNo) {
        this.ordinalNo = ordinalNo;
    }

    public String getName() {
        return this.name;
    }

    public ComplaintAttachment name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return this.path;
    }

    public ComplaintAttachment path(String path) {
        this.setPath(path);
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Complaint getComplaint() {
        return this.complaint;
    }

    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
    }

    public ComplaintAttachment complaint(Complaint complaint) {
        this.setComplaint(complaint);
        return this;
    }

    public CbAttachmentType getCbAttachmentType() {
        return this.cbAttachmentType;
    }

    public void setCbAttachmentType(CbAttachmentType cbAttachmentType) {
        this.cbAttachmentType = cbAttachmentType;
    }

    public ComplaintAttachment cbAttachmentType(CbAttachmentType cbAttachmentType) {
        this.setCbAttachmentType(cbAttachmentType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComplaintAttachment)) {
            return false;
        }
        return getId() != null && getId().equals(((ComplaintAttachment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComplaintAttachment{" +
            "id=" + getId() +
            ", ordinalNo=" + getOrdinalNo() +
            ", name='" + getName() + "'" +
            ", path='" + getPath() + "'" +
            "}";
    }
}
