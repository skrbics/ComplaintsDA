package si.eclectic.complaints.da.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import si.eclectic.complaints.da.service.dto.ComplaintAttachmentDTO;

/**
 * Service Interface for managing {@link si.eclectic.complaints.da.domain.ComplaintAttachment}.
 */
public interface ComplaintAttachmentService {
    /**
     * Save a complaintAttachment.
     *
     * @param complaintAttachmentDTO the entity to save.
     * @return the persisted entity.
     */
    ComplaintAttachmentDTO save(ComplaintAttachmentDTO complaintAttachmentDTO);

    /**
     * Updates a complaintAttachment.
     *
     * @param complaintAttachmentDTO the entity to update.
     * @return the persisted entity.
     */
    ComplaintAttachmentDTO update(ComplaintAttachmentDTO complaintAttachmentDTO);

    /**
     * Partially updates a complaintAttachment.
     *
     * @param complaintAttachmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ComplaintAttachmentDTO> partialUpdate(ComplaintAttachmentDTO complaintAttachmentDTO);

    /**
     * Get all the complaintAttachments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ComplaintAttachmentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" complaintAttachment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ComplaintAttachmentDTO> findOne(Long id);

    /**
     * Delete the "id" complaintAttachment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
