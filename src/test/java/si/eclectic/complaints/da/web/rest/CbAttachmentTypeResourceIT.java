package si.eclectic.complaints.da.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static si.eclectic.complaints.da.domain.CbAttachmentTypeAsserts.*;
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
import si.eclectic.complaints.da.domain.CbAttachmentType;
import si.eclectic.complaints.da.repository.CbAttachmentTypeRepository;
import si.eclectic.complaints.da.service.dto.CbAttachmentTypeDTO;
import si.eclectic.complaints.da.service.mapper.CbAttachmentTypeMapper;

/**
 * Integration tests for the {@link CbAttachmentTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CbAttachmentTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EXTENSION = "AAAAAAAAAA";
    private static final String UPDATED_EXTENSION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cb-attachment-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CbAttachmentTypeRepository cbAttachmentTypeRepository;

    @Autowired
    private CbAttachmentTypeMapper cbAttachmentTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCbAttachmentTypeMockMvc;

    private CbAttachmentType cbAttachmentType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CbAttachmentType createEntity(EntityManager em) {
        CbAttachmentType cbAttachmentType = new CbAttachmentType().name(DEFAULT_NAME).extension(DEFAULT_EXTENSION);
        return cbAttachmentType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CbAttachmentType createUpdatedEntity(EntityManager em) {
        CbAttachmentType cbAttachmentType = new CbAttachmentType().name(UPDATED_NAME).extension(UPDATED_EXTENSION);
        return cbAttachmentType;
    }

    @BeforeEach
    public void initTest() {
        cbAttachmentType = createEntity(em);
    }

    @Test
    @Transactional
    void createCbAttachmentType() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CbAttachmentType
        CbAttachmentTypeDTO cbAttachmentTypeDTO = cbAttachmentTypeMapper.toDto(cbAttachmentType);
        var returnedCbAttachmentTypeDTO = om.readValue(
            restCbAttachmentTypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbAttachmentTypeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CbAttachmentTypeDTO.class
        );

        // Validate the CbAttachmentType in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCbAttachmentType = cbAttachmentTypeMapper.toEntity(returnedCbAttachmentTypeDTO);
        assertCbAttachmentTypeUpdatableFieldsEquals(returnedCbAttachmentType, getPersistedCbAttachmentType(returnedCbAttachmentType));
    }

    @Test
    @Transactional
    void createCbAttachmentTypeWithExistingId() throws Exception {
        // Create the CbAttachmentType with an existing ID
        cbAttachmentType.setId(1L);
        CbAttachmentTypeDTO cbAttachmentTypeDTO = cbAttachmentTypeMapper.toDto(cbAttachmentType);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCbAttachmentTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbAttachmentTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CbAttachmentType in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCbAttachmentTypes() throws Exception {
        // Initialize the database
        cbAttachmentTypeRepository.saveAndFlush(cbAttachmentType);

        // Get all the cbAttachmentTypeList
        restCbAttachmentTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cbAttachmentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].extension").value(hasItem(DEFAULT_EXTENSION)));
    }

    @Test
    @Transactional
    void getCbAttachmentType() throws Exception {
        // Initialize the database
        cbAttachmentTypeRepository.saveAndFlush(cbAttachmentType);

        // Get the cbAttachmentType
        restCbAttachmentTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, cbAttachmentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cbAttachmentType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.extension").value(DEFAULT_EXTENSION));
    }

    @Test
    @Transactional
    void getNonExistingCbAttachmentType() throws Exception {
        // Get the cbAttachmentType
        restCbAttachmentTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCbAttachmentType() throws Exception {
        // Initialize the database
        cbAttachmentTypeRepository.saveAndFlush(cbAttachmentType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbAttachmentType
        CbAttachmentType updatedCbAttachmentType = cbAttachmentTypeRepository.findById(cbAttachmentType.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCbAttachmentType are not directly saved in db
        em.detach(updatedCbAttachmentType);
        updatedCbAttachmentType.name(UPDATED_NAME).extension(UPDATED_EXTENSION);
        CbAttachmentTypeDTO cbAttachmentTypeDTO = cbAttachmentTypeMapper.toDto(updatedCbAttachmentType);

        restCbAttachmentTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cbAttachmentTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbAttachmentTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the CbAttachmentType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCbAttachmentTypeToMatchAllProperties(updatedCbAttachmentType);
    }

    @Test
    @Transactional
    void putNonExistingCbAttachmentType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbAttachmentType.setId(longCount.incrementAndGet());

        // Create the CbAttachmentType
        CbAttachmentTypeDTO cbAttachmentTypeDTO = cbAttachmentTypeMapper.toDto(cbAttachmentType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCbAttachmentTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cbAttachmentTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbAttachmentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbAttachmentType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCbAttachmentType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbAttachmentType.setId(longCount.incrementAndGet());

        // Create the CbAttachmentType
        CbAttachmentTypeDTO cbAttachmentTypeDTO = cbAttachmentTypeMapper.toDto(cbAttachmentType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbAttachmentTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbAttachmentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbAttachmentType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCbAttachmentType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbAttachmentType.setId(longCount.incrementAndGet());

        // Create the CbAttachmentType
        CbAttachmentTypeDTO cbAttachmentTypeDTO = cbAttachmentTypeMapper.toDto(cbAttachmentType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbAttachmentTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbAttachmentTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CbAttachmentType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCbAttachmentTypeWithPatch() throws Exception {
        // Initialize the database
        cbAttachmentTypeRepository.saveAndFlush(cbAttachmentType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbAttachmentType using partial update
        CbAttachmentType partialUpdatedCbAttachmentType = new CbAttachmentType();
        partialUpdatedCbAttachmentType.setId(cbAttachmentType.getId());

        restCbAttachmentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCbAttachmentType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCbAttachmentType))
            )
            .andExpect(status().isOk());

        // Validate the CbAttachmentType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCbAttachmentTypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCbAttachmentType, cbAttachmentType),
            getPersistedCbAttachmentType(cbAttachmentType)
        );
    }

    @Test
    @Transactional
    void fullUpdateCbAttachmentTypeWithPatch() throws Exception {
        // Initialize the database
        cbAttachmentTypeRepository.saveAndFlush(cbAttachmentType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbAttachmentType using partial update
        CbAttachmentType partialUpdatedCbAttachmentType = new CbAttachmentType();
        partialUpdatedCbAttachmentType.setId(cbAttachmentType.getId());

        partialUpdatedCbAttachmentType.name(UPDATED_NAME).extension(UPDATED_EXTENSION);

        restCbAttachmentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCbAttachmentType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCbAttachmentType))
            )
            .andExpect(status().isOk());

        // Validate the CbAttachmentType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCbAttachmentTypeUpdatableFieldsEquals(
            partialUpdatedCbAttachmentType,
            getPersistedCbAttachmentType(partialUpdatedCbAttachmentType)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCbAttachmentType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbAttachmentType.setId(longCount.incrementAndGet());

        // Create the CbAttachmentType
        CbAttachmentTypeDTO cbAttachmentTypeDTO = cbAttachmentTypeMapper.toDto(cbAttachmentType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCbAttachmentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cbAttachmentTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cbAttachmentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbAttachmentType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCbAttachmentType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbAttachmentType.setId(longCount.incrementAndGet());

        // Create the CbAttachmentType
        CbAttachmentTypeDTO cbAttachmentTypeDTO = cbAttachmentTypeMapper.toDto(cbAttachmentType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbAttachmentTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cbAttachmentTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbAttachmentType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCbAttachmentType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbAttachmentType.setId(longCount.incrementAndGet());

        // Create the CbAttachmentType
        CbAttachmentTypeDTO cbAttachmentTypeDTO = cbAttachmentTypeMapper.toDto(cbAttachmentType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbAttachmentTypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cbAttachmentTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CbAttachmentType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCbAttachmentType() throws Exception {
        // Initialize the database
        cbAttachmentTypeRepository.saveAndFlush(cbAttachmentType);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cbAttachmentType
        restCbAttachmentTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, cbAttachmentType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cbAttachmentTypeRepository.count();
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

    protected CbAttachmentType getPersistedCbAttachmentType(CbAttachmentType cbAttachmentType) {
        return cbAttachmentTypeRepository.findById(cbAttachmentType.getId()).orElseThrow();
    }

    protected void assertPersistedCbAttachmentTypeToMatchAllProperties(CbAttachmentType expectedCbAttachmentType) {
        assertCbAttachmentTypeAllPropertiesEquals(expectedCbAttachmentType, getPersistedCbAttachmentType(expectedCbAttachmentType));
    }

    protected void assertPersistedCbAttachmentTypeToMatchUpdatableProperties(CbAttachmentType expectedCbAttachmentType) {
        assertCbAttachmentTypeAllUpdatablePropertiesEquals(
            expectedCbAttachmentType,
            getPersistedCbAttachmentType(expectedCbAttachmentType)
        );
    }
}
