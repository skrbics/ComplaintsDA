package si.eclectic.complaints.da.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import si.eclectic.complaints.da.service.dto.CbComplaintSubFieldDTO;

/**
 * Service Interface for managing {@link si.eclectic.complaints.da.domain.CbComplaintSubField}.
 */
public interface CbComplaintSubFieldService {
    /**
     * Save a cbComplaintSubField.
     *
     * @param cbComplaintSubFieldDTO the entity to save.
     * @return the persisted entity.
     */
    CbComplaintSubFieldDTO save(CbComplaintSubFieldDTO cbComplaintSubFieldDTO);

    /**
     * Updates a cbComplaintSubField.
     *
     * @param cbComplaintSubFieldDTO the entity to update.
     * @return the persisted entity.
     */
    CbComplaintSubFieldDTO update(CbComplaintSubFieldDTO cbComplaintSubFieldDTO);

    /**
     * Partially updates a cbComplaintSubField.
     *
     * @param cbComplaintSubFieldDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CbComplaintSubFieldDTO> partialUpdate(CbComplaintSubFieldDTO cbComplaintSubFieldDTO);

    /**
     * Get all the cbComplaintSubFields.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CbComplaintSubFieldDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cbComplaintSubField.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CbComplaintSubFieldDTO> findOne(Long id);

    /**
     * Delete the "id" cbComplaintSubField.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
