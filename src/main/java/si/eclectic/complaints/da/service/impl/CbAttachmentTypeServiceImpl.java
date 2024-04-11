package si.eclectic.complaints.da.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.complaints.da.domain.CbAttachmentType;
import si.eclectic.complaints.da.repository.CbAttachmentTypeRepository;
import si.eclectic.complaints.da.service.CbAttachmentTypeService;
import si.eclectic.complaints.da.service.dto.CbAttachmentTypeDTO;
import si.eclectic.complaints.da.service.mapper.CbAttachmentTypeMapper;

/**
 * Service Implementation for managing {@link si.eclectic.complaints.da.domain.CbAttachmentType}.
 */
@Service
@Transactional
public class CbAttachmentTypeServiceImpl implements CbAttachmentTypeService {

    private final Logger log = LoggerFactory.getLogger(CbAttachmentTypeServiceImpl.class);

    private final CbAttachmentTypeRepository cbAttachmentTypeRepository;

    private final CbAttachmentTypeMapper cbAttachmentTypeMapper;

    public CbAttachmentTypeServiceImpl(
        CbAttachmentTypeRepository cbAttachmentTypeRepository,
        CbAttachmentTypeMapper cbAttachmentTypeMapper
    ) {
        this.cbAttachmentTypeRepository = cbAttachmentTypeRepository;
        this.cbAttachmentTypeMapper = cbAttachmentTypeMapper;
    }

    @Override
    public CbAttachmentTypeDTO save(CbAttachmentTypeDTO cbAttachmentTypeDTO) {
        log.debug("Request to save CbAttachmentType : {}", cbAttachmentTypeDTO);
        CbAttachmentType cbAttachmentType = cbAttachmentTypeMapper.toEntity(cbAttachmentTypeDTO);
        cbAttachmentType = cbAttachmentTypeRepository.save(cbAttachmentType);
        return cbAttachmentTypeMapper.toDto(cbAttachmentType);
    }

    @Override
    public CbAttachmentTypeDTO update(CbAttachmentTypeDTO cbAttachmentTypeDTO) {
        log.debug("Request to update CbAttachmentType : {}", cbAttachmentTypeDTO);
        CbAttachmentType cbAttachmentType = cbAttachmentTypeMapper.toEntity(cbAttachmentTypeDTO);
        cbAttachmentType = cbAttachmentTypeRepository.save(cbAttachmentType);
        return cbAttachmentTypeMapper.toDto(cbAttachmentType);
    }

    @Override
    public Optional<CbAttachmentTypeDTO> partialUpdate(CbAttachmentTypeDTO cbAttachmentTypeDTO) {
        log.debug("Request to partially update CbAttachmentType : {}", cbAttachmentTypeDTO);

        return cbAttachmentTypeRepository
            .findById(cbAttachmentTypeDTO.getId())
            .map(existingCbAttachmentType -> {
                cbAttachmentTypeMapper.partialUpdate(existingCbAttachmentType, cbAttachmentTypeDTO);

                return existingCbAttachmentType;
            })
            .map(cbAttachmentTypeRepository::save)
            .map(cbAttachmentTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CbAttachmentTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CbAttachmentTypes");
        return cbAttachmentTypeRepository.findAll(pageable).map(cbAttachmentTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CbAttachmentTypeDTO> findOne(Long id) {
        log.debug("Request to get CbAttachmentType : {}", id);
        return cbAttachmentTypeRepository.findById(id).map(cbAttachmentTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CbAttachmentType : {}", id);
        cbAttachmentTypeRepository.deleteById(id);
    }
}
