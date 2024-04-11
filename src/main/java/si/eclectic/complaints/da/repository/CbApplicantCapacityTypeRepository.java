package si.eclectic.complaints.da.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import si.eclectic.complaints.da.domain.CbApplicantCapacityType;

/**
 * Spring Data JPA repository for the CbApplicantCapacityType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CbApplicantCapacityTypeRepository extends JpaRepository<CbApplicantCapacityType, Long> {}
