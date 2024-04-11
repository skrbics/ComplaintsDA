package si.eclectic.complaints.da.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import si.eclectic.complaints.da.domain.CbAttachmentType;

/**
 * Spring Data JPA repository for the CbAttachmentType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CbAttachmentTypeRepository extends JpaRepository<CbAttachmentType, Long> {}
