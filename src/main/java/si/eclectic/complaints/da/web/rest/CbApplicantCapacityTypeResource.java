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
import si.eclectic.complaints.da.repository.CbApplicantCapacityTypeRepository;
import si.eclectic.complaints.da.service.CbApplicantCapacityTypeService;
import si.eclectic.complaints.da.service.dto.CbApplicantCapacityTypeDTO;
import si.eclectic.complaints.da.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link si.eclectic.complaints.da.domain.CbApplicantCapacityType}.
 */
@RestController
@RequestMapping("/api/cb-applicant-capacity-types")
public class CbApplicantCapacityTypeResource {

    private final Logger log = LoggerFactory.getLogger(CbApplicantCapacityTypeResource.class);

    private static final String ENTITY_NAME = "cbApplicantCapacityType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CbApplicantCapacityTypeService cbApplicantCapacityTypeService;

    private final CbApplicantCapacityTypeRepository cbApplicantCapacityTypeRepository;

    public CbApplicantCapacityTypeResource(
        CbApplicantCapacityTypeService cbApplicantCapacityTypeService,
        CbApplicantCapacityTypeRepository cbApplicantCapacityTypeRepository
    ) {
        this.cbApplicantCapacityTypeService = cbApplicantCapacityTypeService;
        this.cbApplicantCapacityTypeRepository = cbApplicantCapacityTypeRepository;
    }

    /**
     * {@code POST  /cb-applicant-capacity-types} : Create a new cbApplicantCapacityType.
     *
     * @param cbApplicantCapacityTypeDTO the cbApplicantCapacityTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cbApplicantCapacityTypeDTO, or with status {@code 400 (Bad Request)} if the cbApplicantCapacityType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CbApplicantCapacityTypeDTO> createCbApplicantCapacityType(
        @RequestBody CbApplicantCapacityTypeDTO cbApplicantCapacityTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CbApplicantCapacityType : {}", cbApplicantCapacityTypeDTO);
        if (cbApplicantCapacityTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new cbApplicantCapacityType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cbApplicantCapacityTypeDTO = cbApplicantCapacityTypeService.save(cbApplicantCapacityTypeDTO);
        return ResponseEntity.created(new URI("/api/cb-applicant-capacity-types/" + cbApplicantCapacityTypeDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cbApplicantCapacityTypeDTO.getId().toString())
            )
            .body(cbApplicantCapacityTypeDTO);
    }

    /**
     * {@code PUT  /cb-applicant-capacity-types/:id} : Updates an existing cbApplicantCapacityType.
     *
     * @param id the id of the cbApplicantCapacityTypeDTO to save.
     * @param cbApplicantCapacityTypeDTO the cbApplicantCapacityTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cbApplicantCapacityTypeDTO,
     * or with status {@code 400 (Bad Request)} if the cbApplicantCapacityTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cbApplicantCapacityTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CbApplicantCapacityTypeDTO> updateCbApplicantCapacityType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CbApplicantCapacityTypeDTO cbApplicantCapacityTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CbApplicantCapacityType : {}, {}", id, cbApplicantCapacityTypeDTO);
        if (cbApplicantCapacityTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cbApplicantCapacityTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cbApplicantCapacityTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cbApplicantCapacityTypeDTO = cbApplicantCapacityTypeService.update(cbApplicantCapacityTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cbApplicantCapacityTypeDTO.getId().toString()))
            .body(cbApplicantCapacityTypeDTO);
    }

    /**
     * {@code PATCH  /cb-applicant-capacity-types/:id} : Partial updates given fields of an existing cbApplicantCapacityType, field will ignore if it is null
     *
     * @param id the id of the cbApplicantCapacityTypeDTO to save.
     * @param cbApplicantCapacityTypeDTO the cbApplicantCapacityTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cbApplicantCapacityTypeDTO,
     * or with status {@code 400 (Bad Request)} if the cbApplicantCapacityTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cbApplicantCapacityTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cbApplicantCapacityTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CbApplicantCapacityTypeDTO> partialUpdateCbApplicantCapacityType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CbApplicantCapacityTypeDTO cbApplicantCapacityTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CbApplicantCapacityType partially : {}, {}", id, cbApplicantCapacityTypeDTO);
        if (cbApplicantCapacityTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cbApplicantCapacityTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cbApplicantCapacityTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CbApplicantCapacityTypeDTO> result = cbApplicantCapacityTypeService.partialUpdate(cbApplicantCapacityTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cbApplicantCapacityTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cb-applicant-capacity-types} : get all the cbApplicantCapacityTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cbApplicantCapacityTypes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CbApplicantCapacityTypeDTO>> getAllCbApplicantCapacityTypes(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CbApplicantCapacityTypes");
        Page<CbApplicantCapacityTypeDTO> page = cbApplicantCapacityTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cb-applicant-capacity-types/:id} : get the "id" cbApplicantCapacityType.
     *
     * @param id the id of the cbApplicantCapacityTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cbApplicantCapacityTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CbApplicantCapacityTypeDTO> getCbApplicantCapacityType(@PathVariable("id") Long id) {
        log.debug("REST request to get CbApplicantCapacityType : {}", id);
        Optional<CbApplicantCapacityTypeDTO> cbApplicantCapacityTypeDTO = cbApplicantCapacityTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cbApplicantCapacityTypeDTO);
    }

    /**
     * {@code DELETE  /cb-applicant-capacity-types/:id} : delete the "id" cbApplicantCapacityType.
     *
     * @param id the id of the cbApplicantCapacityTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCbApplicantCapacityType(@PathVariable("id") Long id) {
        log.debug("REST request to delete CbApplicantCapacityType : {}", id);
        cbApplicantCapacityTypeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
