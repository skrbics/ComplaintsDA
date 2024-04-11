package si.eclectic.complaints.da.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.complaints.da.domain.CbComplaintSubField;
import si.eclectic.complaints.da.repository.CbComplaintSubFieldRepository;
import si.eclectic.complaints.da.service.CbComplaintSubFieldService;
import si.eclectic.complaints.da.service.dto.CbComplaintSubFieldDTO;
import si.eclectic.complaints.da.service.mapper.CbComplaintSubFieldMapper;

/**
 * Service Implementation for managing {@link si.eclectic.complaints.da.domain.CbComplaintSubField}.
 */
@Service
@Transactional
public class CbComplaintSubFieldServiceImpl implements CbComplaintSubFieldService {

    private final Logger log = LoggerFactory.getLogger(CbComplaintSubFieldServiceImpl.class);

    private final CbComplaintSubFieldRepository cbComplaintSubFieldRepository;

    private final CbComplaintSubFieldMapper cbComplaintSubFieldMapper;

    public CbComplaintSubFieldServiceImpl(
        CbComplaintSubFieldRepository cbComplaintSubFieldRepository,
        CbComplaintSubFieldMapper cbComplaintSubFieldMapper
    ) {
        this.cbComplaintSubFieldRepository = cbComplaintSubFieldRepository;
        this.cbComplaintSubFieldMapper = cbComplaintSubFieldMapper;
    }

    @Override
    public CbComplaintSubFieldDTO save(CbComplaintSubFieldDTO cbComplaintSubFieldDTO) {
        log.debug("Request to save CbComplaintSubField : {}", cbComplaintSubFieldDTO);
        CbComplaintSubField cbComplaintSubField = cbComplaintSubFieldMapper.toEntity(cbComplaintSubFieldDTO);
        cbComplaintSubField = cbComplaintSubFieldRepository.save(cbComplaintSubField);
        return cbComplaintSubFieldMapper.toDto(cbComplaintSubField);
    }

    @Override
    public CbComplaintSubFieldDTO update(CbComplaintSubFieldDTO cbComplaintSubFieldDTO) {
        log.debug("Request to update CbComplaintSubField : {}", cbComplaintSubFieldDTO);
        CbComplaintSubField cbComplaintSubField = cbComplaintSubFieldMapper.toEntity(cbComplaintSubFieldDTO);
        cbComplaintSubField = cbComplaintSubFieldRepository.save(cbComplaintSubField);
        return cbComplaintSubFieldMapper.toDto(cbComplaintSubField);
    }

    @Override
    public Optional<CbComplaintSubFieldDTO> partialUpdate(CbComplaintSubFieldDTO cbComplaintSubFieldDTO) {
        log.debug("Request to partially update CbComplaintSubField : {}", cbComplaintSubFieldDTO);

        return cbComplaintSubFieldRepository
            .findById(cbComplaintSubFieldDTO.getId())
            .map(existingCbComplaintSubField -> {
                cbComplaintSubFieldMapper.partialUpdate(existingCbComplaintSubField, cbComplaintSubFieldDTO);

                return existingCbComplaintSubField;
            })
            .map(cbComplaintSubFieldRepository::save)
            .map(cbComplaintSubFieldMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CbComplaintSubFieldDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CbComplaintSubFields");
        return cbComplaintSubFieldRepository.findAll(pageable).map(cbComplaintSubFieldMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CbComplaintSubFieldDTO> findOne(Long id) {
        log.debug("Request to get CbComplaintSubField : {}", id);
        return cbComplaintSubFieldRepository.findById(id).map(cbComplaintSubFieldMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CbComplaintSubField : {}", id);
        cbComplaintSubFieldRepository.deleteById(id);
    }
}
