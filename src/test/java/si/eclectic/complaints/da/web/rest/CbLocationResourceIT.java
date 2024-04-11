package si.eclectic.complaints.da.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static si.eclectic.complaints.da.domain.CbLocationAsserts.*;
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
import si.eclectic.complaints.da.domain.CbLocation;
import si.eclectic.complaints.da.repository.CbLocationRepository;
import si.eclectic.complaints.da.service.dto.CbLocationDTO;
import si.eclectic.complaints.da.service.mapper.CbLocationMapper;

/**
 * Integration tests for the {@link CbLocationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CbLocationResourceIT {

    private static final String DEFAULT_LOCATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cb-locations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CbLocationRepository cbLocationRepository;

    @Autowired
    private CbLocationMapper cbLocationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCbLocationMockMvc;

    private CbLocation cbLocation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CbLocation createEntity(EntityManager em) {
        CbLocation cbLocation = new CbLocation().locationName(DEFAULT_LOCATION_NAME);
        return cbLocation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CbLocation createUpdatedEntity(EntityManager em) {
        CbLocation cbLocation = new CbLocation().locationName(UPDATED_LOCATION_NAME);
        return cbLocation;
    }

    @BeforeEach
    public void initTest() {
        cbLocation = createEntity(em);
    }

    @Test
    @Transactional
    void createCbLocation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CbLocation
        CbLocationDTO cbLocationDTO = cbLocationMapper.toDto(cbLocation);
        var returnedCbLocationDTO = om.readValue(
            restCbLocationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbLocationDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CbLocationDTO.class
        );

        // Validate the CbLocation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCbLocation = cbLocationMapper.toEntity(returnedCbLocationDTO);
        assertCbLocationUpdatableFieldsEquals(returnedCbLocation, getPersistedCbLocation(returnedCbLocation));
    }

    @Test
    @Transactional
    void createCbLocationWithExistingId() throws Exception {
        // Create the CbLocation with an existing ID
        cbLocation.setId(1L);
        CbLocationDTO cbLocationDTO = cbLocationMapper.toDto(cbLocation);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCbLocationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbLocationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CbLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCbLocations() throws Exception {
        // Initialize the database
        cbLocationRepository.saveAndFlush(cbLocation);

        // Get all the cbLocationList
        restCbLocationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cbLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].locationName").value(hasItem(DEFAULT_LOCATION_NAME)));
    }

    @Test
    @Transactional
    void getCbLocation() throws Exception {
        // Initialize the database
        cbLocationRepository.saveAndFlush(cbLocation);

        // Get the cbLocation
        restCbLocationMockMvc
            .perform(get(ENTITY_API_URL_ID, cbLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cbLocation.getId().intValue()))
            .andExpect(jsonPath("$.locationName").value(DEFAULT_LOCATION_NAME));
    }

    @Test
    @Transactional
    void getNonExistingCbLocation() throws Exception {
        // Get the cbLocation
        restCbLocationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCbLocation() throws Exception {
        // Initialize the database
        cbLocationRepository.saveAndFlush(cbLocation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbLocation
        CbLocation updatedCbLocation = cbLocationRepository.findById(cbLocation.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCbLocation are not directly saved in db
        em.detach(updatedCbLocation);
        updatedCbLocation.locationName(UPDATED_LOCATION_NAME);
        CbLocationDTO cbLocationDTO = cbLocationMapper.toDto(updatedCbLocation);

        restCbLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cbLocationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbLocationDTO))
            )
            .andExpect(status().isOk());

        // Validate the CbLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCbLocationToMatchAllProperties(updatedCbLocation);
    }

    @Test
    @Transactional
    void putNonExistingCbLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbLocation.setId(longCount.incrementAndGet());

        // Create the CbLocation
        CbLocationDTO cbLocationDTO = cbLocationMapper.toDto(cbLocation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCbLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cbLocationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbLocationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCbLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbLocation.setId(longCount.incrementAndGet());

        // Create the CbLocation
        CbLocationDTO cbLocationDTO = cbLocationMapper.toDto(cbLocation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbLocationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbLocationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCbLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbLocation.setId(longCount.incrementAndGet());

        // Create the CbLocation
        CbLocationDTO cbLocationDTO = cbLocationMapper.toDto(cbLocation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbLocationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbLocationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CbLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCbLocationWithPatch() throws Exception {
        // Initialize the database
        cbLocationRepository.saveAndFlush(cbLocation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbLocation using partial update
        CbLocation partialUpdatedCbLocation = new CbLocation();
        partialUpdatedCbLocation.setId(cbLocation.getId());

        restCbLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCbLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCbLocation))
            )
            .andExpect(status().isOk());

        // Validate the CbLocation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCbLocationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCbLocation, cbLocation),
            getPersistedCbLocation(cbLocation)
        );
    }

    @Test
    @Transactional
    void fullUpdateCbLocationWithPatch() throws Exception {
        // Initialize the database
        cbLocationRepository.saveAndFlush(cbLocation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbLocation using partial update
        CbLocation partialUpdatedCbLocation = new CbLocation();
        partialUpdatedCbLocation.setId(cbLocation.getId());

        partialUpdatedCbLocation.locationName(UPDATED_LOCATION_NAME);

        restCbLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCbLocation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCbLocation))
            )
            .andExpect(status().isOk());

        // Validate the CbLocation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCbLocationUpdatableFieldsEquals(partialUpdatedCbLocation, getPersistedCbLocation(partialUpdatedCbLocation));
    }

    @Test
    @Transactional
    void patchNonExistingCbLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbLocation.setId(longCount.incrementAndGet());

        // Create the CbLocation
        CbLocationDTO cbLocationDTO = cbLocationMapper.toDto(cbLocation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCbLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cbLocationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cbLocationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCbLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbLocation.setId(longCount.incrementAndGet());

        // Create the CbLocation
        CbLocationDTO cbLocationDTO = cbLocationMapper.toDto(cbLocation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbLocationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cbLocationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCbLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbLocation.setId(longCount.incrementAndGet());

        // Create the CbLocation
        CbLocationDTO cbLocationDTO = cbLocationMapper.toDto(cbLocation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbLocationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cbLocationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CbLocation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCbLocation() throws Exception {
        // Initialize the database
        cbLocationRepository.saveAndFlush(cbLocation);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cbLocation
        restCbLocationMockMvc
            .perform(delete(ENTITY_API_URL_ID, cbLocation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cbLocationRepository.count();
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

    protected CbLocation getPersistedCbLocation(CbLocation cbLocation) {
        return cbLocationRepository.findById(cbLocation.getId()).orElseThrow();
    }

    protected void assertPersistedCbLocationToMatchAllProperties(CbLocation expectedCbLocation) {
        assertCbLocationAllPropertiesEquals(expectedCbLocation, getPersistedCbLocation(expectedCbLocation));
    }

    protected void assertPersistedCbLocationToMatchUpdatableProperties(CbLocation expectedCbLocation) {
        assertCbLocationAllUpdatablePropertiesEquals(expectedCbLocation, getPersistedCbLocation(expectedCbLocation));
    }
}
