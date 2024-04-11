package si.eclectic.complaints.da.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import si.eclectic.complaints.da.service.dto.CbCountryDTO;

/**
 * Service Interface for managing {@link si.eclectic.complaints.da.domain.CbCountry}.
 */
public interface CbCountryService {
    /**
     * Save a cbCountry.
     *
     * @param cbCountryDTO the entity to save.
     * @return the persisted entity.
     */
    CbCountryDTO save(CbCountryDTO cbCountryDTO);

    /**
     * Updates a cbCountry.
     *
     * @param cbCountryDTO the entity to update.
     * @return the persisted entity.
     */
    CbCountryDTO update(CbCountryDTO cbCountryDTO);

    /**
     * Partially updates a cbCountry.
     *
     * @param cbCountryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CbCountryDTO> partialUpdate(CbCountryDTO cbCountryDTO);

    /**
     * Get all the cbCountries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CbCountryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cbCountry.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CbCountryDTO> findOne(Long id);

    /**
     * Delete the "id" cbCountry.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
