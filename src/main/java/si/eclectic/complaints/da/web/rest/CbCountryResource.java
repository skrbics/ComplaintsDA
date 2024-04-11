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
import si.eclectic.complaints.da.repository.CbCountryRepository;
import si.eclectic.complaints.da.service.CbCountryService;
import si.eclectic.complaints.da.service.dto.CbCountryDTO;
import si.eclectic.complaints.da.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link si.eclectic.complaints.da.domain.CbCountry}.
 */
@RestController
@RequestMapping("/api/cb-countries")
public class CbCountryResource {

    private final Logger log = LoggerFactory.getLogger(CbCountryResource.class);

    private static final String ENTITY_NAME = "cbCountry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CbCountryService cbCountryService;

    private final CbCountryRepository cbCountryRepository;

    public CbCountryResource(CbCountryService cbCountryService, CbCountryRepository cbCountryRepository) {
        this.cbCountryService = cbCountryService;
        this.cbCountryRepository = cbCountryRepository;
    }

    /**
     * {@code POST  /cb-countries} : Create a new cbCountry.
     *
     * @param cbCountryDTO the cbCountryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cbCountryDTO, or with status {@code 400 (Bad Request)} if the cbCountry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CbCountryDTO> createCbCountry(@RequestBody CbCountryDTO cbCountryDTO) throws URISyntaxException {
        log.debug("REST request to save CbCountry : {}", cbCountryDTO);
        if (cbCountryDTO.getId() != null) {
            throw new BadRequestAlertException("A new cbCountry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cbCountryDTO = cbCountryService.save(cbCountryDTO);
        return ResponseEntity.created(new URI("/api/cb-countries/" + cbCountryDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cbCountryDTO.getId().toString()))
            .body(cbCountryDTO);
    }

    /**
     * {@code PUT  /cb-countries/:id} : Updates an existing cbCountry.
     *
     * @param id the id of the cbCountryDTO to save.
     * @param cbCountryDTO the cbCountryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cbCountryDTO,
     * or with status {@code 400 (Bad Request)} if the cbCountryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cbCountryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CbCountryDTO> updateCbCountry(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CbCountryDTO cbCountryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CbCountry : {}, {}", id, cbCountryDTO);
        if (cbCountryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cbCountryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cbCountryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cbCountryDTO = cbCountryService.update(cbCountryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cbCountryDTO.getId().toString()))
            .body(cbCountryDTO);
    }

    /**
     * {@code PATCH  /cb-countries/:id} : Partial updates given fields of an existing cbCountry, field will ignore if it is null
     *
     * @param id the id of the cbCountryDTO to save.
     * @param cbCountryDTO the cbCountryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cbCountryDTO,
     * or with status {@code 400 (Bad Request)} if the cbCountryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cbCountryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cbCountryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CbCountryDTO> partialUpdateCbCountry(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CbCountryDTO cbCountryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CbCountry partially : {}, {}", id, cbCountryDTO);
        if (cbCountryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cbCountryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cbCountryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CbCountryDTO> result = cbCountryService.partialUpdate(cbCountryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cbCountryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cb-countries} : get all the cbCountries.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cbCountries in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CbCountryDTO>> getAllCbCountries(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CbCountries");
        Page<CbCountryDTO> page = cbCountryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cb-countries/:id} : get the "id" cbCountry.
     *
     * @param id the id of the cbCountryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cbCountryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CbCountryDTO> getCbCountry(@PathVariable("id") Long id) {
        log.debug("REST request to get CbCountry : {}", id);
        Optional<CbCountryDTO> cbCountryDTO = cbCountryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cbCountryDTO);
    }

    /**
     * {@code DELETE  /cb-countries/:id} : delete the "id" cbCountry.
     *
     * @param id the id of the cbCountryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCbCountry(@PathVariable("id") Long id) {
        log.debug("REST request to delete CbCountry : {}", id);
        cbCountryService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
