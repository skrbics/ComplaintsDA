package si.eclectic.complaints.da.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import si.eclectic.complaints.da.service.dto.CbComplaintFieldDTO;

/**
 * Service Interface for managing {@link si.eclectic.complaints.da.domain.CbComplaintField}.
 */
public interface CbComplaintFieldService {
    /**
     * Save a cbComplaintField.
     *
     * @param cbComplaintFieldDTO the entity to save.
     * @return the persisted entity.
     */
    CbComplaintFieldDTO save(CbComplaintFieldDTO cbComplaintFieldDTO);

    /**
     * Updates a cbComplaintField.
     *
     * @param cbComplaintFieldDTO the entity to update.
     * @return the persisted entity.
     */
    CbComplaintFieldDTO update(CbComplaintFieldDTO cbComplaintFieldDTO);

    /**
     * Partially updates a cbComplaintField.
     *
     * @param cbComplaintFieldDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CbComplaintFieldDTO> partialUpdate(CbComplaintFieldDTO cbComplaintFieldDTO);

    /**
     * Get all the cbComplaintFields.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CbComplaintFieldDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cbComplaintField.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CbComplaintFieldDTO> findOne(Long id);

    /**
     * Delete the "id" cbComplaintField.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
