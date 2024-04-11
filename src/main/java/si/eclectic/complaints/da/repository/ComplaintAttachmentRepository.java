package si.eclectic.complaints.da.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import si.eclectic.complaints.da.domain.ComplaintAttachment;

/**
 * Spring Data JPA repository for the ComplaintAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComplaintAttachmentRepository extends JpaRepository<ComplaintAttachment, Long> {}
