package si.eclectic.complaints.da.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import si.eclectic.complaints.da.service.dto.ComplaintDTO;

/**
 * Service Interface for managing {@link si.eclectic.complaints.da.domain.Complaint}.
 */
public interface ComplaintService {
    /**
     * Save a complaint.
     *
     * @param complaintDTO the entity to save.
     * @return the persisted entity.
     */
    ComplaintDTO save(ComplaintDTO complaintDTO);

    /**
     * Updates a complaint.
     *
     * @param complaintDTO the entity to update.
     * @return the persisted entity.
     */
    ComplaintDTO update(ComplaintDTO complaintDTO);

    /**
     * Partially updates a complaint.
     *
     * @param complaintDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ComplaintDTO> partialUpdate(ComplaintDTO complaintDTO);

    /**
     * Get all the complaints.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ComplaintDTO> findAll(Pageable pageable);

    /**
     * Get the "id" complaint.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ComplaintDTO> findOne(Long id);

    /**
     * Delete the "id" complaint.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
