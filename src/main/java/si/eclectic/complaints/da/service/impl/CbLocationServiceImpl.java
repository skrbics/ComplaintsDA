package si.eclectic.complaints.da.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.complaints.da.domain.CbLocation;
import si.eclectic.complaints.da.repository.CbLocationRepository;
import si.eclectic.complaints.da.service.CbLocationService;
import si.eclectic.complaints.da.service.dto.CbLocationDTO;
import si.eclectic.complaints.da.service.mapper.CbLocationMapper;

/**
 * Service Implementation for managing {@link si.eclectic.complaints.da.domain.CbLocation}.
 */
@Service
@Transactional
public class CbLocationServiceImpl implements CbLocationService {

    private final Logger log = LoggerFactory.getLogger(CbLocationServiceImpl.class);

    private final CbLocationRepository cbLocationRepository;

    private final CbLocationMapper cbLocationMapper;

    public CbLocationServiceImpl(CbLocationRepository cbLocationRepository, CbLocationMapper cbLocationMapper) {
        this.cbLocationRepository = cbLocationRepository;
        this.cbLocationMapper = cbLocationMapper;
    }

    @Override
    public CbLocationDTO save(CbLocationDTO cbLocationDTO) {
        log.debug("Request to save CbLocation : {}", cbLocationDTO);
        CbLocation cbLocation = cbLocationMapper.toEntity(cbLocationDTO);
        cbLocation = cbLocationRepository.save(cbLocation);
        return cbLocationMapper.toDto(cbLocation);
    }

    @Override
    public CbLocationDTO update(CbLocationDTO cbLocationDTO) {
        log.debug("Request to update CbLocation : {}", cbLocationDTO);
        CbLocation cbLocation = cbLocationMapper.toEntity(cbLocationDTO);
        cbLocation = cbLocationRepository.save(cbLocation);
        return cbLocationMapper.toDto(cbLocation);
    }

    @Override
    public Optional<CbLocationDTO> partialUpdate(CbLocationDTO cbLocationDTO) {
        log.debug("Request to partially update CbLocation : {}", cbLocationDTO);

        return cbLocationRepository
            .findById(cbLocationDTO.getId())
            .map(existingCbLocation -> {
                cbLocationMapper.partialUpdate(existingCbLocation, cbLocationDTO);

                return existingCbLocation;
            })
            .map(cbLocationRepository::save)
            .map(cbLocationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CbLocationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CbLocations");
        return cbLocationRepository.findAll(pageable).map(cbLocationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CbLocationDTO> findOne(Long id) {
        log.debug("Request to get CbLocation : {}", id);
        return cbLocationRepository.findById(id).map(cbLocationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CbLocation : {}", id);
        cbLocationRepository.deleteById(id);
    }
}
