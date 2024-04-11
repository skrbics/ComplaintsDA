package si.eclectic.complaints.da.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import si.eclectic.complaints.da.domain.CbCity;

/**
 * Spring Data JPA repository for the CbCity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CbCityRepository extends JpaRepository<CbCity, Long> {}
