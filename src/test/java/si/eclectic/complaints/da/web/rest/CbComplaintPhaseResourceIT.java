package si.eclectic.complaints.da.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static si.eclectic.complaints.da.domain.CbComplaintPhaseAsserts.*;
import static si.eclectic.complaints.da.web.rest.TestUtil.createUpdateProxyForBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.complaints.da.IntegrationTest;
import si.eclectic.complaints.da.domain.CbComplaintPhase;
import si.eclectic.complaints.da.repository.CbComplaintPhaseRepository;
import si.eclectic.complaints.da.service.dto.CbComplaintPhaseDTO;
import si.eclectic.complaints.da.service.mapper.CbComplaintPhaseMapper;

/**
 * Integration tests for the {@link CbComplaintPhaseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CbComplaintPhaseResourceIT {

    private static final Integer DEFAULT_ORDINAL_NO = 1;
    private static final Integer UPDATED_ORDINAL_NO = 2;

    private static final String DEFAULT_COMPLAINT_PHASE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPLAINT_PHASE_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cb-complaint-phases";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CbComplaintPhaseRepository cbComplaintPhaseRepository;

    @Autowired
    private CbComplaintPhaseMapper cbComplaintPhaseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCbComplaintPhaseMockMvc;

    private CbComplaintPhase cbComplaintPhase;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CbComplaintPhase createEntity(EntityManager em) {
        CbComplaintPhase cbComplaintPhase = new CbComplaintPhase()
            .ordinalNo(DEFAULT_ORDINAL_NO)
            .complaintPhaseName(DEFAULT_COMPLAINT_PHASE_NAME);
        return cbComplaintPhase;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CbComplaintPhase createUpdatedEntity(EntityManager em) {
        CbComplaintPhase cbComplaintPhase = new CbComplaintPhase()
            .ordinalNo(UPDATED_ORDINAL_NO)
            .complaintPhaseName(UPDATED_COMPLAINT_PHASE_NAME);
        return cbComplaintPhase;
    }

    @BeforeEach
    public void initTest() {
        cbComplaintPhase = createEntity(em);
    }

    @Test
    @Transactional
    void createCbComplaintPhase() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CbComplaintPhase
        CbComplaintPhaseDTO cbComplaintPhaseDTO = cbComplaintPhaseMapper.toDto(cbComplaintPhase);
        var returnedCbComplaintPhaseDTO = om.readValue(
            restCbComplaintPhaseMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbComplaintPhaseDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CbComplaintPhaseDTO.class
        );

        // Validate the CbComplaintPhase in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCbComplaintPhase = cbComplaintPhaseMapper.toEntity(returnedCbComplaintPhaseDTO);
        assertCbComplaintPhaseUpdatableFieldsEquals(returnedCbComplaintPhase, getPersistedCbComplaintPhase(returnedCbComplaintPhase));
    }

    @Test
    @Transactional
    void createCbComplaintPhaseWithExistingId() throws Exception {
        // Create the CbComplaintPhase with an existing ID
        cbComplaintPhase.setId(1L);
        CbComplaintPhaseDTO cbComplaintPhaseDTO = cbComplaintPhaseMapper.toDto(cbComplaintPhase);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCbComplaintPhaseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbComplaintPhaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintPhase in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCbComplaintPhases() throws Exception {
        // Initialize the database
        cbComplaintPhaseRepository.saveAndFlush(cbComplaintPhase);

        // Get all the cbComplaintPhaseList
        restCbComplaintPhaseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cbComplaintPhase.getId().intValue())))
            .andExpect(jsonPath("$.[*].ordinalNo").value(hasItem(DEFAULT_ORDINAL_NO)))
            .andExpect(jsonPath("$.[*].complaintPhaseName").value(hasItem(DEFAULT_COMPLAINT_PHASE_NAME)));
    }

    @Test
    @Transactional
    void getCbComplaintPhase() throws Exception {
        // Initialize the database
        cbComplaintPhaseRepository.saveAndFlush(cbComplaintPhase);

        // Get the cbComplaintPhase
        restCbComplaintPhaseMockMvc
            .perform(get(ENTITY_API_URL_ID, cbComplaintPhase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cbComplaintPhase.getId().intValue()))
            .andExpect(jsonPath("$.ordinalNo").value(DEFAULT_ORDINAL_NO))
            .andExpect(jsonPath("$.complaintPhaseName").value(DEFAULT_COMPLAINT_PHASE_NAME));
    }

    @Test
    @Transactional
    void getNonExistingCbComplaintPhase() throws Exception {
        // Get the cbComplaintPhase
        restCbComplaintPhaseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCbComplaintPhase() throws Exception {
        // Initialize the database
        cbComplaintPhaseRepository.saveAndFlush(cbComplaintPhase);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbComplaintPhase
        CbComplaintPhase updatedCbComplaintPhase = cbComplaintPhaseRepository.findById(cbComplaintPhase.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCbComplaintPhase are not directly saved in db
        em.detach(updatedCbComplaintPhase);
        updatedCbComplaintPhase.ordinalNo(UPDATED_ORDINAL_NO).complaintPhaseName(UPDATED_COMPLAINT_PHASE_NAME);
        CbComplaintPhaseDTO cbComplaintPhaseDTO = cbComplaintPhaseMapper.toDto(updatedCbComplaintPhase);

        restCbComplaintPhaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cbComplaintPhaseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbComplaintPhaseDTO))
            )
            .andExpect(status().isOk());

        // Validate the CbComplaintPhase in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCbComplaintPhaseToMatchAllProperties(updatedCbComplaintPhase);
    }

    @Test
    @Transactional
    void putNonExistingCbComplaintPhase() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintPhase.setId(longCount.incrementAndGet());

        // Create the CbComplaintPhase
        CbComplaintPhaseDTO cbComplaintPhaseDTO = cbComplaintPhaseMapper.toDto(cbComplaintPhase);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCbComplaintPhaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cbComplaintPhaseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbComplaintPhaseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintPhase in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCbComplaintPhase() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintPhase.setId(longCount.incrementAndGet());

        // Create the CbComplaintPhase
        CbComplaintPhaseDTO cbComplaintPhaseDTO = cbComplaintPhaseMapper.toDto(cbComplaintPhase);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbComplaintPhaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbComplaintPhaseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintPhase in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCbComplaintPhase() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintPhase.setId(longCount.incrementAndGet());

        // Create the CbComplaintPhase
        CbComplaintPhaseDTO cbComplaintPhaseDTO = cbComplaintPhaseMapper.toDto(cbComplaintPhase);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbComplaintPhaseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbComplaintPhaseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CbComplaintPhase in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCbComplaintPhaseWithPatch() throws Exception {
        // Initialize the database
        cbComplaintPhaseRepository.saveAndFlush(cbComplaintPhase);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbComplaintPhase using partial update
        CbComplaintPhase partialUpdatedCbComplaintPhase = new CbComplaintPhase();
        partialUpdatedCbComplaintPhase.setId(cbComplaintPhase.getId());

        partialUpdatedCbComplaintPhase.ordinalNo(UPDATED_ORDINAL_NO);

        restCbComplaintPhaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCbComplaintPhase.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCbComplaintPhase))
            )
            .andExpect(status().isOk());

        // Validate the CbComplaintPhase in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCbComplaintPhaseUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCbComplaintPhase, cbComplaintPhase),
            getPersistedCbComplaintPhase(cbComplaintPhase)
        );
    }

    @Test
    @Transactional
    void fullUpdateCbComplaintPhaseWithPatch() throws Exception {
        // Initialize the database
        cbComplaintPhaseRepository.saveAndFlush(cbComplaintPhase);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbComplaintPhase using partial update
        CbComplaintPhase partialUpdatedCbComplaintPhase = new CbComplaintPhase();
        partialUpdatedCbComplaintPhase.setId(cbComplaintPhase.getId());

        partialUpdatedCbComplaintPhase.ordinalNo(UPDATED_ORDINAL_NO).complaintPhaseName(UPDATED_COMPLAINT_PHASE_NAME);

        restCbComplaintPhaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCbComplaintPhase.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCbComplaintPhase))
            )
            .andExpect(status().isOk());

        // Validate the CbComplaintPhase in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCbComplaintPhaseUpdatableFieldsEquals(
            partialUpdatedCbComplaintPhase,
            getPersistedCbComplaintPhase(partialUpdatedCbComplaintPhase)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCbComplaintPhase() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintPhase.setId(longCount.incrementAndGet());

        // Create the CbComplaintPhase
        CbComplaintPhaseDTO cbComplaintPhaseDTO = cbComplaintPhaseMapper.toDto(cbComplaintPhase);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCbComplaintPhaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cbComplaintPhaseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cbComplaintPhaseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintPhase in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCbComplaintPhase() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintPhase.setId(longCount.incrementAndGet());

        // Create the CbComplaintPhase
        CbComplaintPhaseDTO cbComplaintPhaseDTO = cbComplaintPhaseMapper.toDto(cbComplaintPhase);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbComplaintPhaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cbComplaintPhaseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintPhase in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCbComplaintPhase() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintPhase.setId(longCount.incrementAndGet());

        // Create the CbComplaintPhase
        CbComplaintPhaseDTO cbComplaintPhaseDTO = cbComplaintPhaseMapper.toDto(cbComplaintPhase);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbComplaintPhaseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cbComplaintPhaseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CbComplaintPhase in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCbComplaintPhase() throws Exception {
        // Initialize the database
        cbComplaintPhaseRepository.saveAndFlush(cbComplaintPhase);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cbComplaintPhase
        restCbComplaintPhaseMockMvc
            .perform(delete(ENTITY_API_URL_ID, cbComplaintPhase.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cbComplaintPhaseRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected CbComplaintPhase getPersistedCbComplaintPhase(CbComplaintPhase cbComplaintPhase) {
        return cbComplaintPhaseRepository.findById(cbComplaintPhase.getId()).orElseThrow();
    }

    protected void assertPersistedCbComplaintPhaseToMatchAllProperties(CbComplaintPhase expectedCbComplaintPhase) {
        assertCbComplaintPhaseAllPropertiesEquals(expectedCbComplaintPhase, getPersistedCbComplaintPhase(expectedCbComplaintPhase));
    }

    protected void assertPersistedCbComplaintPhaseToMatchUpdatableProperties(CbComplaintPhase expectedCbComplaintPhase) {
        assertCbComplaintPhaseAllUpdatablePropertiesEquals(
            expectedCbComplaintPhase,
            getPersistedCbComplaintPhase(expectedCbComplaintPhase)
        );
    }
}
