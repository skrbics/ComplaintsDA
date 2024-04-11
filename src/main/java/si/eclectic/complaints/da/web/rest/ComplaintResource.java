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
import si.eclectic.complaints.da.repository.ComplaintRepository;
import si.eclectic.complaints.da.service.ComplaintService;
import si.eclectic.complaints.da.service.dto.ComplaintDTO;
import si.eclectic.complaints.da.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link si.eclectic.complaints.da.domain.Complaint}.
 */
@RestController
@RequestMapping("/api/complaints")
public class ComplaintResource {

    private final Logger log = LoggerFactory.getLogger(ComplaintResource.class);

    private static final String ENTITY_NAME = "complaint";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComplaintService complaintService;

    private final ComplaintRepository complaintRepository;

    public ComplaintResource(ComplaintService complaintService, ComplaintRepository complaintRepository) {
        this.complaintService = complaintService;
        this.complaintRepository = complaintRepository;
    }

    /**
     * {@code POST  /complaints} : Create a new complaint.
     *
     * @param complaintDTO the complaintDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new complaintDTO, or with status {@code 400 (Bad Request)} if the complaint has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ComplaintDTO> createComplaint(@RequestBody ComplaintDTO complaintDTO) throws URISyntaxException {
        log.debug("REST request to save Complaint : {}", complaintDTO);
        if (complaintDTO.getId() != null) {
            throw new BadRequestAlertException("A new complaint cannot already have an ID", ENTITY_NAME, "idexists");
        }
        complaintDTO = complaintService.save(complaintDTO);
        return ResponseEntity.created(new URI("/api/complaints/" + complaintDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, complaintDTO.getId().toString()))
            .body(complaintDTO);
    }

    /**
     * {@code PUT  /complaints/:id} : Updates an existing complaint.
     *
     * @param id the id of the complaintDTO to save.
     * @param complaintDTO the complaintDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated complaintDTO,
     * or with status {@code 400 (Bad Request)} if the complaintDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the complaintDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ComplaintDTO> updateComplaint(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ComplaintDTO complaintDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Complaint : {}, {}", id, complaintDTO);
        if (complaintDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, complaintDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!complaintRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        complaintDTO = complaintService.update(complaintDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, complaintDTO.getId().toString()))
            .body(complaintDTO);
    }

    /**
     * {@code PATCH  /complaints/:id} : Partial updates given fields of an existing complaint, field will ignore if it is null
     *
     * @param id the id of the complaintDTO to save.
     * @param complaintDTO the complaintDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated complaintDTO,
     * or with status {@code 400 (Bad Request)} if the complaintDTO is not valid,
     * or with status {@code 404 (Not Found)} if the complaintDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the complaintDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ComplaintDTO> partialUpdateComplaint(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ComplaintDTO complaintDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Complaint partially : {}, {}", id, complaintDTO);
        if (complaintDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, complaintDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!complaintRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ComplaintDTO> result = complaintService.partialUpdate(complaintDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, complaintDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /complaints} : get all the complaints.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of complaints in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ComplaintDTO>> getAllComplaints(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Complaints");
        Page<ComplaintDTO> page = complaintService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /complaints/:id} : get the "id" complaint.
     *
     * @param id the id of the complaintDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the complaintDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ComplaintDTO> getComplaint(@PathVariable("id") Long id) {
        log.debug("REST request to get Complaint : {}", id);
        Optional<ComplaintDTO> complaintDTO = complaintService.findOne(id);
        return ResponseUtil.wrapOrNotFound(complaintDTO);
    }

    /**
     * {@code DELETE  /complaints/:id} : delete the "id" complaint.
     *
     * @param id the id of the complaintDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComplaint(@PathVariable("id") Long id) {
        log.debug("REST request to delete Complaint : {}", id);
        complaintService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
