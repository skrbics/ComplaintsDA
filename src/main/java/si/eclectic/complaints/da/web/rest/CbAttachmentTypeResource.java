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
import si.eclectic.complaints.da.repository.CbAttachmentTypeRepository;
import si.eclectic.complaints.da.service.CbAttachmentTypeService;
import si.eclectic.complaints.da.service.dto.CbAttachmentTypeDTO;
import si.eclectic.complaints.da.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link si.eclectic.complaints.da.domain.CbAttachmentType}.
 */
@RestController
@RequestMapping("/api/cb-attachment-types")
public class CbAttachmentTypeResource {

    private final Logger log = LoggerFactory.getLogger(CbAttachmentTypeResource.class);

    private static final String ENTITY_NAME = "cbAttachmentType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CbAttachmentTypeService cbAttachmentTypeService;

    private final CbAttachmentTypeRepository cbAttachmentTypeRepository;

    public CbAttachmentTypeResource(
        CbAttachmentTypeService cbAttachmentTypeService,
        CbAttachmentTypeRepository cbAttachmentTypeRepository
    ) {
        this.cbAttachmentTypeService = cbAttachmentTypeService;
        this.cbAttachmentTypeRepository = cbAttachmentTypeRepository;
    }

    /**
     * {@code POST  /cb-attachment-types} : Create a new cbAttachmentType.
     *
     * @param cbAttachmentTypeDTO the cbAttachmentTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cbAttachmentTypeDTO, or with status {@code 400 (Bad Request)} if the cbAttachmentType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CbAttachmentTypeDTO> createCbAttachmentType(@RequestBody CbAttachmentTypeDTO cbAttachmentTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save CbAttachmentType : {}", cbAttachmentTypeDTO);
        if (cbAttachmentTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new cbAttachmentType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cbAttachmentTypeDTO = cbAttachmentTypeService.save(cbAttachmentTypeDTO);
        return ResponseEntity.created(new URI("/api/cb-attachment-types/" + cbAttachmentTypeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cbAttachmentTypeDTO.getId().toString()))
            .body(cbAttachmentTypeDTO);
    }

    /**
     * {@code PUT  /cb-attachment-types/:id} : Updates an existing cbAttachmentType.
     *
     * @param id the id of the cbAttachmentTypeDTO to save.
     * @param cbAttachmentTypeDTO the cbAttachmentTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cbAttachmentTypeDTO,
     * or with status {@code 400 (Bad Request)} if the cbAttachmentTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cbAttachmentTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CbAttachmentTypeDTO> updateCbAttachmentType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CbAttachmentTypeDTO cbAttachmentTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CbAttachmentType : {}, {}", id, cbAttachmentTypeDTO);
        if (cbAttachmentTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cbAttachmentTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cbAttachmentTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cbAttachmentTypeDTO = cbAttachmentTypeService.update(cbAttachmentTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cbAttachmentTypeDTO.getId().toString()))
            .body(cbAttachmentTypeDTO);
    }

    /**
     * {@code PATCH  /cb-attachment-types/:id} : Partial updates given fields of an existing cbAttachmentType, field will ignore if it is null
     *
     * @param id the id of the cbAttachmentTypeDTO to save.
     * @param cbAttachmentTypeDTO the cbAttachmentTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cbAttachmentTypeDTO,
     * or with status {@code 400 (Bad Request)} if the cbAttachmentTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cbAttachmentTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cbAttachmentTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CbAttachmentTypeDTO> partialUpdateCbAttachmentType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CbAttachmentTypeDTO cbAttachmentTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CbAttachmentType partially : {}, {}", id, cbAttachmentTypeDTO);
        if (cbAttachmentTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cbAttachmentTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cbAttachmentTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CbAttachmentTypeDTO> result = cbAttachmentTypeService.partialUpdate(cbAttachmentTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cbAttachmentTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cb-attachment-types} : get all the cbAttachmentTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cbAttachmentTypes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CbAttachmentTypeDTO>> getAllCbAttachmentTypes(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CbAttachmentTypes");
        Page<CbAttachmentTypeDTO> page = cbAttachmentTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cb-attachment-types/:id} : get the "id" cbAttachmentType.
     *
     * @param id the id of the cbAttachmentTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cbAttachmentTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CbAttachmentTypeDTO> getCbAttachmentType(@PathVariable("id") Long id) {
        log.debug("REST request to get CbAttachmentType : {}", id);
        Optional<CbAttachmentTypeDTO> cbAttachmentTypeDTO = cbAttachmentTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cbAttachmentTypeDTO);
    }

    /**
     * {@code DELETE  /cb-attachment-types/:id} : delete the "id" cbAttachmentType.
     *
     * @param id the id of the cbAttachmentTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCbAttachmentType(@PathVariable("id") Long id) {
        log.debug("REST request to delete CbAttachmentType : {}", id);
        cbAttachmentTypeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
