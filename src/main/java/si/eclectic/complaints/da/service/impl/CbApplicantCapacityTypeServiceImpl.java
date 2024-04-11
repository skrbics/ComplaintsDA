package si.eclectic.complaints.da.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.complaints.da.domain.CbApplicantCapacityType;
import si.eclectic.complaints.da.repository.CbApplicantCapacityTypeRepository;
import si.eclectic.complaints.da.service.CbApplicantCapacityTypeService;
import si.eclectic.complaints.da.service.dto.CbApplicantCapacityTypeDTO;
import si.eclectic.complaints.da.service.mapper.CbApplicantCapacityTypeMapper;

/**
 * Service Implementation for managing {@link si.eclectic.complaints.da.domain.CbApplicantCapacityType}.
 */
@Service
@Transactional
public class CbApplicantCapacityTypeServiceImpl implements CbApplicantCapacityTypeService {

    private final Logger log = LoggerFactory.getLogger(CbApplicantCapacityTypeServiceImpl.class);

    private final CbApplicantCapacityTypeRepository cbApplicantCapacityTypeRepository;

    private final CbApplicantCapacityTypeMapper cbApplicantCapacityTypeMapper;

    public CbApplicantCapacityTypeServiceImpl(
        CbApplicantCapacityTypeRepository cbApplicantCapacityTypeRepository,
        CbApplicantCapacityTypeMapper cbApplicantCapacityTypeMapper
    ) {
        this.cbApplicantCapacityTypeRepository = cbApplicantCapacityTypeRepository;
        this.cbApplicantCapacityTypeMapper = cbApplicantCapacityTypeMapper;
    }

    @Override
    public CbApplicantCapacityTypeDTO save(CbApplicantCapacityTypeDTO cbApplicantCapacityTypeDTO) {
        log.debug("Request to save CbApplicantCapacityType : {}", cbApplicantCapacityTypeDTO);
        CbApplicantCapacityType cbApplicantCapacityType = cbApplicantCapacityTypeMapper.toEntity(cbApplicantCapacityTypeDTO);
        cbApplicantCapacityType = cbApplicantCapacityTypeRepository.save(cbApplicantCapacityType);
        return cbApplicantCapacityTypeMapper.toDto(cbApplicantCapacityType);
    }

    @Override
    public CbApplicantCapacityTypeDTO update(CbApplicantCapacityTypeDTO cbApplicantCapacityTypeDTO) {
        log.debug("Request to update CbApplicantCapacityType : {}", cbApplicantCapacityTypeDTO);
        CbApplicantCapacityType cbApplicantCapacityType = cbApplicantCapacityTypeMapper.toEntity(cbApplicantCapacityTypeDTO);
        cbApplicantCapacityType = cbApplicantCapacityTypeRepository.save(cbApplicantCapacityType);
        return cbApplicantCapacityTypeMapper.toDto(cbApplicantCapacityType);
    }

    @Override
    public Optional<CbApplicantCapacityTypeDTO> partialUpdate(CbApplicantCapacityTypeDTO cbApplicantCapacityTypeDTO) {
        log.debug("Request to partially update CbApplicantCapacityType : {}", cbApplicantCapacityTypeDTO);

        return cbApplicantCapacityTypeRepository
            .findById(cbApplicantCapacityTypeDTO.getId())
            .map(existingCbApplicantCapacityType -> {
                cbApplicantCapacityTypeMapper.partialUpdate(existingCbApplicantCapacityType, cbApplicantCapacityTypeDTO);

                return existingCbApplicantCapacityType;
            })
            .map(cbApplicantCapacityTypeRepository::save)
            .map(cbApplicantCapacityTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CbApplicantCapacityTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CbApplicantCapacityTypes");
        return cbApplicantCapacityTypeRepository.findAll(pageable).map(cbApplicantCapacityTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CbApplicantCapacityTypeDTO> findOne(Long id) {
        log.debug("Request to get CbApplicantCapacityType : {}", id);
        return cbApplicantCapacityTypeRepository.findById(id).map(cbApplicantCapacityTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CbApplicantCapacityType : {}", id);
        cbApplicantCapacityTypeRepository.deleteById(id);
    }
}
