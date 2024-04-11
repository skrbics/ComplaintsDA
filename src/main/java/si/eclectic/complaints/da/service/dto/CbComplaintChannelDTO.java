package si.eclectic.complaints.da.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link si.eclectic.complaints.da.domain.CbComplaintChannel} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CbComplaintChannelDTO implements Serializable {

    private Long id;

    private String complaintChannelName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComplaintChannelName() {
        return complaintChannelName;
    }

    public void setComplaintChannelName(String complaintChannelName) {
        this.complaintChannelName = complaintChannelName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CbComplaintChannelDTO)) {
            return false;
        }

        CbComplaintChannelDTO cbComplaintChannelDTO = (CbComplaintChannelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cbComplaintChannelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CbComplaintChannelDTO{" +
            "id=" + getId() +
            ", complaintChannelName='" + getComplaintChannelName() + "'" +
            "}";
    }
}
