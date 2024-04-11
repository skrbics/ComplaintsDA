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
import si.eclectic.complaints.da.repository.CbComplaintTypeRepository;
import si.eclectic.complaints.da.service.CbComplaintTypeService;
import si.eclectic.complaints.da.service.dto.CbComplaintTypeDTO;
import si.eclectic.complaints.da.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link si.eclectic.complaints.da.domain.CbComplaintType}.
 */
@RestController
@RequestMapping("/api/cb-complaint-types")
public class CbComplaintTypeResource {

    private final Logger log = LoggerFactory.getLogger(CbComplaintTypeResource.class);

    private static final String ENTITY_NAME = "cbComplaintType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CbComplaintTypeService cbComplaintTypeService;

    private final CbComplaintTypeRepository cbComplaintTypeRepository;

    public CbComplaintTypeResource(CbComplaintTypeService cbComplaintTypeService, CbComplaintTypeRepository cbComplaintTypeRepository) {
        this.cbComplaintTypeService = cbComplaintTypeService;
        this.cbComplaintTypeRepository = cbComplaintTypeRepository;
    }

    /**
     * {@code POST  /cb-complaint-types} : Create a new cbComplaintType.
     *
     * @param cbComplaintTypeDTO the cbComplaintTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cbComplaintTypeDTO, or with status {@code 400 (Bad Request)} if the cbComplaintType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CbComplaintTypeDTO> createCbComplaintType(@RequestBody CbComplaintTypeDTO cbComplaintTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save CbComplaintType : {}", cbComplaintTypeDTO);
        if (cbComplaintTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new cbComplaintType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cbComplaintTypeDTO = cbComplaintTypeService.save(cbComplaintTypeDTO);
        return ResponseEntity.created(new URI("/api/cb-complaint-types/" + cbComplaintTypeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cbComplaintTypeDTO.getId().toString()))
            .body(cbComplaintTypeDTO);
    }

    /**
     * {@code PUT  /cb-complaint-types/:id} : Updates an existing cbComplaintType.
     *
     * @param id the id of the cbComplaintTypeDTO to save.
     * @param cbComplaintTypeDTO the cbComplaintTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cbComplaintTypeDTO,
     * or with status {@code 400 (Bad Request)} if the cbComplaintTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cbComplaintTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CbComplaintTypeDTO> updateCbComplaintType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CbComplaintTypeDTO cbComplaintTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CbComplaintType : {}, {}", id, cbComplaintTypeDTO);
        if (cbComplaintTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cbComplaintTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cbComplaintTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cbComplaintTypeDTO = cbComplaintTypeService.update(cbComplaintTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cbComplaintTypeDTO.getId().toString()))
            .body(cbComplaintTypeDTO);
    }

    /**
     * {@code PATCH  /cb-complaint-types/:id} : Partial updates given fields of an existing cbComplaintType, field will ignore if it is null
     *
     * @param id the id of the cbComplaintTypeDTO to save.
     * @param cbComplaintTypeDTO the cbComplaintTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cbComplaintTypeDTO,
     * or with status {@code 400 (Bad Request)} if the cbComplaintTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cbComplaintTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cbComplaintTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CbComplaintTypeDTO> partialUpdateCbComplaintType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CbComplaintTypeDTO cbComplaintTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CbComplaintType partially : {}, {}", id, cbComplaintTypeDTO);
        if (cbComplaintTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cbComplaintTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cbComplaintTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CbComplaintTypeDTO> result = cbComplaintTypeService.partialUpdate(cbComplaintTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cbComplaintTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cb-complaint-types} : get all the cbComplaintTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cbComplaintTypes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CbComplaintTypeDTO>> getAllCbComplaintTypes(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CbComplaintTypes");
        Page<CbComplaintTypeDTO> page = cbComplaintTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cb-complaint-types/:id} : get the "id" cbComplaintType.
     *
     * @param id the id of the cbComplaintTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cbComplaintTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CbComplaintTypeDTO> getCbComplaintType(@PathVariable("id") Long id) {
        log.debug("REST request to get CbComplaintType : {}", id);
        Optional<CbComplaintTypeDTO> cbComplaintTypeDTO = cbComplaintTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cbComplaintTypeDTO);
    }

    /**
     * {@code DELETE  /cb-complaint-types/:id} : delete the "id" cbComplaintType.
     *
     * @param id the id of the cbComplaintTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCbComplaintType(@PathVariable("id") Long id) {
        log.debug("REST request to delete CbComplaintType : {}", id);
        cbComplaintTypeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
