package si.eclectic.complaints.da.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import si.eclectic.complaints.da.domain.CbLocation;

/**
 * Spring Data JPA repository for the CbLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CbLocationRepository extends JpaRepository<CbLocation, Long> {}
