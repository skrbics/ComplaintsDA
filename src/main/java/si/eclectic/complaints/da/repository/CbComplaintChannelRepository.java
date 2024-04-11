package si.eclectic.complaints.da.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import si.eclectic.complaints.da.domain.CbComplaintChannel;

/**
 * Spring Data JPA repository for the CbComplaintChannel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CbComplaintChannelRepository extends JpaRepository<CbComplaintChannel, Long> {}
