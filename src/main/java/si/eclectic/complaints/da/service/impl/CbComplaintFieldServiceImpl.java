package si.eclectic.complaints.da.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.complaints.da.domain.CbComplaintField;
import si.eclectic.complaints.da.repository.CbComplaintFieldRepository;
import si.eclectic.complaints.da.service.CbComplaintFieldService;
import si.eclectic.complaints.da.service.dto.CbComplaintFieldDTO;
import si.eclectic.complaints.da.service.mapper.CbComplaintFieldMapper;

/**
 * Service Implementation for managing {@link si.eclectic.complaints.da.domain.CbComplaintField}.
 */
@Service
@Transactional
public class CbComplaintFieldServiceImpl implements CbComplaintFieldService {

    private final Logger log = LoggerFactory.getLogger(CbComplaintFieldServiceImpl.class);

    private final CbComplaintFieldRepository cbComplaintFieldRepository;

    private final CbComplaintFieldMapper cbComplaintFieldMapper;

    public CbComplaintFieldServiceImpl(
        CbComplaintFieldRepository cbComplaintFieldRepository,
        CbComplaintFieldMapper cbComplaintFieldMapper
    ) {
        this.cbComplaintFieldRepository = cbComplaintFieldRepository;
        this.cbComplaintFieldMapper = cbComplaintFieldMapper;
    }

    @Override
    public CbComplaintFieldDTO save(CbComplaintFieldDTO cbComplaintFieldDTO) {
        log.debug("Request to save CbComplaintField : {}", cbComplaintFieldDTO);
        CbComplaintField cbComplaintField = cbComplaintFieldMapper.toEntity(cbComplaintFieldDTO);
        cbComplaintField = cbComplaintFieldRepository.save(cbComplaintField);
        return cbComplaintFieldMapper.toDto(cbComplaintField);
    }

    @Override
    public CbComplaintFieldDTO update(CbComplaintFieldDTO cbComplaintFieldDTO) {
        log.debug("Request to update CbComplaintField : {}", cbComplaintFieldDTO);
        CbComplaintField cbComplaintField = cbComplaintFieldMapper.toEntity(cbComplaintFieldDTO);
        cbComplaintField = cbComplaintFieldRepository.save(cbComplaintField);
        return cbComplaintFieldMapper.toDto(cbComplaintField);
    }

    @Override
    public Optional<CbComplaintFieldDTO> partialUpdate(CbComplaintFieldDTO cbComplaintFieldDTO) {
        log.debug("Request to partially update CbComplaintField : {}", cbComplaintFieldDTO);

        return cbComplaintFieldRepository
            .findById(cbComplaintFieldDTO.getId())
            .map(existingCbComplaintField -> {
                cbComplaintFieldMapper.partialUpdate(existingCbComplaintField, cbComplaintFieldDTO);

                return existingCbComplaintField;
            })
            .map(cbComplaintFieldRepository::save)
            .map(cbComplaintFieldMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CbComplaintFieldDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CbComplaintFields");
        return cbComplaintFieldRepository.findAll(pageable).map(cbComplaintFieldMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CbComplaintFieldDTO> findOne(Long id) {
        log.debug("Request to get CbComplaintField : {}", id);
        return cbComplaintFieldRepository.findById(id).map(cbComplaintFieldMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CbComplaintField : {}", id);
        cbComplaintFieldRepository.deleteById(id);
    }
}
