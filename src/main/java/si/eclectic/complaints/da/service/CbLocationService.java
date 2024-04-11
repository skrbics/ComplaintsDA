package si.eclectic.complaints.da.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import si.eclectic.complaints.da.service.dto.CbLocationDTO;

/**
 * Service Interface for managing {@link si.eclectic.complaints.da.domain.CbLocation}.
 */
public interface CbLocationService {
    /**
     * Save a cbLocation.
     *
     * @param cbLocationDTO the entity to save.
     * @return the persisted entity.
     */
    CbLocationDTO save(CbLocationDTO cbLocationDTO);

    /**
     * Updates a cbLocation.
     *
     * @param cbLocationDTO the entity to update.
     * @return the persisted entity.
     */
    CbLocationDTO update(CbLocationDTO cbLocationDTO);

    /**
     * Partially updates a cbLocation.
     *
     * @param cbLocationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CbLocationDTO> partialUpdate(CbLocationDTO cbLocationDTO);

    /**
     * Get all the cbLocations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CbLocationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cbLocation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CbLocationDTO> findOne(Long id);

    /**
     * Delete the "id" cbLocation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
