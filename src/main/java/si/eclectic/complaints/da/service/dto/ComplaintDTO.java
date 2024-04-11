package si.eclectic.complaints.da.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link si.eclectic.complaints.da.domain.Complaint} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ComplaintDTO implements Serializable {

    private Long id;

    private String complaintText;

    private ZonedDateTime dateAndTime;

    private Boolean writtenReplyBySMS;

    private ApplicantDTO applicant;

    private CbComplaintFieldDTO cbComplaintField;

    private CbComplaintSubFieldDTO cbComplaintSubField;

    private CbComplaintTypeDTO cbComplaintType;

    private CbComplaintChannelDTO cbComplaintChannel;

    private CbApplicantCapacityTypeDTO cbApplicantCapacityType;

    private CbComplaintPhaseDTO cbComplaintPhase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComplaintText() {
        return complaintText;
    }

    public void setComplaintText(String complaintText) {
        this.complaintText = complaintText;
    }

    public ZonedDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(ZonedDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public Boolean getWrittenReplyBySMS() {
        return writtenReplyBySMS;
    }

    public void setWrittenReplyBySMS(Boolean writtenReplyBySMS) {
        this.writtenReplyBySMS = writtenReplyBySMS;
    }

    public ApplicantDTO getApplicant() {
        return applicant;
    }

    public void setApplicant(ApplicantDTO applicant) {
        this.applicant = applicant;
    }

    public CbComplaintFieldDTO getCbComplaintField() {
        return cbComplaintField;
    }

    public void setCbComplaintField(CbComplaintFieldDTO cbComplaintField) {
        this.cbComplaintField = cbComplaintField;
    }

    public CbComplaintSubFieldDTO getCbComplaintSubField() {
        return cbComplaintSubField;
    }

    public void setCbComplaintSubField(CbComplaintSubFieldDTO cbComplaintSubField) {
        this.cbComplaintSubField = cbComplaintSubField;
    }

    public CbComplaintTypeDTO getCbComplaintType() {
        return cbComplaintType;
    }

    public void setCbComplaintType(CbComplaintTypeDTO cbComplaintType) {
        this.cbComplaintType = cbComplaintType;
    }

    public CbComplaintChannelDTO getCbComplaintChannel() {
        return cbComplaintChannel;
    }

    public void setCbComplaintChannel(CbComplaintChannelDTO cbComplaintChannel) {
        this.cbComplaintChannel = cbComplaintChannel;
    }

    public CbApplicantCapacityTypeDTO getCbApplicantCapacityType() {
        return cbApplicantCapacityType;
    }

    public void setCbApplicantCapacityType(CbApplicantCapacityTypeDTO cbApplicantCapacityType) {
        this.cbApplicantCapacityType = cbApplicantCapacityType;
    }

    public CbComplaintPhaseDTO getCbComplaintPhase() {
        return cbComplaintPhase;
    }

    public void setCbComplaintPhase(CbComplaintPhaseDTO cbComplaintPhase) {
        this.cbComplaintPhase = cbComplaintPhase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComplaintDTO)) {
            return false;
        }

        ComplaintDTO complaintDTO = (ComplaintDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, complaintDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComplaintDTO{" +
            "id=" + getId() +
            ", complaintText='" + getComplaintText() + "'" +
            ", dateAndTime='" + getDateAndTime() + "'" +
            ", writtenReplyBySMS='" + getWrittenReplyBySMS() + "'" +
            ", applicant=" + getApplicant() +
            ", cbComplaintField=" + getCbComplaintField() +
            ", cbComplaintSubField=" + getCbComplaintSubField() +
            ", cbComplaintType=" + getCbComplaintType() +
            ", cbComplaintChannel=" + getCbComplaintChannel() +
            ", cbApplicantCapacityType=" + getCbApplicantCapacityType() +
            ", cbComplaintPhase=" + getCbComplaintPhase() +
            "}";
    }
}
