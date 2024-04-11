package si.eclectic.complaints.da.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.complaints.da.domain.Applicant;
import si.eclectic.complaints.da.repository.ApplicantRepository;
import si.eclectic.complaints.da.service.ApplicantService;
import si.eclectic.complaints.da.service.dto.ApplicantDTO;
import si.eclectic.complaints.da.service.mapper.ApplicantMapper;

/**
 * Service Implementation for managing {@link si.eclectic.complaints.da.domain.Applicant}.
 */
@Service
@Transactional
public class ApplicantServiceImpl implements ApplicantService {

    private final Logger log = LoggerFactory.getLogger(ApplicantServiceImpl.class);

    private final ApplicantRepository applicantRepository;

    private final ApplicantMapper applicantMapper;

    public ApplicantServiceImpl(ApplicantRepository applicantRepository, ApplicantMapper applicantMapper) {
        this.applicantRepository = applicantRepository;
        this.applicantMapper = applicantMapper;
    }

    @Override
    public ApplicantDTO save(ApplicantDTO applicantDTO) {
        log.debug("Request to save Applicant : {}", applicantDTO);
        Applicant applicant = applicantMapper.toEntity(applicantDTO);
        applicant = applicantRepository.save(applicant);
        return applicantMapper.toDto(applicant);
    }

    @Override
    public ApplicantDTO update(ApplicantDTO applicantDTO) {
        log.debug("Request to update Applicant : {}", applicantDTO);
        Applicant applicant = applicantMapper.toEntity(applicantDTO);
        applicant = applicantRepository.save(applicant);
        return applicantMapper.toDto(applicant);
    }

    @Override
    public Optional<ApplicantDTO> partialUpdate(ApplicantDTO applicantDTO) {
        log.debug("Request to partially update Applicant : {}", applicantDTO);

        return applicantRepository
            .findById(applicantDTO.getId())
            .map(existingApplicant -> {
                applicantMapper.partialUpdate(existingApplicant, applicantDTO);

                return existingApplicant;
            })
            .map(applicantRepository::save)
            .map(applicantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApplicantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Applicants");
        return applicantRepository.findAll(pageable).map(applicantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicantDTO> findOne(Long id) {
        log.debug("Request to get Applicant : {}", id);
        return applicantRepository.findById(id).map(applicantMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Applicant : {}", id);
        applicantRepository.deleteById(id);
    }
}
