package si.eclectic.complaints.da.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static si.eclectic.complaints.da.domain.CbComplaintTypeAsserts.*;
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
import si.eclectic.complaints.da.domain.CbComplaintType;
import si.eclectic.complaints.da.repository.CbComplaintTypeRepository;
import si.eclectic.complaints.da.service.dto.CbComplaintTypeDTO;
import si.eclectic.complaints.da.service.mapper.CbComplaintTypeMapper;

/**
 * Integration tests for the {@link CbComplaintTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CbComplaintTypeResourceIT {

    private static final String DEFAULT_COMPLAINT_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPLAINT_TYPE_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cb-complaint-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CbComplaintTypeRepository cbComplaintTypeRepository;

    @Autowired
    private CbComplaintTypeMapper cbComplaintTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCbComplaintTypeMockMvc;

    private CbComplaintType cbComplaintType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CbComplaintType createEntity(EntityManager em) {
        CbComplaintType cbComplaintType = new CbComplaintType().complaintTypeName(DEFAULT_COMPLAINT_TYPE_NAME);
        return cbComplaintType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CbComplaintType createUpdatedEntity(EntityManager em) {
        CbComplaintType cbComplaintType = new CbComplaintType().complaintTypeName(UPDATED_COMPLAINT_TYPE_NAME);
        return cbComplaintType;
    }

    @BeforeEach
    public void initTest() {
        cbComplaintType = createEntity(em);
    }

    @Test
    @Transactional
    void createCbComplaintType() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CbComplaintType
        CbComplaintTypeDTO cbComplaintTypeDTO = cbComplaintTypeMapper.toDto(cbComplaintType);
        var returnedCbComplaintTypeDTO = om.readValue(
            restCbComplaintTypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbComplaintTypeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CbComplaintTypeDTO.class
        );

        // Validate the CbComplaintType in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCbComplaintType = cbComplaintTypeMapper.toEntity(returnedCbComplaintTypeDTO);
        assertCbComplaintTypeUpdatableFieldsEquals(returnedCbComplaintType, getPersistedCbComplaintType(returnedCbComplaintType));
    }

    @Test
    @Transactional
    void createCbComplaintTypeWithExistingId() throws Exception {
        // Create the CbComplaintType with an existing ID
        cbComplaintType.setId(1L);
        CbComplaintTypeDTO cbComplaintTypeDTO = cbComplaintTypeMapper.toDto(cbComplaintType);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCbComplaintTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbComplaintTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintType in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCbComplaintTypes() throws Exception {
        // Initialize the database
        cbComplaintTypeRepository.saveAndFlush(cbComplaintType);

        // Get all the cbComplaintTypeList
        restCbComplaintTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cbComplaintType.getId().intValue())))
            .andExpect(jsonPath("$.[*].complaintTypeName").value(hasItem(DEFAULT_COMPLAINT_TYPE_NAME)));
    }

    @Test
    @Transactional
    void getCbComplaintType() throws Exception {
        // Initialize the database
        cbComplaintTypeRepository.saveAndFlush(cbComplaintType);

        // Get the cbComplaintType
        restCbComplaintTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, cbComplaintType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cbComplaintType.getId().intValue()))
            .andExpect(jsonPath("$.complaintTypeName").value(DEFAULT_COMPLAINT_TYPE_NAME));
    }

    @Test
    @Transactional
    void getNonExistingCbComplaintType() throws Exception {
        // Get the cbComplaintType
        restCbComplaintTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCbComplaintType() throws Exception {
        // Initialize the database
        cbComplaintTypeRepository.saveAndFlush(cbComplaintType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbComplaintType
        CbComplaintType updatedCbComplaintType = cbComplaintTypeRepository.findById(cbComplaintType.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCbComplaintType are not directly saved in db
        em.detach(updatedCbComplaintType);
        updatedCbComplaintType.complaintTypeName(UPDATED_COMPLAINT_TYPE_NAME);
        CbComplaintTypeDTO cbComplaintTypeDTO = cbComplaintTypeMapper.toDto(updatedCbComplaintType);

        restCbComplaintTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cbComplaintTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbComplaintTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the CbComplaintType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCbComplaintTypeToMatchAllProperties(updatedCbComplaintType);
    }

    @Test
    @Transactional
    void putNonExistingCbComplaintType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintType.setId(longCount.incrementAndGet());

        // Create the CbComplaintType
        CbComplaintTypeDTO cbComplaintTypeDTO = cbComplaintTypeMapper.toDto(cbComplaintType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCbComplaintTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cbComplaintTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbComplaintTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCbComplaintType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintType.setId(longCount.incrementAndGet());

        // Create the CbComplaintType
        CbComplaintTypeDTO cbComplaintTypeDTO = cbComplaintTypeMapper.toDto(cbComplaintType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbComplaintTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbComplaintTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCbComplaintType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintType.setId(longCount.incrementAndGet());

        // Create the CbComplaintType
        CbComplaintTypeDTO cbComplaintTypeDTO = cbComplaintTypeMapper.toDto(cbComplaintType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbComplaintTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbComplaintTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CbComplaintType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCbComplaintTypeWithPatch() throws Exception {
        // Initialize the database
        cbComplaintTypeRepository.saveAndFlush(cbComplaintType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbComplaintType using partial update
        CbComplaintType partialUpdatedCbComplaintType = new CbComplaintType();
        partialUpdatedCbComplaintType.setId(cbComplaintType.getId());

        restCbComplaintTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCbComplaintType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCbComplaintType))
            )
            .andExpect(status().isOk());

        // Validate the CbComplaintType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCbComplaintTypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCbComplaintType, cbComplaintType),
            getPersistedCbComplaintType(cbComplaintType)
        );
    }

    @Test
    @Transactional
    void fullUpdateCbComplaintTypeWithPatch() throws Exception {
        // Initialize the database
        cbComplaintTypeRepository.saveAndFlush(cbComplaintType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbComplaintType using partial update
        CbComplaintType partialUpdatedCbComplaintType = new CbComplaintType();
        partialUpdatedCbComplaintType.setId(cbComplaintType.getId());

        partialUpdatedCbComplaintType.complaintTypeName(UPDATED_COMPLAINT_TYPE_NAME);

        restCbComplaintTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCbComplaintType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCbComplaintType))
            )
            .andExpect(status().isOk());

        // Validate the CbComplaintType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCbComplaintTypeUpdatableFieldsEquals(
            partialUpdatedCbComplaintType,
            getPersistedCbComplaintType(partialUpdatedCbComplaintType)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCbComplaintType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintType.setId(longCount.incrementAndGet());

        // Create the CbComplaintType
        CbComplaintTypeDTO cbComplaintTypeDTO = cbComplaintTypeMapper.toDto(cbComplaintType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCbComplaintTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cbComplaintTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cbComplaintTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCbComplaintType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintType.setId(longCount.incrementAndGet());

        // Create the CbComplaintType
        CbComplaintTypeDTO cbComplaintTypeDTO = cbComplaintTypeMapper.toDto(cbComplaintType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbComplaintTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cbComplaintTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCbComplaintType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintType.setId(longCount.incrementAndGet());

        // Create the CbComplaintType
        CbComplaintTypeDTO cbComplaintTypeDTO = cbComplaintTypeMapper.toDto(cbComplaintType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbComplaintTypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cbComplaintTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CbComplaintType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCbComplaintType() throws Exception {
        // Initialize the database
        cbComplaintTypeRepository.saveAndFlush(cbComplaintType);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cbComplaintType
        restCbComplaintTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, cbComplaintType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cbComplaintTypeRepository.count();
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

    protected CbComplaintType getPersistedCbComplaintType(CbComplaintType cbComplaintType) {
        return cbComplaintTypeRepository.findById(cbComplaintType.getId()).orElseThrow();
    }

    protected void assertPersistedCbComplaintTypeToMatchAllProperties(CbComplaintType expectedCbComplaintType) {
        assertCbComplaintTypeAllPropertiesEquals(expectedCbComplaintType, getPersistedCbComplaintType(expectedCbComplaintType));
    }

    protected void assertPersistedCbComplaintTypeToMatchUpdatableProperties(CbComplaintType expectedCbComplaintType) {
        assertCbComplaintTypeAllUpdatablePropertiesEquals(expectedCbComplaintType, getPersistedCbComplaintType(expectedCbComplaintType));
    }
}
