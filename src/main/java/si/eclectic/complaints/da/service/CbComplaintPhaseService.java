package si.eclectic.complaints.da.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import si.eclectic.complaints.da.service.dto.CbComplaintPhaseDTO;

/**
 * Service Interface for managing {@link si.eclectic.complaints.da.domain.CbComplaintPhase}.
 */
public interface CbComplaintPhaseService {
    /**
     * Save a cbComplaintPhase.
     *
     * @param cbComplaintPhaseDTO the entity to save.
     * @return the persisted entity.
     */
    CbComplaintPhaseDTO save(CbComplaintPhaseDTO cbComplaintPhaseDTO);

    /**
     * Updates a cbComplaintPhase.
     *
     * @param cbComplaintPhaseDTO the entity to update.
     * @return the persisted entity.
     */
    CbComplaintPhaseDTO update(CbComplaintPhaseDTO cbComplaintPhaseDTO);

    /**
     * Partially updates a cbComplaintPhase.
     *
     * @param cbComplaintPhaseDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CbComplaintPhaseDTO> partialUpdate(CbComplaintPhaseDTO cbComplaintPhaseDTO);

    /**
     * Get all the cbComplaintPhases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CbComplaintPhaseDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cbComplaintPhase.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CbComplaintPhaseDTO> findOne(Long id);

    /**
     * Delete the "id" cbComplaintPhase.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
