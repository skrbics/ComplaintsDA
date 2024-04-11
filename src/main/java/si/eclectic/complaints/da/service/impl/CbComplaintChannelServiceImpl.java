package si.eclectic.complaints.da.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.complaints.da.domain.CbComplaintChannel;
import si.eclectic.complaints.da.repository.CbComplaintChannelRepository;
import si.eclectic.complaints.da.service.CbComplaintChannelService;
import si.eclectic.complaints.da.service.dto.CbComplaintChannelDTO;
import si.eclectic.complaints.da.service.mapper.CbComplaintChannelMapper;

/**
 * Service Implementation for managing {@link si.eclectic.complaints.da.domain.CbComplaintChannel}.
 */
@Service
@Transactional
public class CbComplaintChannelServiceImpl implements CbComplaintChannelService {

    private final Logger log = LoggerFactory.getLogger(CbComplaintChannelServiceImpl.class);

    private final CbComplaintChannelRepository cbComplaintChannelRepository;

    private final CbComplaintChannelMapper cbComplaintChannelMapper;

    public CbComplaintChannelServiceImpl(
        CbComplaintChannelRepository cbComplaintChannelRepository,
        CbComplaintChannelMapper cbComplaintChannelMapper
    ) {
        this.cbComplaintChannelRepository = cbComplaintChannelRepository;
        this.cbComplaintChannelMapper = cbComplaintChannelMapper;
    }

    @Override
    public CbComplaintChannelDTO save(CbComplaintChannelDTO cbComplaintChannelDTO) {
        log.debug("Request to save CbComplaintChannel : {}", cbComplaintChannelDTO);
        CbComplaintChannel cbComplaintChannel = cbComplaintChannelMapper.toEntity(cbComplaintChannelDTO);
        cbComplaintChannel = cbComplaintChannelRepository.save(cbComplaintChannel);
        return cbComplaintChannelMapper.toDto(cbComplaintChannel);
    }

    @Override
    public CbComplaintChannelDTO update(CbComplaintChannelDTO cbComplaintChannelDTO) {
        log.debug("Request to update CbComplaintChannel : {}", cbComplaintChannelDTO);
        CbComplaintChannel cbComplaintChannel = cbComplaintChannelMapper.toEntity(cbComplaintChannelDTO);
        cbComplaintChannel = cbComplaintChannelRepository.save(cbComplaintChannel);
        return cbComplaintChannelMapper.toDto(cbComplaintChannel);
    }

    @Override
    public Optional<CbComplaintChannelDTO> partialUpdate(CbComplaintChannelDTO cbComplaintChannelDTO) {
        log.debug("Request to partially update CbComplaintChannel : {}", cbComplaintChannelDTO);

        return cbComplaintChannelRepository
            .findById(cbComplaintChannelDTO.getId())
            .map(existingCbComplaintChannel -> {
                cbComplaintChannelMapper.partialUpdate(existingCbComplaintChannel, cbComplaintChannelDTO);

                return existingCbComplaintChannel;
            })
            .map(cbComplaintChannelRepository::save)
            .map(cbComplaintChannelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CbComplaintChannelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CbComplaintChannels");
        return cbComplaintChannelRepository.findAll(pageable).map(cbComplaintChannelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CbComplaintChannelDTO> findOne(Long id) {
        log.debug("Request to get CbComplaintChannel : {}", id);
        return cbComplaintChannelRepository.findById(id).map(cbComplaintChannelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CbComplaintChannel : {}", id);
        cbComplaintChannelRepository.deleteById(id);
    }
}
