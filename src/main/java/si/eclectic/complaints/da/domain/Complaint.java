package si.eclectic.complaints.da.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Complaint.
 */
@Entity
@Table(name = "complaint")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Complaint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "complaint_text")
    private String complaintText;

    @Column(name = "date_and_time")
    private ZonedDateTime dateAndTime;

    @Column(name = "written_reply_by_sms")
    private Boolean writtenReplyBySMS;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "address", "complaints" }, allowSetters = true)
    private Applicant applicant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "complaints" }, allowSetters = true)
    private CbComplaintField cbComplaintField;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "complaints" }, allowSetters = true)
    private CbComplaintSubField cbComplaintSubField;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "complaints" }, allowSetters = true)
    private CbComplaintType cbComplaintType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "complaints" }, allowSetters = true)
    private CbComplaintChannel cbComplaintChannel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "complaints" }, allowSetters = true)
    private CbApplicantCapacityType cbApplicantCapacityType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "complaints" }, allowSetters = true)
    private CbComplaintPhase cbComplaintPhase;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "complaint")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "complaint", "cbAttachmentType" }, allowSetters = true)
    private Set<ComplaintAttachment> complaintAttachments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Complaint id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComplaintText() {
        return this.complaintText;
    }

    public Complaint complaintText(String complaintText) {
        this.setComplaintText(complaintText);
        return this;
    }

    public void setComplaintText(String complaintText) {
        this.complaintText = complaintText;
    }

    public ZonedDateTime getDateAndTime() {
        return this.dateAndTime;
    }

    public Complaint dateAndTime(ZonedDateTime dateAndTime) {
        this.setDateAndTime(dateAndTime);
        return this;
    }

    public void setDateAndTime(ZonedDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public Boolean getWrittenReplyBySMS() {
        return this.writtenReplyBySMS;
    }

    public Complaint writtenReplyBySMS(Boolean writtenReplyBySMS) {
        this.setWrittenReplyBySMS(writtenReplyBySMS);
        return this;
    }

    public void setWrittenReplyBySMS(Boolean writtenReplyBySMS) {
        this.writtenReplyBySMS = writtenReplyBySMS;
    }

    public Applicant getApplicant() {
        return this.applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public Complaint applicant(Applicant applicant) {
        this.setApplicant(applicant);
        return this;
    }

    public CbComplaintField getCbComplaintField() {
        return this.cbComplaintField;
    }

    public void setCbComplaintField(CbComplaintField cbComplaintField) {
        this.cbComplaintField = cbComplaintField;
    }

    public Complaint cbComplaintField(CbComplaintField cbComplaintField) {
        this.setCbComplaintField(cbComplaintField);
        return this;
    }

    public CbComplaintSubField getCbComplaintSubField() {
        return this.cbComplaintSubField;
    }

    public void setCbComplaintSubField(CbComplaintSubField cbComplaintSubField) {
        this.cbComplaintSubField = cbComplaintSubField;
    }

    public Complaint cbComplaintSubField(CbComplaintSubField cbComplaintSubField) {
        this.setCbComplaintSubField(cbComplaintSubField);
        return this;
    }

    public CbComplaintType getCbComplaintType() {
        return this.cbComplaintType;
    }

    public void setCbComplaintType(CbComplaintType cbComplaintType) {
        this.cbComplaintType = cbComplaintType;
    }

    public Complaint cbComplaintType(CbComplaintType cbComplaintType) {
        this.setCbComplaintType(cbComplaintType);
        return this;
    }

    public CbComplaintChannel getCbComplaintChannel() {
        return this.cbComplaintChannel;
    }

    public void setCbComplaintChannel(CbComplaintChannel cbComplaintChannel) {
        this.cbComplaintChannel = cbComplaintChannel;
    }

    public Complaint cbComplaintChannel(CbComplaintChannel cbComplaintChannel) {
        this.setCbComplaintChannel(cbComplaintChannel);
        return this;
    }

    public CbApplicantCapacityType getCbApplicantCapacityType() {
        return this.cbApplicantCapacityType;
    }

    public void setCbApplicantCapacityType(CbApplicantCapacityType cbApplicantCapacityType) {
        this.cbApplicantCapacityType = cbApplicantCapacityType;
    }

    public Complaint cbApplicantCapacityType(CbApplicantCapacityType cbApplicantCapacityType) {
        this.setCbApplicantCapacityType(cbApplicantCapacityType);
        return this;
    }

    public CbComplaintPhase getCbComplaintPhase() {
        return this.cbComplaintPhase;
    }

    public void setCbComplaintPhase(CbComplaintPhase cbComplaintPhase) {
        this.cbComplaintPhase = cbComplaintPhase;
    }

    public Complaint cbComplaintPhase(CbComplaintPhase cbComplaintPhase) {
        this.setCbComplaintPhase(cbComplaintPhase);
        return this;
    }

    public Set<ComplaintAttachment> getComplaintAttachments() {
        return this.complaintAttachments;
    }

    public void setComplaintAttachments(Set<ComplaintAttachment> complaintAttachments) {
        if (this.complaintAttachments != null) {
            this.complaintAttachments.forEach(i -> i.setComplaint(null));
        }
        if (complaintAttachments != null) {
            complaintAttachments.forEach(i -> i.setComplaint(this));
        }
        this.complaintAttachments = complaintAttachments;
    }

    public Complaint complaintAttachments(Set<ComplaintAttachment> complaintAttachments) {
        this.setComplaintAttachments(complaintAttachments);
        return this;
    }

    public Complaint addComplaintAttachment(ComplaintAttachment complaintAttachment) {
        this.complaintAttachments.add(complaintAttachment);
        complaintAttachment.setComplaint(this);
        return this;
    }

    public Complaint removeComplaintAttachment(ComplaintAttachment complaintAttachment) {
        this.complaintAttachments.remove(complaintAttachment);
        complaintAttachment.setComplaint(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Complaint)) {
            return false;
        }
        return getId() != null && getId().equals(((Complaint) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Complaint{" +
            "id=" + getId() +
            ", complaintText='" + getComplaintText() + "'" +
            ", dateAndTime='" + getDateAndTime() + "'" +
            ", writtenReplyBySMS='" + getWrittenReplyBySMS() + "'" +
            "}";
    }
}
