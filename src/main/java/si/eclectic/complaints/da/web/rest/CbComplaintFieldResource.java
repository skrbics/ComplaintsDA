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
import si.eclectic.complaints.da.repository.CbComplaintFieldRepository;
import si.eclectic.complaints.da.service.CbComplaintFieldService;
import si.eclectic.complaints.da.service.dto.CbComplaintFieldDTO;
import si.eclectic.complaints.da.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link si.eclectic.complaints.da.domain.CbComplaintField}.
 */
@RestController
@RequestMapping("/api/cb-complaint-fields")
public class CbComplaintFieldResource {

    private final Logger log = LoggerFactory.getLogger(CbComplaintFieldResource.class);

    private static final String ENTITY_NAME = "cbComplaintField";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CbComplaintFieldService cbComplaintFieldService;

    private final CbComplaintFieldRepository cbComplaintFieldRepository;

    public CbComplaintFieldResource(
        CbComplaintFieldService cbComplaintFieldService,
        CbComplaintFieldRepository cbComplaintFieldRepository
    ) {
        this.cbComplaintFieldService = cbComplaintFieldService;
        this.cbComplaintFieldRepository = cbComplaintFieldRepository;
    }

    /**
     * {@code POST  /cb-complaint-fields} : Create a new cbComplaintField.
     *
     * @param cbComplaintFieldDTO the cbComplaintFieldDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cbComplaintFieldDTO, or with status {@code 400 (Bad Request)} if the cbComplaintField has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CbComplaintFieldDTO> createCbComplaintField(@RequestBody CbComplaintFieldDTO cbComplaintFieldDTO)
        throws URISyntaxException {
        log.debug("REST request to save CbComplaintField : {}", cbComplaintFieldDTO);
        if (cbComplaintFieldDTO.getId() != null) {
            throw new BadRequestAlertException("A new cbComplaintField cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cbComplaintFieldDTO = cbComplaintFieldService.save(cbComplaintFieldDTO);
        return ResponseEntity.created(new URI("/api/cb-complaint-fields/" + cbComplaintFieldDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cbComplaintFieldDTO.getId().toString()))
            .body(cbComplaintFieldDTO);
    }

    /**
     * {@code PUT  /cb-complaint-fields/:id} : Updates an existing cbComplaintField.
     *
     * @param id the id of the cbComplaintFieldDTO to save.
     * @param cbComplaintFieldDTO the cbComplaintFieldDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cbComplaintFieldDTO,
     * or with status {@code 400 (Bad Request)} if the cbComplaintFieldDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cbComplaintFieldDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CbComplaintFieldDTO> updateCbComplaintField(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CbComplaintFieldDTO cbComplaintFieldDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CbComplaintField : {}, {}", id, cbComplaintFieldDTO);
        if (cbComplaintFieldDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cbComplaintFieldDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cbComplaintFieldRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cbComplaintFieldDTO = cbComplaintFieldService.update(cbComplaintFieldDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cbComplaintFieldDTO.getId().toString()))
            .body(cbComplaintFieldDTO);
    }

    /**
     * {@code PATCH  /cb-complaint-fields/:id} : Partial updates given fields of an existing cbComplaintField, field will ignore if it is null
     *
     * @param id the id of the cbComplaintFieldDTO to save.
     * @param cbComplaintFieldDTO the cbComplaintFieldDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cbComplaintFieldDTO,
     * or with status {@code 400 (Bad Request)} if the cbComplaintFieldDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cbComplaintFieldDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cbComplaintFieldDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CbComplaintFieldDTO> partialUpdateCbComplaintField(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CbComplaintFieldDTO cbComplaintFieldDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CbComplaintField partially : {}, {}", id, cbComplaintFieldDTO);
        if (cbComplaintFieldDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cbComplaintFieldDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cbComplaintFieldRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CbComplaintFieldDTO> result = cbComplaintFieldService.partialUpdate(cbComplaintFieldDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cbComplaintFieldDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cb-complaint-fields} : get all the cbComplaintFields.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cbComplaintFields in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CbComplaintFieldDTO>> getAllCbComplaintFields(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CbComplaintFields");
        Page<CbComplaintFieldDTO> page = cbComplaintFieldService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cb-complaint-fields/:id} : get the "id" cbComplaintField.
     *
     * @param id the id of the cbComplaintFieldDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cbComplaintFieldDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CbComplaintFieldDTO> getCbComplaintField(@PathVariable("id") Long id) {
        log.debug("REST request to get CbComplaintField : {}", id);
        Optional<CbComplaintFieldDTO> cbComplaintFieldDTO = cbComplaintFieldService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cbComplaintFieldDTO);
    }

    /**
     * {@code DELETE  /cb-complaint-fields/:id} : delete the "id" cbComplaintField.
     *
     * @param id the id of the cbComplaintFieldDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCbComplaintField(@PathVariable("id") Long id) {
        log.debug("REST request to delete CbComplaintField : {}", id);
        cbComplaintFieldService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
