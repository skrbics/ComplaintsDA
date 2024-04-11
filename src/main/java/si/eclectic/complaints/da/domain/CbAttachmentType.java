package si.eclectic.complaints.da.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CbAttachmentType.
 */
@Entity
@Table(name = "cb_attachment_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CbAttachmentType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "extension")
    private String extension;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cbAttachmentType")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "complaint", "cbAttachmentType" }, allowSetters = true)
    private Set<ComplaintAttachment> complaintAttachments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CbAttachmentType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CbAttachmentType name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return this.extension;
    }

    public CbAttachmentType extension(String extension) {
        this.setExtension(extension);
        return this;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Set<ComplaintAttachment> getComplaintAttachments() {
        return this.complaintAttachments;
    }

    public void setComplaintAttachments(Set<ComplaintAttachment> complaintAttachments) {
        if (this.complaintAttachments != null) {
            this.complaintAttachments.forEach(i -> i.setCbAttachmentType(null));
        }
        if (complaintAttachments != null) {
            complaintAttachments.forEach(i -> i.setCbAttachmentType(this));
        }
        this.complaintAttachments = complaintAttachments;
    }

    public CbAttachmentType complaintAttachments(Set<ComplaintAttachment> complaintAttachments) {
        this.setComplaintAttachments(complaintAttachments);
        return this;
    }

    public CbAttachmentType addComplaintAttachment(ComplaintAttachment complaintAttachment) {
        this.complaintAttachments.add(complaintAttachment);
        complaintAttachment.setCbAttachmentType(this);
        return this;
    }

    public CbAttachmentType removeComplaintAttachment(ComplaintAttachment complaintAttachment) {
        this.complaintAttachments.remove(complaintAttachment);
        complaintAttachment.setCbAttachmentType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CbAttachmentType)) {
            return false;
        }
        return getId() != null && getId().equals(((CbAttachmentType) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CbAttachmentType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", extension='" + getExtension() + "'" +
            "}";
    }
}
