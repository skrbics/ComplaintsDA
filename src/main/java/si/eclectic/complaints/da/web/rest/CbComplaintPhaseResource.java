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
import si.eclectic.complaints.da.repository.CbComplaintPhaseRepository;
import si.eclectic.complaints.da.service.CbComplaintPhaseService;
import si.eclectic.complaints.da.service.dto.CbComplaintPhaseDTO;
import si.eclectic.complaints.da.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link si.eclectic.complaints.da.domain.CbComplaintPhase}.
 */
@RestController
@RequestMapping("/api/cb-complaint-phases")
public class CbComplaintPhaseResource {

    private final Logger log = LoggerFactory.getLogger(CbComplaintPhaseResource.class);

    private static final String ENTITY_NAME = "cbComplaintPhase";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CbComplaintPhaseService cbComplaintPhaseService;

    private final CbComplaintPhaseRepository cbComplaintPhaseRepository;

    public CbComplaintPhaseResource(
        CbComplaintPhaseService cbComplaintPhaseService,
        CbComplaintPhaseRepository cbComplaintPhaseRepository
    ) {
        this.cbComplaintPhaseService = cbComplaintPhaseService;
        this.cbComplaintPhaseRepository = cbComplaintPhaseRepository;
    }

    /**
     * {@code POST  /cb-complaint-phases} : Create a new cbComplaintPhase.
     *
     * @param cbComplaintPhaseDTO the cbComplaintPhaseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cbComplaintPhaseDTO, or with status {@code 400 (Bad Request)} if the cbComplaintPhase has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CbComplaintPhaseDTO> createCbComplaintPhase(@RequestBody CbComplaintPhaseDTO cbComplaintPhaseDTO)
        throws URISyntaxException {
        log.debug("REST request to save CbComplaintPhase : {}", cbComplaintPhaseDTO);
        if (cbComplaintPhaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new cbComplaintPhase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cbComplaintPhaseDTO = cbComplaintPhaseService.save(cbComplaintPhaseDTO);
        return ResponseEntity.created(new URI("/api/cb-complaint-phases/" + cbComplaintPhaseDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cbComplaintPhaseDTO.getId().toString()))
            .body(cbComplaintPhaseDTO);
    }

    /**
     * {@code PUT  /cb-complaint-phases/:id} : Updates an existing cbComplaintPhase.
     *
     * @param id the id of the cbComplaintPhaseDTO to save.
     * @param cbComplaintPhaseDTO the cbComplaintPhaseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cbComplaintPhaseDTO,
     * or with status {@code 400 (Bad Request)} if the cbComplaintPhaseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cbComplaintPhaseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CbComplaintPhaseDTO> updateCbComplaintPhase(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CbComplaintPhaseDTO cbComplaintPhaseDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CbComplaintPhase : {}, {}", id, cbComplaintPhaseDTO);
        if (cbComplaintPhaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cbComplaintPhaseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cbComplaintPhaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cbComplaintPhaseDTO = cbComplaintPhaseService.update(cbComplaintPhaseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cbComplaintPhaseDTO.getId().toString()))
            .body(cbComplaintPhaseDTO);
    }

    /**
     * {@code PATCH  /cb-complaint-phases/:id} : Partial updates given fields of an existing cbComplaintPhase, field will ignore if it is null
     *
     * @param id the id of the cbComplaintPhaseDTO to save.
     * @param cbComplaintPhaseDTO the cbComplaintPhaseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cbComplaintPhaseDTO,
     * or with status {@code 400 (Bad Request)} if the cbComplaintPhaseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cbComplaintPhaseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cbComplaintPhaseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CbComplaintPhaseDTO> partialUpdateCbComplaintPhase(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CbComplaintPhaseDTO cbComplaintPhaseDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CbComplaintPhase partially : {}, {}", id, cbComplaintPhaseDTO);
        if (cbComplaintPhaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cbComplaintPhaseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cbComplaintPhaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CbComplaintPhaseDTO> result = cbComplaintPhaseService.partialUpdate(cbComplaintPhaseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cbComplaintPhaseDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cb-complaint-phases} : get all the cbComplaintPhases.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cbComplaintPhases in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CbComplaintPhaseDTO>> getAllCbComplaintPhases(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CbComplaintPhases");
        Page<CbComplaintPhaseDTO> page = cbComplaintPhaseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cb-complaint-phases/:id} : get the "id" cbComplaintPhase.
     *
     * @param id the id of the cbComplaintPhaseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cbComplaintPhaseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CbComplaintPhaseDTO> getCbComplaintPhase(@PathVariable("id") Long id) {
        log.debug("REST request to get CbComplaintPhase : {}", id);
        Optional<CbComplaintPhaseDTO> cbComplaintPhaseDTO = cbComplaintPhaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cbComplaintPhaseDTO);
    }

    /**
     * {@code DELETE  /cb-complaint-phases/:id} : delete the "id" cbComplaintPhase.
     *
     * @param id the id of the cbComplaintPhaseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCbComplaintPhase(@PathVariable("id") Long id) {
        log.debug("REST request to delete CbComplaintPhase : {}", id);
        cbComplaintPhaseService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
