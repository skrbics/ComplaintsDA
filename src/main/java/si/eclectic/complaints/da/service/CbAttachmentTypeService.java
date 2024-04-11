package si.eclectic.complaints.da.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import si.eclectic.complaints.da.service.dto.CbAttachmentTypeDTO;

/**
 * Service Interface for managing {@link si.eclectic.complaints.da.domain.CbAttachmentType}.
 */
public interface CbAttachmentTypeService {
    /**
     * Save a cbAttachmentType.
     *
     * @param cbAttachmentTypeDTO the entity to save.
     * @return the persisted entity.
     */
    CbAttachmentTypeDTO save(CbAttachmentTypeDTO cbAttachmentTypeDTO);

    /**
     * Updates a cbAttachmentType.
     *
     * @param cbAttachmentTypeDTO the entity to update.
     * @return the persisted entity.
     */
    CbAttachmentTypeDTO update(CbAttachmentTypeDTO cbAttachmentTypeDTO);

    /**
     * Partially updates a cbAttachmentType.
     *
     * @param cbAttachmentTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CbAttachmentTypeDTO> partialUpdate(CbAttachmentTypeDTO cbAttachmentTypeDTO);

    /**
     * Get all the cbAttachmentTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CbAttachmentTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cbAttachmentType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CbAttachmentTypeDTO> findOne(Long id);

    /**
     * Delete the "id" cbAttachmentType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
