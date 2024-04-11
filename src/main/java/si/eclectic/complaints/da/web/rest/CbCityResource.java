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
import si.eclectic.complaints.da.repository.CbCityRepository;
import si.eclectic.complaints.da.service.CbCityService;
import si.eclectic.complaints.da.service.dto.CbCityDTO;
import si.eclectic.complaints.da.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link si.eclectic.complaints.da.domain.CbCity}.
 */
@RestController
@RequestMapping("/api/cb-cities")
public class CbCityResource {

    private final Logger log = LoggerFactory.getLogger(CbCityResource.class);

    private static final String ENTITY_NAME = "cbCity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CbCityService cbCityService;

    private final CbCityRepository cbCityRepository;

    public CbCityResource(CbCityService cbCityService, CbCityRepository cbCityRepository) {
        this.cbCityService = cbCityService;
        this.cbCityRepository = cbCityRepository;
    }

    /**
     * {@code POST  /cb-cities} : Create a new cbCity.
     *
     * @param cbCityDTO the cbCityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cbCityDTO, or with status {@code 400 (Bad Request)} if the cbCity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CbCityDTO> createCbCity(@RequestBody CbCityDTO cbCityDTO) throws URISyntaxException {
        log.debug("REST request to save CbCity : {}", cbCityDTO);
        if (cbCityDTO.getId() != null) {
            throw new BadRequestAlertException("A new cbCity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cbCityDTO = cbCityService.save(cbCityDTO);
        return ResponseEntity.created(new URI("/api/cb-cities/" + cbCityDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cbCityDTO.getId().toString()))
            .body(cbCityDTO);
    }

    /**
     * {@code PUT  /cb-cities/:id} : Updates an existing cbCity.
     *
     * @param id the id of the cbCityDTO to save.
     * @param cbCityDTO the cbCityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cbCityDTO,
     * or with status {@code 400 (Bad Request)} if the cbCityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cbCityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CbCityDTO> updateCbCity(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CbCityDTO cbCityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CbCity : {}, {}", id, cbCityDTO);
        if (cbCityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cbCityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cbCityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cbCityDTO = cbCityService.update(cbCityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cbCityDTO.getId().toString()))
            .body(cbCityDTO);
    }

    /**
     * {@code PATCH  /cb-cities/:id} : Partial updates given fields of an existing cbCity, field will ignore if it is null
     *
     * @param id the id of the cbCityDTO to save.
     * @param cbCityDTO the cbCityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cbCityDTO,
     * or with status {@code 400 (Bad Request)} if the cbCityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cbCityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cbCityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CbCityDTO> partialUpdateCbCity(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CbCityDTO cbCityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CbCity partially : {}, {}", id, cbCityDTO);
        if (cbCityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cbCityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cbCityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CbCityDTO> result = cbCityService.partialUpdate(cbCityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cbCityDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cb-cities} : get all the cbCities.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cbCities in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CbCityDTO>> getAllCbCities(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CbCities");
        Page<CbCityDTO> page = cbCityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cb-cities/:id} : get the "id" cbCity.
     *
     * @param id the id of the cbCityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cbCityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CbCityDTO> getCbCity(@PathVariable("id") Long id) {
        log.debug("REST request to get CbCity : {}", id);
        Optional<CbCityDTO> cbCityDTO = cbCityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cbCityDTO);
    }

    /**
     * {@code DELETE  /cb-cities/:id} : delete the "id" cbCity.
     *
     * @param id the id of the cbCityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCbCity(@PathVariable("id") Long id) {
        log.debug("REST request to delete CbCity : {}", id);
        cbCityService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
