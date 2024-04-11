package si.eclectic.complaints.da.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import si.eclectic.complaints.da.domain.CbComplaintPhase;

/**
 * Spring Data JPA repository for the CbComplaintPhase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CbComplaintPhaseRepository extends JpaRepository<CbComplaintPhase, Long> {}
