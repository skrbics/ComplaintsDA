package si.eclectic.complaints.da.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import si.eclectic.complaints.da.service.dto.CbComplaintChannelDTO;

/**
 * Service Interface for managing {@link si.eclectic.complaints.da.domain.CbComplaintChannel}.
 */
public interface CbComplaintChannelService {
    /**
     * Save a cbComplaintChannel.
     *
     * @param cbComplaintChannelDTO the entity to save.
     * @return the persisted entity.
     */
    CbComplaintChannelDTO save(CbComplaintChannelDTO cbComplaintChannelDTO);

    /**
     * Updates a cbComplaintChannel.
     *
     * @param cbComplaintChannelDTO the entity to update.
     * @return the persisted entity.
     */
    CbComplaintChannelDTO update(CbComplaintChannelDTO cbComplaintChannelDTO);

    /**
     * Partially updates a cbComplaintChannel.
     *
     * @param cbComplaintChannelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CbComplaintChannelDTO> partialUpdate(CbComplaintChannelDTO cbComplaintChannelDTO);

    /**
     * Get all the cbComplaintChannels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CbComplaintChannelDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cbComplaintChannel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CbComplaintChannelDTO> findOne(Long id);

    /**
     * Delete the "id" cbComplaintChannel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
