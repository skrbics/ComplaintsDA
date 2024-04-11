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
import si.eclectic.complaints.da.repository.CbLocationRepository;
import si.eclectic.complaints.da.service.CbLocationService;
import si.eclectic.complaints.da.service.dto.CbLocationDTO;
import si.eclectic.complaints.da.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link si.eclectic.complaints.da.domain.CbLocation}.
 */
@RestController
@RequestMapping("/api/cb-locations")
public class CbLocationResource {

    private final Logger log = LoggerFactory.getLogger(CbLocationResource.class);

    private static final String ENTITY_NAME = "cbLocation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CbLocationService cbLocationService;

    private final CbLocationRepository cbLocationRepository;

    public CbLocationResource(CbLocationService cbLocationService, CbLocationRepository cbLocationRepository) {
        this.cbLocationService = cbLocationService;
        this.cbLocationRepository = cbLocationRepository;
    }

    /**
     * {@code POST  /cb-locations} : Create a new cbLocation.
     *
     * @param cbLocationDTO the cbLocationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cbLocationDTO, or with status {@code 400 (Bad Request)} if the cbLocation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CbLocationDTO> createCbLocation(@RequestBody CbLocationDTO cbLocationDTO) throws URISyntaxException {
        log.debug("REST request to save CbLocation : {}", cbLocationDTO);
        if (cbLocationDTO.getId() != null) {
            throw new BadRequestAlertException("A new cbLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cbLocationDTO = cbLocationService.save(cbLocationDTO);
        return ResponseEntity.created(new URI("/api/cb-locations/" + cbLocationDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cbLocationDTO.getId().toString()))
            .body(cbLocationDTO);
    }

    /**
     * {@code PUT  /cb-locations/:id} : Updates an existing cbLocation.
     *
     * @param id the id of the cbLocationDTO to save.
     * @param cbLocationDTO the cbLocationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cbLocationDTO,
     * or with status {@code 400 (Bad Request)} if the cbLocationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cbLocationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CbLocationDTO> updateCbLocation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CbLocationDTO cbLocationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CbLocation : {}, {}", id, cbLocationDTO);
        if (cbLocationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cbLocationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cbLocationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cbLocationDTO = cbLocationService.update(cbLocationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cbLocationDTO.getId().toString()))
            .body(cbLocationDTO);
    }

    /**
     * {@code PATCH  /cb-locations/:id} : Partial updates given fields of an existing cbLocation, field will ignore if it is null
     *
     * @param id the id of the cbLocationDTO to save.
     * @param cbLocationDTO the cbLocationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cbLocationDTO,
     * or with status {@code 400 (Bad Request)} if the cbLocationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cbLocationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cbLocationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CbLocationDTO> partialUpdateCbLocation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CbLocationDTO cbLocationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CbLocation partially : {}, {}", id, cbLocationDTO);
        if (cbLocationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cbLocationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cbLocationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CbLocationDTO> result = cbLocationService.partialUpdate(cbLocationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cbLocationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cb-locations} : get all the cbLocations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cbLocations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CbLocationDTO>> getAllCbLocations(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CbLocations");
        Page<CbLocationDTO> page = cbLocationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cb-locations/:id} : get the "id" cbLocation.
     *
     * @param id the id of the cbLocationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cbLocationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CbLocationDTO> getCbLocation(@PathVariable("id") Long id) {
        log.debug("REST request to get CbLocation : {}", id);
        Optional<CbLocationDTO> cbLocationDTO = cbLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cbLocationDTO);
    }

    /**
     * {@code DELETE  /cb-locations/:id} : delete the "id" cbLocation.
     *
     * @param id the id of the cbLocationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCbLocation(@PathVariable("id") Long id) {
        log.debug("REST request to delete CbLocation : {}", id);
        cbLocationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
