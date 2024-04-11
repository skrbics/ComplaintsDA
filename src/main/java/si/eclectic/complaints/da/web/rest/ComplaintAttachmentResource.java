package si.eclectic.complaints.da.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import si.eclectic.complaints.da.repository.ComplaintAttachmentRepository;
import si.eclectic.complaints.da.service.ComplaintAttachmentService;
import si.eclectic.complaints.da.service.dto.ComplaintAttachmentDTO;
import si.eclectic.complaints.da.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link si.eclectic.complaints.da.domain.ComplaintAttachment}.
 */
@RestController
@RequestMapping("/api/complaint-attachments")
public class ComplaintAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(ComplaintAttachmentResource.class);

    private static final String ENTITY_NAME = "complaintAttachment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComplaintAttachmentService complaintAttachmentService;

    private final ComplaintAttachmentRepository complaintAttachmentRepository;

    public ComplaintAttachmentResource(
        ComplaintAttachmentService complaintAttachmentService,
        ComplaintAttachmentRepository complaintAttachmentRepository
    ) {
        this.complaintAttachmentService = complaintAttachmentService;
        this.complaintAttachmentRepository = complaintAttachmentRepository;
    }

    /**
     * {@code POST  /complaint-attachments} : Create a new complaintAttachment.
     *
     * @param complaintAttachmentDTO the complaintAttachmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new complaintAttachmentDTO, or with status {@code 400 (Bad Request)} if the complaintAttachment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ComplaintAttachmentDTO> createComplaintAttachment(@RequestBody ComplaintAttachmentDTO complaintAttachmentDTO)
        throws URISyntaxException {
        log.debug("REST request to save ComplaintAttachment : {}", complaintAttachmentDTO);
        if (complaintAttachmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new complaintAttachment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        complaintAttachmentDTO = complaintAttachmentService.save(complaintAttachmentDTO);
        return ResponseEntity.created(new URI("/api/complaint-attachments/" + complaintAttachmentDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, complaintAttachmentDTO.getId().toString()))
            .body(complaintAttachmentDTO);
    }

    /**
     * {@code PUT  /complaint-attachments/:id} : Updates an existing complaintAttachment.
     *
     * @param id the id of the complaintAttachmentDTO to save.
     * @param complaintAttachmentDTO the complaintAttachmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated complaintAttachmentDTO,
     * or with status {@code 400 (Bad Request)} if the complaintAttachmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the complaintAttachmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ComplaintAttachmentDTO> updateComplaintAttachment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ComplaintAttachmentDTO complaintAttachmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ComplaintAttachment : {}, {}", id, complaintAttachmentDTO);
        if (complaintAttachmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, complaintAttachmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!complaintAttachmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        complaintAttachmentDTO = complaintAttachmentService.update(complaintAttachmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, complaintAttachmentDTO.getId().toString()))
            .body(complaintAttachmentDTO);
    }

    /**
     * {@code PATCH  /complaint-attachments/:id} : Partial updates given fields of an existing complaintAttachment, field will ignore if it is null
     *
     * @param id the id of the complaintAttachmentDTO to save.
     * @param complaintAttachmentDTO the complaintAttachmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated complaintAttachmentDTO,
     * or with status {@code 400 (Bad Request)} if the complaintAttachmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the complaintAttachmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the complaintAttachmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ComplaintAttachmentDTO> partialUpdateComplaintAttachment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ComplaintAttachmentDTO complaintAttachmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ComplaintAttachment partially : {}, {}", id, complaintAttachmentDTO);
        if (complaintAttachmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, complaintAttachmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!complaintAttachmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ComplaintAttachmentDTO> result = complaintAttachmentService.partialUpdate(complaintAttachmentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, complaintAttachmentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /complaint-attachments} : get all the complaintAttachments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of complaintAttachments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ComplaintAttachmentDTO>> getAllComplaintAttachments(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ComplaintAttachments");
        Page<ComplaintAttachmentDTO> page = complaintAttachmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /complaint-attachments/:id} : get the "id" complaintAttachment.
     *
     * @param id the id of the complaintAttachmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the complaintAttachmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ComplaintAttachmentDTO> getComplaintAttachment(@PathVariable("id") Long id) {
        log.debug("REST request to get ComplaintAttachment : {}", id);
        Optional<ComplaintAttachmentDTO> complaintAttachmentDTO = complaintAttachmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(complaintAttachmentDTO);
    }

    /**
     * {@code DELETE  /complaint-attachments/:id} : delete the "id" complaintAttachment.
     *
     * @param id the id of the complaintAttachmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComplaintAttachment(@PathVariable("id") Long id) {
        log.debug("REST request to delete ComplaintAttachment : {}", id);
        complaintAttachmentService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
