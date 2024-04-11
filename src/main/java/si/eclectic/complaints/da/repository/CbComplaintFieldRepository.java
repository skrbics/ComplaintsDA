package si.eclectic.complaints.da.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import si.eclectic.complaints.da.domain.CbComplaintField;

/**
 * Spring Data JPA repository for the CbComplaintField entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CbComplaintFieldRepository extends JpaRepository<CbComplaintField, Long> {}
