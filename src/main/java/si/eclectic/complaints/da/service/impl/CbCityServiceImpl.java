package si.eclectic.complaints.da.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.complaints.da.domain.CbCity;
import si.eclectic.complaints.da.repository.CbCityRepository;
import si.eclectic.complaints.da.service.CbCityService;
import si.eclectic.complaints.da.service.dto.CbCityDTO;
import si.eclectic.complaints.da.service.mapper.CbCityMapper;

/**
 * Service Implementation for managing {@link si.eclectic.complaints.da.domain.CbCity}.
 */
@Service
@Transactional
public class CbCityServiceImpl implements CbCityService {

    private final Logger log = LoggerFactory.getLogger(CbCityServiceImpl.class);

    private final CbCityRepository cbCityRepository;

    private final CbCityMapper cbCityMapper;

    public CbCityServiceImpl(CbCityRepository cbCityRepository, CbCityMapper cbCityMapper) {
        this.cbCityRepository = cbCityRepository;
        this.cbCityMapper = cbCityMapper;
    }

    @Override
    public CbCityDTO save(CbCityDTO cbCityDTO) {
        log.debug("Request to save CbCity : {}", cbCityDTO);
        CbCity cbCity = cbCityMapper.toEntity(cbCityDTO);
        cbCity = cbCityRepository.save(cbCity);
        return cbCityMapper.toDto(cbCity);
    }

    @Override
    public CbCityDTO update(CbCityDTO cbCityDTO) {
        log.debug("Request to update CbCity : {}", cbCityDTO);
        CbCity cbCity = cbCityMapper.toEntity(cbCityDTO);
        cbCity = cbCityRepository.save(cbCity);
        return cbCityMapper.toDto(cbCity);
    }

    @Override
    public Optional<CbCityDTO> partialUpdate(CbCityDTO cbCityDTO) {
        log.debug("Request to partially update CbCity : {}", cbCityDTO);

        return cbCityRepository
            .findById(cbCityDTO.getId())
            .map(existingCbCity -> {
                cbCityMapper.partialUpdate(existingCbCity, cbCityDTO);

                return existingCbCity;
            })
            .map(cbCityRepository::save)
            .map(cbCityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CbCityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CbCities");
        return cbCityRepository.findAll(pageable).map(cbCityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CbCityDTO> findOne(Long id) {
        log.debug("Request to get CbCity : {}", id);
        return cbCityRepository.findById(id).map(cbCityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CbCity : {}", id);
        cbCityRepository.deleteById(id);
    }
}
