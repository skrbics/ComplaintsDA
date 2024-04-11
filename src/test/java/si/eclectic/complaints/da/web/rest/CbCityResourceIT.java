package si.eclectic.complaints.da.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static si.eclectic.complaints.da.domain.CbCityAsserts.*;
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
import si.eclectic.complaints.da.domain.CbCity;
import si.eclectic.complaints.da.repository.CbCityRepository;
import si.eclectic.complaints.da.service.dto.CbCityDTO;
import si.eclectic.complaints.da.service.mapper.CbCityMapper;

/**
 * Integration tests for the {@link CbCityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CbCityResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP = "AAAAAAAAAA";
    private static final String UPDATED_ZIP = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cb-cities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CbCityRepository cbCityRepository;

    @Autowired
    private CbCityMapper cbCityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCbCityMockMvc;

    private CbCity cbCity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CbCity createEntity(EntityManager em) {
        CbCity cbCity = new CbCity().name(DEFAULT_NAME).zip(DEFAULT_ZIP);
        return cbCity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CbCity createUpdatedEntity(EntityManager em) {
        CbCity cbCity = new CbCity().name(UPDATED_NAME).zip(UPDATED_ZIP);
        return cbCity;
    }

    @BeforeEach
    public void initTest() {
        cbCity = createEntity(em);
    }

    @Test
    @Transactional
    void createCbCity() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CbCity
        CbCityDTO cbCityDTO = cbCityMapper.toDto(cbCity);
        var returnedCbCityDTO = om.readValue(
            restCbCityMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbCityDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CbCityDTO.class
        );

        // Validate the CbCity in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCbCity = cbCityMapper.toEntity(returnedCbCityDTO);
        assertCbCityUpdatableFieldsEquals(returnedCbCity, getPersistedCbCity(returnedCbCity));
    }

    @Test
    @Transactional
    void createCbCityWithExistingId() throws Exception {
        // Create the CbCity with an existing ID
        cbCity.setId(1L);
        CbCityDTO cbCityDTO = cbCityMapper.toDto(cbCity);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCbCityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbCityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CbCity in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCbCities() throws Exception {
        // Initialize the database
        cbCityRepository.saveAndFlush(cbCity);

        // Get all the cbCityList
        restCbCityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cbCity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].zip").value(hasItem(DEFAULT_ZIP)));
    }

    @Test
    @Transactional
    void getCbCity() throws Exception {
        // Initialize the database
        cbCityRepository.saveAndFlush(cbCity);

        // Get the cbCity
        restCbCityMockMvc
            .perform(get(ENTITY_API_URL_ID, cbCity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cbCity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.zip").value(DEFAULT_ZIP));
    }

    @Test
    @Transactional
    void getNonExistingCbCity() throws Exception {
        // Get the cbCity
        restCbCityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCbCity() throws Exception {
        // Initialize the database
        cbCityRepository.saveAndFlush(cbCity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbCity
        CbCity updatedCbCity = cbCityRepository.findById(cbCity.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCbCity are not directly saved in db
        em.detach(updatedCbCity);
        updatedCbCity.name(UPDATED_NAME).zip(UPDATED_ZIP);
        CbCityDTO cbCityDTO = cbCityMapper.toDto(updatedCbCity);

        restCbCityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cbCityDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbCityDTO))
            )
            .andExpect(status().isOk());

        // Validate the CbCity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCbCityToMatchAllProperties(updatedCbCity);
    }

    @Test
    @Transactional
    void putNonExistingCbCity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbCity.setId(longCount.incrementAndGet());

        // Create the CbCity
        CbCityDTO cbCityDTO = cbCityMapper.toDto(cbCity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCbCityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cbCityDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbCityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbCity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCbCity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbCity.setId(longCount.incrementAndGet());

        // Create the CbCity
        CbCityDTO cbCityDTO = cbCityMapper.toDto(cbCity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbCityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbCityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbCity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCbCity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbCity.setId(longCount.incrementAndGet());

        // Create the CbCity
        CbCityDTO cbCityDTO = cbCityMapper.toDto(cbCity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbCityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbCityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CbCity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCbCityWithPatch() throws Exception {
        // Initialize the database
        cbCityRepository.saveAndFlush(cbCity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbCity using partial update
        CbCity partialUpdatedCbCity = new CbCity();
        partialUpdatedCbCity.setId(cbCity.getId());

        partialUpdatedCbCity.name(UPDATED_NAME);

        restCbCityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCbCity.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCbCity))
            )
            .andExpect(status().isOk());

        // Validate the CbCity in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCbCityUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCbCity, cbCity), getPersistedCbCity(cbCity));
    }

    @Test
    @Transactional
    void fullUpdateCbCityWithPatch() throws Exception {
        // Initialize the database
        cbCityRepository.saveAndFlush(cbCity);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbCity using partial update
        CbCity partialUpdatedCbCity = new CbCity();
        partialUpdatedCbCity.setId(cbCity.getId());

        partialUpdatedCbCity.name(UPDATED_NAME).zip(UPDATED_ZIP);

        restCbCityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCbCity.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCbCity))
            )
            .andExpect(status().isOk());

        // Validate the CbCity in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCbCityUpdatableFieldsEquals(partialUpdatedCbCity, getPersistedCbCity(partialUpdatedCbCity));
    }

    @Test
    @Transactional
    void patchNonExistingCbCity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbCity.setId(longCount.incrementAndGet());

        // Create the CbCity
        CbCityDTO cbCityDTO = cbCityMapper.toDto(cbCity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCbCityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cbCityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cbCityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbCity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCbCity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbCity.setId(longCount.incrementAndGet());

        // Create the CbCity
        CbCityDTO cbCityDTO = cbCityMapper.toDto(cbCity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbCityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cbCityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbCity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCbCity() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbCity.setId(longCount.incrementAndGet());

        // Create the CbCity
        CbCityDTO cbCityDTO = cbCityMapper.toDto(cbCity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbCityMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cbCityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CbCity in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCbCity() throws Exception {
        // Initialize the database
        cbCityRepository.saveAndFlush(cbCity);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cbCity
        restCbCityMockMvc
            .perform(delete(ENTITY_API_URL_ID, cbCity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cbCityRepository.count();
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

    protected CbCity getPersistedCbCity(CbCity cbCity) {
        return cbCityRepository.findById(cbCity.getId()).orElseThrow();
    }

    protected void assertPersistedCbCityToMatchAllProperties(CbCity expectedCbCity) {
        assertCbCityAllPropertiesEquals(expectedCbCity, getPersistedCbCity(expectedCbCity));
    }

    protected void assertPersistedCbCityToMatchUpdatableProperties(CbCity expectedCbCity) {
        assertCbCityAllUpdatablePropertiesEquals(expectedCbCity, getPersistedCbCity(expectedCbCity));
    }
}
