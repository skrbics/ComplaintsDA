package si.eclectic.complaints.da.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static si.eclectic.complaints.da.domain.CbApplicantCapacityTypeAsserts.*;
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
import si.eclectic.complaints.da.domain.CbApplicantCapacityType;
import si.eclectic.complaints.da.repository.CbApplicantCapacityTypeRepository;
import si.eclectic.complaints.da.service.dto.CbApplicantCapacityTypeDTO;
import si.eclectic.complaints.da.service.mapper.CbApplicantCapacityTypeMapper;

/**
 * Integration tests for the {@link CbApplicantCapacityTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CbApplicantCapacityTypeResourceIT {

    private static final String DEFAULT_APPLICANT_CAPACITY_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_APPLICANT_CAPACITY_TYPE_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cb-applicant-capacity-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CbApplicantCapacityTypeRepository cbApplicantCapacityTypeRepository;

    @Autowired
    private CbApplicantCapacityTypeMapper cbApplicantCapacityTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCbApplicantCapacityTypeMockMvc;

    private CbApplicantCapacityType cbApplicantCapacityType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CbApplicantCapacityType createEntity(EntityManager em) {
        CbApplicantCapacityType cbApplicantCapacityType = new CbApplicantCapacityType()
            .applicantCapacityTypeName(DEFAULT_APPLICANT_CAPACITY_TYPE_NAME);
        return cbApplicantCapacityType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CbApplicantCapacityType createUpdatedEntity(EntityManager em) {
        CbApplicantCapacityType cbApplicantCapacityType = new CbApplicantCapacityType()
            .applicantCapacityTypeName(UPDATED_APPLICANT_CAPACITY_TYPE_NAME);
        return cbApplicantCapacityType;
    }

    @BeforeEach
    public void initTest() {
        cbApplicantCapacityType = createEntity(em);
    }

    @Test
    @Transactional
    void createCbApplicantCapacityType() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CbApplicantCapacityType
        CbApplicantCapacityTypeDTO cbApplicantCapacityTypeDTO = cbApplicantCapacityTypeMapper.toDto(cbApplicantCapacityType);
        var returnedCbApplicantCapacityTypeDTO = om.readValue(
            restCbApplicantCapacityTypeMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbApplicantCapacityTypeDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CbApplicantCapacityTypeDTO.class
        );

        // Validate the CbApplicantCapacityType in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCbApplicantCapacityType = cbApplicantCapacityTypeMapper.toEntity(returnedCbApplicantCapacityTypeDTO);
        assertCbApplicantCapacityTypeUpdatableFieldsEquals(
            returnedCbApplicantCapacityType,
            getPersistedCbApplicantCapacityType(returnedCbApplicantCapacityType)
        );
    }

    @Test
    @Transactional
    void createCbApplicantCapacityTypeWithExistingId() throws Exception {
        // Create the CbApplicantCapacityType with an existing ID
        cbApplicantCapacityType.setId(1L);
        CbApplicantCapacityTypeDTO cbApplicantCapacityTypeDTO = cbApplicantCapacityTypeMapper.toDto(cbApplicantCapacityType);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCbApplicantCapacityTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbApplicantCapacityTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CbApplicantCapacityType in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCbApplicantCapacityTypes() throws Exception {
        // Initialize the database
        cbApplicantCapacityTypeRepository.saveAndFlush(cbApplicantCapacityType);

        // Get all the cbApplicantCapacityTypeList
        restCbApplicantCapacityTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cbApplicantCapacityType.getId().intValue())))
            .andExpect(jsonPath("$.[*].applicantCapacityTypeName").value(hasItem(DEFAULT_APPLICANT_CAPACITY_TYPE_NAME)));
    }

    @Test
    @Transactional
    void getCbApplicantCapacityType() throws Exception {
        // Initialize the database
        cbApplicantCapacityTypeRepository.saveAndFlush(cbApplicantCapacityType);

        // Get the cbApplicantCapacityType
        restCbApplicantCapacityTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, cbApplicantCapacityType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cbApplicantCapacityType.getId().intValue()))
            .andExpect(jsonPath("$.applicantCapacityTypeName").value(DEFAULT_APPLICANT_CAPACITY_TYPE_NAME));
    }

    @Test
    @Transactional
    void getNonExistingCbApplicantCapacityType() throws Exception {
        // Get the cbApplicantCapacityType
        restCbApplicantCapacityTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCbApplicantCapacityType() throws Exception {
        // Initialize the database
        cbApplicantCapacityTypeRepository.saveAndFlush(cbApplicantCapacityType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbApplicantCapacityType
        CbApplicantCapacityType updatedCbApplicantCapacityType = cbApplicantCapacityTypeRepository
            .findById(cbApplicantCapacityType.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedCbApplicantCapacityType are not directly saved in db
        em.detach(updatedCbApplicantCapacityType);
        updatedCbApplicantCapacityType.applicantCapacityTypeName(UPDATED_APPLICANT_CAPACITY_TYPE_NAME);
        CbApplicantCapacityTypeDTO cbApplicantCapacityTypeDTO = cbApplicantCapacityTypeMapper.toDto(updatedCbApplicantCapacityType);

        restCbApplicantCapacityTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cbApplicantCapacityTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbApplicantCapacityTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the CbApplicantCapacityType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCbApplicantCapacityTypeToMatchAllProperties(updatedCbApplicantCapacityType);
    }

    @Test
    @Transactional
    void putNonExistingCbApplicantCapacityType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbApplicantCapacityType.setId(longCount.incrementAndGet());

        // Create the CbApplicantCapacityType
        CbApplicantCapacityTypeDTO cbApplicantCapacityTypeDTO = cbApplicantCapacityTypeMapper.toDto(cbApplicantCapacityType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCbApplicantCapacityTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cbApplicantCapacityTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbApplicantCapacityTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbApplicantCapacityType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCbApplicantCapacityType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbApplicantCapacityType.setId(longCount.incrementAndGet());

        // Create the CbApplicantCapacityType
        CbApplicantCapacityTypeDTO cbApplicantCapacityTypeDTO = cbApplicantCapacityTypeMapper.toDto(cbApplicantCapacityType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbApplicantCapacityTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbApplicantCapacityTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbApplicantCapacityType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCbApplicantCapacityType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbApplicantCapacityType.setId(longCount.incrementAndGet());

        // Create the CbApplicantCapacityType
        CbApplicantCapacityTypeDTO cbApplicantCapacityTypeDTO = cbApplicantCapacityTypeMapper.toDto(cbApplicantCapacityType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbApplicantCapacityTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbApplicantCapacityTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CbApplicantCapacityType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCbApplicantCapacityTypeWithPatch() throws Exception {
        // Initialize the database
        cbApplicantCapacityTypeRepository.saveAndFlush(cbApplicantCapacityType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbApplicantCapacityType using partial update
        CbApplicantCapacityType partialUpdatedCbApplicantCapacityType = new CbApplicantCapacityType();
        partialUpdatedCbApplicantCapacityType.setId(cbApplicantCapacityType.getId());

        partialUpdatedCbApplicantCapacityType.applicantCapacityTypeName(UPDATED_APPLICANT_CAPACITY_TYPE_NAME);

        restCbApplicantCapacityTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCbApplicantCapacityType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCbApplicantCapacityType))
            )
            .andExpect(status().isOk());

        // Validate the CbApplicantCapacityType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCbApplicantCapacityTypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCbApplicantCapacityType, cbApplicantCapacityType),
            getPersistedCbApplicantCapacityType(cbApplicantCapacityType)
        );
    }

    @Test
    @Transactional
    void fullUpdateCbApplicantCapacityTypeWithPatch() throws Exception {
        // Initialize the database
        cbApplicantCapacityTypeRepository.saveAndFlush(cbApplicantCapacityType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbApplicantCapacityType using partial update
        CbApplicantCapacityType partialUpdatedCbApplicantCapacityType = new CbApplicantCapacityType();
        partialUpdatedCbApplicantCapacityType.setId(cbApplicantCapacityType.getId());

        partialUpdatedCbApplicantCapacityType.applicantCapacityTypeName(UPDATED_APPLICANT_CAPACITY_TYPE_NAME);

        restCbApplicantCapacityTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCbApplicantCapacityType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCbApplicantCapacityType))
            )
            .andExpect(status().isOk());

        // Validate the CbApplicantCapacityType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCbApplicantCapacityTypeUpdatableFieldsEquals(
            partialUpdatedCbApplicantCapacityType,
            getPersistedCbApplicantCapacityType(partialUpdatedCbApplicantCapacityType)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCbApplicantCapacityType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbApplicantCapacityType.setId(longCount.incrementAndGet());

        // Create the CbApplicantCapacityType
        CbApplicantCapacityTypeDTO cbApplicantCapacityTypeDTO = cbApplicantCapacityTypeMapper.toDto(cbApplicantCapacityType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCbApplicantCapacityTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cbApplicantCapacityTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cbApplicantCapacityTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbApplicantCapacityType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCbApplicantCapacityType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbApplicantCapacityType.setId(longCount.incrementAndGet());

        // Create the CbApplicantCapacityType
        CbApplicantCapacityTypeDTO cbApplicantCapacityTypeDTO = cbApplicantCapacityTypeMapper.toDto(cbApplicantCapacityType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbApplicantCapacityTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cbApplicantCapacityTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbApplicantCapacityType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCbApplicantCapacityType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbApplicantCapacityType.setId(longCount.incrementAndGet());

        // Create the CbApplicantCapacityType
        CbApplicantCapacityTypeDTO cbApplicantCapacityTypeDTO = cbApplicantCapacityTypeMapper.toDto(cbApplicantCapacityType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbApplicantCapacityTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cbApplicantCapacityTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CbApplicantCapacityType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCbApplicantCapacityType() throws Exception {
        // Initialize the database
        cbApplicantCapacityTypeRepository.saveAndFlush(cbApplicantCapacityType);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cbApplicantCapacityType
        restCbApplicantCapacityTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, cbApplicantCapacityType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cbApplicantCapacityTypeRepository.count();
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

    protected CbApplicantCapacityType getPersistedCbApplicantCapacityType(CbApplicantCapacityType cbApplicantCapacityType) {
        return cbApplicantCapacityTypeRepository.findById(cbApplicantCapacityType.getId()).orElseThrow();
    }

    protected void assertPersistedCbApplicantCapacityTypeToMatchAllProperties(CbApplicantCapacityType expectedCbApplicantCapacityType) {
        assertCbApplicantCapacityTypeAllPropertiesEquals(
            expectedCbApplicantCapacityType,
            getPersistedCbApplicantCapacityType(expectedCbApplicantCapacityType)
        );
    }

    protected void assertPersistedCbApplicantCapacityTypeToMatchUpdatableProperties(
        CbApplicantCapacityType expectedCbApplicantCapacityType
    ) {
        assertCbApplicantCapacityTypeAllUpdatablePropertiesEquals(
            expectedCbApplicantCapacityType,
            getPersistedCbApplicantCapacityType(expectedCbApplicantCapacityType)
        );
    }
}
