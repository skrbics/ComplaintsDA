package si.eclectic.complaints.da.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import si.eclectic.complaints.da.service.dto.CbCityDTO;

/**
 * Service Interface for managing {@link si.eclectic.complaints.da.domain.CbCity}.
 */
public interface CbCityService {
    /**
     * Save a cbCity.
     *
     * @param cbCityDTO the entity to save.
     * @return the persisted entity.
     */
    CbCityDTO save(CbCityDTO cbCityDTO);

    /**
     * Updates a cbCity.
     *
     * @param cbCityDTO the entity to update.
     * @return the persisted entity.
     */
    CbCityDTO update(CbCityDTO cbCityDTO);

    /**
     * Partially updates a cbCity.
     *
     * @param cbCityDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CbCityDTO> partialUpdate(CbCityDTO cbCityDTO);

    /**
     * Get all the cbCities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CbCityDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cbCity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CbCityDTO> findOne(Long id);

    /**
     * Delete the "id" cbCity.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
