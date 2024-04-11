package si.eclectic.complaints.da.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import si.eclectic.complaints.da.domain.CbComplaintSubField;

/**
 * Spring Data JPA repository for the CbComplaintSubField entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CbComplaintSubFieldRepository extends JpaRepository<CbComplaintSubField, Long> {}
