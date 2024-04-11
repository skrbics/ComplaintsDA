package si.eclectic.complaints.da.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import si.eclectic.complaints.da.service.dto.CbApplicantCapacityTypeDTO;

/**
 * Service Interface for managing {@link si.eclectic.complaints.da.domain.CbApplicantCapacityType}.
 */
public interface CbApplicantCapacityTypeService {
    /**
     * Save a cbApplicantCapacityType.
     *
     * @param cbApplicantCapacityTypeDTO the entity to save.
     * @return the persisted entity.
     */
    CbApplicantCapacityTypeDTO save(CbApplicantCapacityTypeDTO cbApplicantCapacityTypeDTO);

    /**
     * Updates a cbApplicantCapacityType.
     *
     * @param cbApplicantCapacityTypeDTO the entity to update.
     * @return the persisted entity.
     */
    CbApplicantCapacityTypeDTO update(CbApplicantCapacityTypeDTO cbApplicantCapacityTypeDTO);

    /**
     * Partially updates a cbApplicantCapacityType.
     *
     * @param cbApplicantCapacityTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CbApplicantCapacityTypeDTO> partialUpdate(CbApplicantCapacityTypeDTO cbApplicantCapacityTypeDTO);

    /**
     * Get all the cbApplicantCapacityTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CbApplicantCapacityTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cbApplicantCapacityType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CbApplicantCapacityTypeDTO> findOne(Long id);

    /**
     * Delete the "id" cbApplicantCapacityType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
