package si.eclectic.complaints.da.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import si.eclectic.complaints.da.service.dto.CbComplaintTypeDTO;

/**
 * Service Interface for managing {@link si.eclectic.complaints.da.domain.CbComplaintType}.
 */
public interface CbComplaintTypeService {
    /**
     * Save a cbComplaintType.
     *
     * @param cbComplaintTypeDTO the entity to save.
     * @return the persisted entity.
     */
    CbComplaintTypeDTO save(CbComplaintTypeDTO cbComplaintTypeDTO);

    /**
     * Updates a cbComplaintType.
     *
     * @param cbComplaintTypeDTO the entity to update.
     * @return the persisted entity.
     */
    CbComplaintTypeDTO update(CbComplaintTypeDTO cbComplaintTypeDTO);

    /**
     * Partially updates a cbComplaintType.
     *
     * @param cbComplaintTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CbComplaintTypeDTO> partialUpdate(CbComplaintTypeDTO cbComplaintTypeDTO);

    /**
     * Get all the cbComplaintTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CbComplaintTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cbComplaintType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CbComplaintTypeDTO> findOne(Long id);

    /**
     * Delete the "id" cbComplaintType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
