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
import si.eclectic.complaints.da.repository.CbComplaintChannelRepository;
import si.eclectic.complaints.da.service.CbComplaintChannelService;
import si.eclectic.complaints.da.service.dto.CbComplaintChannelDTO;
import si.eclectic.complaints.da.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link si.eclectic.complaints.da.domain.CbComplaintChannel}.
 */
@RestController
@RequestMapping("/api/cb-complaint-channels")
public class CbComplaintChannelResource {

    private final Logger log = LoggerFactory.getLogger(CbComplaintChannelResource.class);

    private static final String ENTITY_NAME = "cbComplaintChannel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CbComplaintChannelService cbComplaintChannelService;

    private final CbComplaintChannelRepository cbComplaintChannelRepository;

    public CbComplaintChannelResource(
        CbComplaintChannelService cbComplaintChannelService,
        CbComplaintChannelRepository cbComplaintChannelRepository
    ) {
        this.cbComplaintChannelService = cbComplaintChannelService;
        this.cbComplaintChannelRepository = cbComplaintChannelRepository;
    }

    /**
     * {@code POST  /cb-complaint-channels} : Create a new cbComplaintChannel.
     *
     * @param cbComplaintChannelDTO the cbComplaintChannelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cbComplaintChannelDTO, or with status {@code 400 (Bad Request)} if the cbComplaintChannel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CbComplaintChannelDTO> createCbComplaintChannel(@RequestBody CbComplaintChannelDTO cbComplaintChannelDTO)
        throws URISyntaxException {
        log.debug("REST request to save CbComplaintChannel : {}", cbComplaintChannelDTO);
        if (cbComplaintChannelDTO.getId() != null) {
            throw new BadRequestAlertException("A new cbComplaintChannel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cbComplaintChannelDTO = cbComplaintChannelService.save(cbComplaintChannelDTO);
        return ResponseEntity.created(new URI("/api/cb-complaint-channels/" + cbComplaintChannelDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cbComplaintChannelDTO.getId().toString()))
            .body(cbComplaintChannelDTO);
    }

    /**
     * {@code PUT  /cb-complaint-channels/:id} : Updates an existing cbComplaintChannel.
     *
     * @param id the id of the cbComplaintChannelDTO to save.
     * @param cbComplaintChannelDTO the cbComplaintChannelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cbComplaintChannelDTO,
     * or with status {@code 400 (Bad Request)} if the cbComplaintChannelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cbComplaintChannelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CbComplaintChannelDTO> updateCbComplaintChannel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CbComplaintChannelDTO cbComplaintChannelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CbComplaintChannel : {}, {}", id, cbComplaintChannelDTO);
        if (cbComplaintChannelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cbComplaintChannelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cbComplaintChannelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cbComplaintChannelDTO = cbComplaintChannelService.update(cbComplaintChannelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cbComplaintChannelDTO.getId().toString()))
            .body(cbComplaintChannelDTO);
    }

    /**
     * {@code PATCH  /cb-complaint-channels/:id} : Partial updates given fields of an existing cbComplaintChannel, field will ignore if it is null
     *
     * @param id the id of the cbComplaintChannelDTO to save.
     * @param cbComplaintChannelDTO the cbComplaintChannelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cbComplaintChannelDTO,
     * or with status {@code 400 (Bad Request)} if the cbComplaintChannelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cbComplaintChannelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cbComplaintChannelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CbComplaintChannelDTO> partialUpdateCbComplaintChannel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CbComplaintChannelDTO cbComplaintChannelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CbComplaintChannel partially : {}, {}", id, cbComplaintChannelDTO);
        if (cbComplaintChannelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cbComplaintChannelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cbComplaintChannelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CbComplaintChannelDTO> result = cbComplaintChannelService.partialUpdate(cbComplaintChannelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cbComplaintChannelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cb-complaint-channels} : get all the cbComplaintChannels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cbComplaintChannels in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CbComplaintChannelDTO>> getAllCbComplaintChannels(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CbComplaintChannels");
        Page<CbComplaintChannelDTO> page = cbComplaintChannelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cb-complaint-channels/:id} : get the "id" cbComplaintChannel.
     *
     * @param id the id of the cbComplaintChannelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cbComplaintChannelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CbComplaintChannelDTO> getCbComplaintChannel(@PathVariable("id") Long id) {
        log.debug("REST request to get CbComplaintChannel : {}", id);
        Optional<CbComplaintChannelDTO> cbComplaintChannelDTO = cbComplaintChannelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cbComplaintChannelDTO);
    }

    /**
     * {@code DELETE  /cb-complaint-channels/:id} : delete the "id" cbComplaintChannel.
     *
     * @param id the id of the cbComplaintChannelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCbComplaintChannel(@PathVariable("id") Long id) {
        log.debug("REST request to delete CbComplaintChannel : {}", id);
        cbComplaintChannelService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
