package si.eclectic.complaints.da.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static si.eclectic.complaints.da.domain.CbCountryAsserts.*;
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
import si.eclectic.complaints.da.domain.CbCountry;
import si.eclectic.complaints.da.repository.CbCountryRepository;
import si.eclectic.complaints.da.service.dto.CbCountryDTO;
import si.eclectic.complaints.da.service.mapper.CbCountryMapper;

/**
 * Integration tests for the {@link CbCountryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CbCountryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ABBREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_ABBREVIATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cb-countries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CbCountryRepository cbCountryRepository;

    @Autowired
    private CbCountryMapper cbCountryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCbCountryMockMvc;

    private CbCountry cbCountry;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CbCountry createEntity(EntityManager em) {
        CbCountry cbCountry = new CbCountry().name(DEFAULT_NAME).abbreviation(DEFAULT_ABBREVIATION);
        return cbCountry;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CbCountry createUpdatedEntity(EntityManager em) {
        CbCountry cbCountry = new CbCountry().name(UPDATED_NAME).abbreviation(UPDATED_ABBREVIATION);
        return cbCountry;
    }

    @BeforeEach
    public void initTest() {
        cbCountry = createEntity(em);
    }

    @Test
    @Transactional
    void createCbCountry() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CbCountry
        CbCountryDTO cbCountryDTO = cbCountryMapper.toDto(cbCountry);
        var returnedCbCountryDTO = om.readValue(
            restCbCountryMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbCountryDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CbCountryDTO.class
        );

        // Validate the CbCountry in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCbCountry = cbCountryMapper.toEntity(returnedCbCountryDTO);
        assertCbCountryUpdatableFieldsEquals(returnedCbCountry, getPersistedCbCountry(returnedCbCountry));
    }

    @Test
    @Transactional
    void createCbCountryWithExistingId() throws Exception {
        // Create the CbCountry with an existing ID
        cbCountry.setId(1L);
        CbCountryDTO cbCountryDTO = cbCountryMapper.toDto(cbCountry);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCbCountryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbCountryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CbCountry in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCbCountries() throws Exception {
        // Initialize the database
        cbCountryRepository.saveAndFlush(cbCountry);

        // Get all the cbCountryList
        restCbCountryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cbCountry.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].abbreviation").value(hasItem(DEFAULT_ABBREVIATION)));
    }

    @Test
    @Transactional
    void getCbCountry() throws Exception {
        // Initialize the database
        cbCountryRepository.saveAndFlush(cbCountry);

        // Get the cbCountry
        restCbCountryMockMvc
            .perform(get(ENTITY_API_URL_ID, cbCountry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cbCountry.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.abbreviation").value(DEFAULT_ABBREVIATION));
    }

    @Test
    @Transactional
    void getNonExistingCbCountry() throws Exception {
        // Get the cbCountry
        restCbCountryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCbCountry() throws Exception {
        // Initialize the database
        cbCountryRepository.saveAndFlush(cbCountry);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbCountry
        CbCountry updatedCbCountry = cbCountryRepository.findById(cbCountry.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCbCountry are not directly saved in db
        em.detach(updatedCbCountry);
        updatedCbCountry.name(UPDATED_NAME).abbreviation(UPDATED_ABBREVIATION);
        CbCountryDTO cbCountryDTO = cbCountryMapper.toDto(updatedCbCountry);

        restCbCountryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cbCountryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbCountryDTO))
            )
            .andExpect(status().isOk());

        // Validate the CbCountry in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCbCountryToMatchAllProperties(updatedCbCountry);
    }

    @Test
    @Transactional
    void putNonExistingCbCountry() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbCountry.setId(longCount.incrementAndGet());

        // Create the CbCountry
        CbCountryDTO cbCountryDTO = cbCountryMapper.toDto(cbCountry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCbCountryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cbCountryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbCountryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbCountry in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCbCountry() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbCountry.setId(longCount.incrementAndGet());

        // Create the CbCountry
        CbCountryDTO cbCountryDTO = cbCountryMapper.toDto(cbCountry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbCountryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbCountryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbCountry in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCbCountry() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbCountry.setId(longCount.incrementAndGet());

        // Create the CbCountry
        CbCountryDTO cbCountryDTO = cbCountryMapper.toDto(cbCountry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbCountryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbCountryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CbCountry in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCbCountryWithPatch() throws Exception {
        // Initialize the database
        cbCountryRepository.saveAndFlush(cbCountry);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbCountry using partial update
        CbCountry partialUpdatedCbCountry = new CbCountry();
        partialUpdatedCbCountry.setId(cbCountry.getId());

        partialUpdatedCbCountry.name(UPDATED_NAME).abbreviation(UPDATED_ABBREVIATION);

        restCbCountryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCbCountry.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCbCountry))
            )
            .andExpect(status().isOk());

        // Validate the CbCountry in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCbCountryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCbCountry, cbCountry),
            getPersistedCbCountry(cbCountry)
        );
    }

    @Test
    @Transactional
    void fullUpdateCbCountryWithPatch() throws Exception {
        // Initialize the database
        cbCountryRepository.saveAndFlush(cbCountry);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbCountry using partial update
        CbCountry partialUpdatedCbCountry = new CbCountry();
        partialUpdatedCbCountry.setId(cbCountry.getId());

        partialUpdatedCbCountry.name(UPDATED_NAME).abbreviation(UPDATED_ABBREVIATION);

        restCbCountryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCbCountry.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCbCountry))
            )
            .andExpect(status().isOk());

        // Validate the CbCountry in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCbCountryUpdatableFieldsEquals(partialUpdatedCbCountry, getPersistedCbCountry(partialUpdatedCbCountry));
    }

    @Test
    @Transactional
    void patchNonExistingCbCountry() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbCountry.setId(longCount.incrementAndGet());

        // Create the CbCountry
        CbCountryDTO cbCountryDTO = cbCountryMapper.toDto(cbCountry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCbCountryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cbCountryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cbCountryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbCountry in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCbCountry() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbCountry.setId(longCount.incrementAndGet());

        // Create the CbCountry
        CbCountryDTO cbCountryDTO = cbCountryMapper.toDto(cbCountry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbCountryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cbCountryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbCountry in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCbCountry() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbCountry.setId(longCount.incrementAndGet());

        // Create the CbCountry
        CbCountryDTO cbCountryDTO = cbCountryMapper.toDto(cbCountry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbCountryMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cbCountryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CbCountry in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCbCountry() throws Exception {
        // Initialize the database
        cbCountryRepository.saveAndFlush(cbCountry);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cbCountry
        restCbCountryMockMvc
            .perform(delete(ENTITY_API_URL_ID, cbCountry.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cbCountryRepository.count();
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

    protected CbCountry getPersistedCbCountry(CbCountry cbCountry) {
        return cbCountryRepository.findById(cbCountry.getId()).orElseThrow();
    }

    protected void assertPersistedCbCountryToMatchAllProperties(CbCountry expectedCbCountry) {
        assertCbCountryAllPropertiesEquals(expectedCbCountry, getPersistedCbCountry(expectedCbCountry));
    }

    protected void assertPersistedCbCountryToMatchUpdatableProperties(CbCountry expectedCbCountry) {
        assertCbCountryAllUpdatablePropertiesEquals(expectedCbCountry, getPersistedCbCountry(expectedCbCountry));
    }
}
