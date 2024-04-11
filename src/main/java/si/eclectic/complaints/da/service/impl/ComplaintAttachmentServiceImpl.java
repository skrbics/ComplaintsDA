package si.eclectic.complaints.da.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.complaints.da.domain.ComplaintAttachment;
import si.eclectic.complaints.da.repository.ComplaintAttachmentRepository;
import si.eclectic.complaints.da.service.ComplaintAttachmentService;
import si.eclectic.complaints.da.service.dto.ComplaintAttachmentDTO;
import si.eclectic.complaints.da.service.mapper.ComplaintAttachmentMapper;

/**
 * Service Implementation for managing {@link si.eclectic.complaints.da.domain.ComplaintAttachment}.
 */
@Service
@Transactional
public class ComplaintAttachmentServiceImpl implements ComplaintAttachmentService {

    private final Logger log = LoggerFactory.getLogger(ComplaintAttachmentServiceImpl.class);

    private final ComplaintAttachmentRepository complaintAttachmentRepository;

    private final ComplaintAttachmentMapper complaintAttachmentMapper;

    public ComplaintAttachmentServiceImpl(
        ComplaintAttachmentRepository complaintAttachmentRepository,
        ComplaintAttachmentMapper complaintAttachmentMapper
    ) {
        this.complaintAttachmentRepository = complaintAttachmentRepository;
        this.complaintAttachmentMapper = complaintAttachmentMapper;
    }

    @Override
    public ComplaintAttachmentDTO save(ComplaintAttachmentDTO complaintAttachmentDTO) {
        log.debug("Request to save ComplaintAttachment : {}", complaintAttachmentDTO);
        ComplaintAttachment complaintAttachment = complaintAttachmentMapper.toEntity(complaintAttachmentDTO);
        complaintAttachment = complaintAttachmentRepository.save(complaintAttachment);
        return complaintAttachmentMapper.toDto(complaintAttachment);
    }

    @Override
    public ComplaintAttachmentDTO update(ComplaintAttachmentDTO complaintAttachmentDTO) {
        log.debug("Request to update ComplaintAttachment : {}", complaintAttachmentDTO);
        ComplaintAttachment complaintAttachment = complaintAttachmentMapper.toEntity(complaintAttachmentDTO);
        complaintAttachment = complaintAttachmentRepository.save(complaintAttachment);
        return complaintAttachmentMapper.toDto(complaintAttachment);
    }

    @Override
    public Optional<ComplaintAttachmentDTO> partialUpdate(ComplaintAttachmentDTO complaintAttachmentDTO) {
        log.debug("Request to partially update ComplaintAttachment : {}", complaintAttachmentDTO);

        return complaintAttachmentRepository
            .findById(complaintAttachmentDTO.getId())
            .map(existingComplaintAttachment -> {
                complaintAttachmentMapper.partialUpdate(existingComplaintAttachment, complaintAttachmentDTO);

                return existingComplaintAttachment;
            })
            .map(complaintAttachmentRepository::save)
            .map(complaintAttachmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ComplaintAttachmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ComplaintAttachments");
        return complaintAttachmentRepository.findAll(pageable).map(complaintAttachmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ComplaintAttachmentDTO> findOne(Long id) {
        log.debug("Request to get ComplaintAttachment : {}", id);
        return complaintAttachmentRepository.findById(id).map(complaintAttachmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ComplaintAttachment : {}", id);
        complaintAttachmentRepository.deleteById(id);
    }
}
