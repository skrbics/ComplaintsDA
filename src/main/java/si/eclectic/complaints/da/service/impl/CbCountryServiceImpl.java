package si.eclectic.complaints.da.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.complaints.da.domain.CbCountry;
import si.eclectic.complaints.da.repository.CbCountryRepository;
import si.eclectic.complaints.da.service.CbCountryService;
import si.eclectic.complaints.da.service.dto.CbCountryDTO;
import si.eclectic.complaints.da.service.mapper.CbCountryMapper;

/**
 * Service Implementation for managing {@link si.eclectic.complaints.da.domain.CbCountry}.
 */
@Service
@Transactional
public class CbCountryServiceImpl implements CbCountryService {

    private final Logger log = LoggerFactory.getLogger(CbCountryServiceImpl.class);

    private final CbCountryRepository cbCountryRepository;

    private final CbCountryMapper cbCountryMapper;

    public CbCountryServiceImpl(CbCountryRepository cbCountryRepository, CbCountryMapper cbCountryMapper) {
        this.cbCountryRepository = cbCountryRepository;
        this.cbCountryMapper = cbCountryMapper;
    }

    @Override
    public CbCountryDTO save(CbCountryDTO cbCountryDTO) {
        log.debug("Request to save CbCountry : {}", cbCountryDTO);
        CbCountry cbCountry = cbCountryMapper.toEntity(cbCountryDTO);
        cbCountry = cbCountryRepository.save(cbCountry);
        return cbCountryMapper.toDto(cbCountry);
    }

    @Override
    public CbCountryDTO update(CbCountryDTO cbCountryDTO) {
        log.debug("Request to update CbCountry : {}", cbCountryDTO);
        CbCountry cbCountry = cbCountryMapper.toEntity(cbCountryDTO);
        cbCountry = cbCountryRepository.save(cbCountry);
        return cbCountryMapper.toDto(cbCountry);
    }

    @Override
    public Optional<CbCountryDTO> partialUpdate(CbCountryDTO cbCountryDTO) {
        log.debug("Request to partially update CbCountry : {}", cbCountryDTO);

        return cbCountryRepository
            .findById(cbCountryDTO.getId())
            .map(existingCbCountry -> {
                cbCountryMapper.partialUpdate(existingCbCountry, cbCountryDTO);

                return existingCbCountry;
            })
            .map(cbCountryRepository::save)
            .map(cbCountryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CbCountryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CbCountries");
        return cbCountryRepository.findAll(pageable).map(cbCountryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CbCountryDTO> findOne(Long id) {
        log.debug("Request to get CbCountry : {}", id);
        return cbCountryRepository.findById(id).map(cbCountryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CbCountry : {}", id);
        cbCountryRepository.deleteById(id);
    }
}
