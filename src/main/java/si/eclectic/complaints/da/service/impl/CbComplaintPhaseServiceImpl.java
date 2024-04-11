package si.eclectic.complaints.da.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.complaints.da.domain.CbComplaintPhase;
import si.eclectic.complaints.da.repository.CbComplaintPhaseRepository;
import si.eclectic.complaints.da.service.CbComplaintPhaseService;
import si.eclectic.complaints.da.service.dto.CbComplaintPhaseDTO;
import si.eclectic.complaints.da.service.mapper.CbComplaintPhaseMapper;

/**
 * Service Implementation for managing {@link si.eclectic.complaints.da.domain.CbComplaintPhase}.
 */
@Service
@Transactional
public class CbComplaintPhaseServiceImpl implements CbComplaintPhaseService {

    private final Logger log = LoggerFactory.getLogger(CbComplaintPhaseServiceImpl.class);

    private final CbComplaintPhaseRepository cbComplaintPhaseRepository;

    private final CbComplaintPhaseMapper cbComplaintPhaseMapper;

    public CbComplaintPhaseServiceImpl(
        CbComplaintPhaseRepository cbComplaintPhaseRepository,
        CbComplaintPhaseMapper cbComplaintPhaseMapper
    ) {
        this.cbComplaintPhaseRepository = cbComplaintPhaseRepository;
        this.cbComplaintPhaseMapper = cbComplaintPhaseMapper;
    }

    @Override
    public CbComplaintPhaseDTO save(CbComplaintPhaseDTO cbComplaintPhaseDTO) {
        log.debug("Request to save CbComplaintPhase : {}", cbComplaintPhaseDTO);
        CbComplaintPhase cbComplaintPhase = cbComplaintPhaseMapper.toEntity(cbComplaintPhaseDTO);
        cbComplaintPhase = cbComplaintPhaseRepository.save(cbComplaintPhase);
        return cbComplaintPhaseMapper.toDto(cbComplaintPhase);
    }

    @Override
    public CbComplaintPhaseDTO update(CbComplaintPhaseDTO cbComplaintPhaseDTO) {
        log.debug("Request to update CbComplaintPhase : {}", cbComplaintPhaseDTO);
        CbComplaintPhase cbComplaintPhase = cbComplaintPhaseMapper.toEntity(cbComplaintPhaseDTO);
        cbComplaintPhase = cbComplaintPhaseRepository.save(cbComplaintPhase);
        return cbComplaintPhaseMapper.toDto(cbComplaintPhase);
    }

    @Override
    public Optional<CbComplaintPhaseDTO> partialUpdate(CbComplaintPhaseDTO cbComplaintPhaseDTO) {
        log.debug("Request to partially update CbComplaintPhase : {}", cbComplaintPhaseDTO);

        return cbComplaintPhaseRepository
            .findById(cbComplaintPhaseDTO.getId())
            .map(existingCbComplaintPhase -> {
                cbComplaintPhaseMapper.partialUpdate(existingCbComplaintPhase, cbComplaintPhaseDTO);

                return existingCbComplaintPhase;
            })
            .map(cbComplaintPhaseRepository::save)
            .map(cbComplaintPhaseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CbComplaintPhaseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CbComplaintPhases");
        return cbComplaintPhaseRepository.findAll(pageable).map(cbComplaintPhaseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CbComplaintPhaseDTO> findOne(Long id) {
        log.debug("Request to get CbComplaintPhase : {}", id);
        return cbComplaintPhaseRepository.findById(id).map(cbComplaintPhaseMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CbComplaintPhase : {}", id);
        cbComplaintPhaseRepository.deleteById(id);
    }
}
