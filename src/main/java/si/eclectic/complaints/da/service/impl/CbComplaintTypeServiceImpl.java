package si.eclectic.complaints.da.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.complaints.da.domain.CbComplaintType;
import si.eclectic.complaints.da.repository.CbComplaintTypeRepository;
import si.eclectic.complaints.da.service.CbComplaintTypeService;
import si.eclectic.complaints.da.service.dto.CbComplaintTypeDTO;
import si.eclectic.complaints.da.service.mapper.CbComplaintTypeMapper;

/**
 * Service Implementation for managing {@link si.eclectic.complaints.da.domain.CbComplaintType}.
 */
@Service
@Transactional
public class CbComplaintTypeServiceImpl implements CbComplaintTypeService {

    private final Logger log = LoggerFactory.getLogger(CbComplaintTypeServiceImpl.class);

    private final CbComplaintTypeRepository cbComplaintTypeRepository;

    private final CbComplaintTypeMapper cbComplaintTypeMapper;

    public CbComplaintTypeServiceImpl(CbComplaintTypeRepository cbComplaintTypeRepository, CbComplaintTypeMapper cbComplaintTypeMapper) {
        this.cbComplaintTypeRepository = cbComplaintTypeRepository;
        this.cbComplaintTypeMapper = cbComplaintTypeMapper;
    }

    @Override
    public CbComplaintTypeDTO save(CbComplaintTypeDTO cbComplaintTypeDTO) {
        log.debug("Request to save CbComplaintType : {}", cbComplaintTypeDTO);
        CbComplaintType cbComplaintType = cbComplaintTypeMapper.toEntity(cbComplaintTypeDTO);
        cbComplaintType = cbComplaintTypeRepository.save(cbComplaintType);
        return cbComplaintTypeMapper.toDto(cbComplaintType);
    }

    @Override
    public CbComplaintTypeDTO update(CbComplaintTypeDTO cbComplaintTypeDTO) {
        log.debug("Request to update CbComplaintType : {}", cbComplaintTypeDTO);
        CbComplaintType cbComplaintType = cbComplaintTypeMapper.toEntity(cbComplaintTypeDTO);
        cbComplaintType = cbComplaintTypeRepository.save(cbComplaintType);
        return cbComplaintTypeMapper.toDto(cbComplaintType);
    }

    @Override
    public Optional<CbComplaintTypeDTO> partialUpdate(CbComplaintTypeDTO cbComplaintTypeDTO) {
        log.debug("Request to partially update CbComplaintType : {}", cbComplaintTypeDTO);

        return cbComplaintTypeRepository
            .findById(cbComplaintTypeDTO.getId())
            .map(existingCbComplaintType -> {
                cbComplaintTypeMapper.partialUpdate(existingCbComplaintType, cbComplaintTypeDTO);

                return existingCbComplaintType;
            })
            .map(cbComplaintTypeRepository::save)
            .map(cbComplaintTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CbComplaintTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CbComplaintTypes");
        return cbComplaintTypeRepository.findAll(pageable).map(cbComplaintTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CbComplaintTypeDTO> findOne(Long id) {
        log.debug("Request to get CbComplaintType : {}", id);
        return cbComplaintTypeRepository.findById(id).map(cbComplaintTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CbComplaintType : {}", id);
        cbComplaintTypeRepository.deleteById(id);
    }
}
