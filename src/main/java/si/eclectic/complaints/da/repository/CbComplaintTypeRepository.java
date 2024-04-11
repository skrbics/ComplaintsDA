package si.eclectic.complaints.da.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import si.eclectic.complaints.da.domain.CbComplaintType;

/**
 * Spring Data JPA repository for the CbComplaintType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CbComplaintTypeRepository extends JpaRepository<CbComplaintType, Long> {}
