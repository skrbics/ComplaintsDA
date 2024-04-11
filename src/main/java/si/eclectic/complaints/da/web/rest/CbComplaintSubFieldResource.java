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
import si.eclectic.complaints.da.repository.CbComplaintSubFieldRepository;
import si.eclectic.complaints.da.service.CbComplaintSubFieldService;
import si.eclectic.complaints.da.service.dto.CbComplaintSubFieldDTO;
import si.eclectic.complaints.da.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link si.eclectic.complaints.da.domain.CbComplaintSubField}.
 */
@RestController
@RequestMapping("/api/cb-complaint-sub-fields")
public class CbComplaintSubFieldResource {

    private final Logger log = LoggerFactory.getLogger(CbComplaintSubFieldResource.class);

    private static final String ENTITY_NAME = "cbComplaintSubField";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CbComplaintSubFieldService cbComplaintSubFieldService;

    private final CbComplaintSubFieldRepository cbComplaintSubFieldRepository;

    public CbComplaintSubFieldResource(
        CbComplaintSubFieldService cbComplaintSubFieldService,
        CbComplaintSubFieldRepository cbComplaintSubFieldRepository
    ) {
        this.cbComplaintSubFieldService = cbComplaintSubFieldService;
        this.cbComplaintSubFieldRepository = cbComplaintSubFieldRepository;
    }

    /**
     * {@code POST  /cb-complaint-sub-fields} : Create a new cbComplaintSubField.
     *
     * @param cbComplaintSubFieldDTO the cbComplaintSubFieldDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cbComplaintSubFieldDTO, or with status {@code 400 (Bad Request)} if the cbComplaintSubField has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CbComplaintSubFieldDTO> createCbComplaintSubField(@RequestBody CbComplaintSubFieldDTO cbComplaintSubFieldDTO)
        throws URISyntaxException {
        log.debug("REST request to save CbComplaintSubField : {}", cbComplaintSubFieldDTO);
        if (cbComplaintSubFieldDTO.getId() != null) {
            throw new BadRequestAlertException("A new cbComplaintSubField cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cbComplaintSubFieldDTO = cbComplaintSubFieldService.save(cbComplaintSubFieldDTO);
        return ResponseEntity.created(new URI("/api/cb-complaint-sub-fields/" + cbComplaintSubFieldDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cbComplaintSubFieldDTO.getId().toString()))
            .body(cbComplaintSubFieldDTO);
    }

    /**
     * {@code PUT  /cb-complaint-sub-fields/:id} : Updates an existing cbComplaintSubField.
     *
     * @param id the id of the cbComplaintSubFieldDTO to save.
     * @param cbComplaintSubFieldDTO the cbComplaintSubFieldDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cbComplaintSubFieldDTO,
     * or with status {@code 400 (Bad Request)} if the cbComplaintSubFieldDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cbComplaintSubFieldDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CbComplaintSubFieldDTO> updateCbComplaintSubField(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CbComplaintSubFieldDTO cbComplaintSubFieldDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CbComplaintSubField : {}, {}", id, cbComplaintSubFieldDTO);
        if (cbComplaintSubFieldDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cbComplaintSubFieldDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cbComplaintSubFieldRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cbComplaintSubFieldDTO = cbComplaintSubFieldService.update(cbComplaintSubFieldDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cbComplaintSubFieldDTO.getId().toString()))
            .body(cbComplaintSubFieldDTO);
    }

    /**
     * {@code PATCH  /cb-complaint-sub-fields/:id} : Partial updates given fields of an existing cbComplaintSubField, field will ignore if it is null
     *
     * @param id the id of the cbComplaintSubFieldDTO to save.
     * @param cbComplaintSubFieldDTO the cbComplaintSubFieldDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cbComplaintSubFieldDTO,
     * or with status {@code 400 (Bad Request)} if the cbComplaintSubFieldDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cbComplaintSubFieldDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cbComplaintSubFieldDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CbComplaintSubFieldDTO> partialUpdateCbComplaintSubField(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CbComplaintSubFieldDTO cbComplaintSubFieldDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CbComplaintSubField partially : {}, {}", id, cbComplaintSubFieldDTO);
        if (cbComplaintSubFieldDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cbComplaintSubFieldDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cbComplaintSubFieldRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CbComplaintSubFieldDTO> result = cbComplaintSubFieldService.partialUpdate(cbComplaintSubFieldDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cbComplaintSubFieldDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cb-complaint-sub-fields} : get all the cbComplaintSubFields.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cbComplaintSubFields in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CbComplaintSubFieldDTO>> getAllCbComplaintSubFields(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CbComplaintSubFields");
        Page<CbComplaintSubFieldDTO> page = cbComplaintSubFieldService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cb-complaint-sub-fields/:id} : get the "id" cbComplaintSubField.
     *
     * @param id the id of the cbComplaintSubFieldDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cbComplaintSubFieldDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CbComplaintSubFieldDTO> getCbComplaintSubField(@PathVariable("id") Long id) {
        log.debug("REST request to get CbComplaintSubField : {}", id);
        Optional<CbComplaintSubFieldDTO> cbComplaintSubFieldDTO = cbComplaintSubFieldService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cbComplaintSubFieldDTO);
    }

    /**
     * {@code DELETE  /cb-complaint-sub-fields/:id} : delete the "id" cbComplaintSubField.
     *
     * @param id the id of the cbComplaintSubFieldDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCbComplaintSubField(@PathVariable("id") Long id) {
        log.debug("REST request to delete CbComplaintSubField : {}", id);
        cbComplaintSubFieldService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
