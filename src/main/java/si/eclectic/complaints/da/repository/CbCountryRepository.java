package si.eclectic.complaints.da.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import si.eclectic.complaints.da.domain.CbCountry;

/**
 * Spring Data JPA repository for the CbCountry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CbCountryRepository extends JpaRepository<CbCountry, Long> {}
