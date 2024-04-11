package si.eclectic.complaints.da.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static si.eclectic.complaints.da.domain.CbComplaintFieldAsserts.*;
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
import si.eclectic.complaints.da.domain.CbComplaintField;
import si.eclectic.complaints.da.repository.CbComplaintFieldRepository;
import si.eclectic.complaints.da.service.dto.CbComplaintFieldDTO;
import si.eclectic.complaints.da.service.mapper.CbComplaintFieldMapper;

/**
 * Integration tests for the {@link CbComplaintFieldResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CbComplaintFieldResourceIT {

    private static final String DEFAULT_COMPLAINT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPLAINT_FIELD_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cb-complaint-fields";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CbComplaintFieldRepository cbComplaintFieldRepository;

    @Autowired
    private CbComplaintFieldMapper cbComplaintFieldMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCbComplaintFieldMockMvc;

    private CbComplaintField cbComplaintField;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CbComplaintField createEntity(EntityManager em) {
        CbComplaintField cbComplaintField = new CbComplaintField().complaintFieldName(DEFAULT_COMPLAINT_FIELD_NAME);
        return cbComplaintField;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CbComplaintField createUpdatedEntity(EntityManager em) {
        CbComplaintField cbComplaintField = new CbComplaintField().complaintFieldName(UPDATED_COMPLAINT_FIELD_NAME);
        return cbComplaintField;
    }

    @BeforeEach
    public void initTest() {
        cbComplaintField = createEntity(em);
    }

    @Test
    @Transactional
    void createCbComplaintField() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CbComplaintField
        CbComplaintFieldDTO cbComplaintFieldDTO = cbComplaintFieldMapper.toDto(cbComplaintField);
        var returnedCbComplaintFieldDTO = om.readValue(
            restCbComplaintFieldMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbComplaintFieldDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CbComplaintFieldDTO.class
        );

        // Validate the CbComplaintField in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCbComplaintField = cbComplaintFieldMapper.toEntity(returnedCbComplaintFieldDTO);
        assertCbComplaintFieldUpdatableFieldsEquals(returnedCbComplaintField, getPersistedCbComplaintField(returnedCbComplaintField));
    }

    @Test
    @Transactional
    void createCbComplaintFieldWithExistingId() throws Exception {
        // Create the CbComplaintField with an existing ID
        cbComplaintField.setId(1L);
        CbComplaintFieldDTO cbComplaintFieldDTO = cbComplaintFieldMapper.toDto(cbComplaintField);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCbComplaintFieldMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbComplaintFieldDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintField in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCbComplaintFields() throws Exception {
        // Initialize the database
        cbComplaintFieldRepository.saveAndFlush(cbComplaintField);

        // Get all the cbComplaintFieldList
        restCbComplaintFieldMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cbComplaintField.getId().intValue())))
            .andExpect(jsonPath("$.[*].complaintFieldName").value(hasItem(DEFAULT_COMPLAINT_FIELD_NAME)));
    }

    @Test
    @Transactional
    void getCbComplaintField() throws Exception {
        // Initialize the database
        cbComplaintFieldRepository.saveAndFlush(cbComplaintField);

        // Get the cbComplaintField
        restCbComplaintFieldMockMvc
            .perform(get(ENTITY_API_URL_ID, cbComplaintField.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cbComplaintField.getId().intValue()))
            .andExpect(jsonPath("$.complaintFieldName").value(DEFAULT_COMPLAINT_FIELD_NAME));
    }

    @Test
    @Transactional
    void getNonExistingCbComplaintField() throws Exception {
        // Get the cbComplaintField
        restCbComplaintFieldMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCbComplaintField() throws Exception {
        // Initialize the database
        cbComplaintFieldRepository.saveAndFlush(cbComplaintField);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbComplaintField
        CbComplaintField updatedCbComplaintField = cbComplaintFieldRepository.findById(cbComplaintField.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCbComplaintField are not directly saved in db
        em.detach(updatedCbComplaintField);
        updatedCbComplaintField.complaintFieldName(UPDATED_COMPLAINT_FIELD_NAME);
        CbComplaintFieldDTO cbComplaintFieldDTO = cbComplaintFieldMapper.toDto(updatedCbComplaintField);

        restCbComplaintFieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cbComplaintFieldDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbComplaintFieldDTO))
            )
            .andExpect(status().isOk());

        // Validate the CbComplaintField in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCbComplaintFieldToMatchAllProperties(updatedCbComplaintField);
    }

    @Test
    @Transactional
    void putNonExistingCbComplaintField() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintField.setId(longCount.incrementAndGet());

        // Create the CbComplaintField
        CbComplaintFieldDTO cbComplaintFieldDTO = cbComplaintFieldMapper.toDto(cbComplaintField);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCbComplaintFieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cbComplaintFieldDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbComplaintFieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintField in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCbComplaintField() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintField.setId(longCount.incrementAndGet());

        // Create the CbComplaintField
        CbComplaintFieldDTO cbComplaintFieldDTO = cbComplaintFieldMapper.toDto(cbComplaintField);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbComplaintFieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbComplaintFieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintField in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCbComplaintField() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintField.setId(longCount.incrementAndGet());

        // Create the CbComplaintField
        CbComplaintFieldDTO cbComplaintFieldDTO = cbComplaintFieldMapper.toDto(cbComplaintField);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbComplaintFieldMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbComplaintFieldDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CbComplaintField in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCbComplaintFieldWithPatch() throws Exception {
        // Initialize the database
        cbComplaintFieldRepository.saveAndFlush(cbComplaintField);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbComplaintField using partial update
        CbComplaintField partialUpdatedCbComplaintField = new CbComplaintField();
        partialUpdatedCbComplaintField.setId(cbComplaintField.getId());

        partialUpdatedCbComplaintField.complaintFieldName(UPDATED_COMPLAINT_FIELD_NAME);

        restCbComplaintFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCbComplaintField.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCbComplaintField))
            )
            .andExpect(status().isOk());

        // Validate the CbComplaintField in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCbComplaintFieldUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCbComplaintField, cbComplaintField),
            getPersistedCbComplaintField(cbComplaintField)
        );
    }

    @Test
    @Transactional
    void fullUpdateCbComplaintFieldWithPatch() throws Exception {
        // Initialize the database
        cbComplaintFieldRepository.saveAndFlush(cbComplaintField);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbComplaintField using partial update
        CbComplaintField partialUpdatedCbComplaintField = new CbComplaintField();
        partialUpdatedCbComplaintField.setId(cbComplaintField.getId());

        partialUpdatedCbComplaintField.complaintFieldName(UPDATED_COMPLAINT_FIELD_NAME);

        restCbComplaintFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCbComplaintField.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCbComplaintField))
            )
            .andExpect(status().isOk());

        // Validate the CbComplaintField in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCbComplaintFieldUpdatableFieldsEquals(
            partialUpdatedCbComplaintField,
            getPersistedCbComplaintField(partialUpdatedCbComplaintField)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCbComplaintField() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintField.setId(longCount.incrementAndGet());

        // Create the CbComplaintField
        CbComplaintFieldDTO cbComplaintFieldDTO = cbComplaintFieldMapper.toDto(cbComplaintField);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCbComplaintFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cbComplaintFieldDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cbComplaintFieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintField in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCbComplaintField() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintField.setId(longCount.incrementAndGet());

        // Create the CbComplaintField
        CbComplaintFieldDTO cbComplaintFieldDTO = cbComplaintFieldMapper.toDto(cbComplaintField);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbComplaintFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cbComplaintFieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintField in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCbComplaintField() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintField.setId(longCount.incrementAndGet());

        // Create the CbComplaintField
        CbComplaintFieldDTO cbComplaintFieldDTO = cbComplaintFieldMapper.toDto(cbComplaintField);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbComplaintFieldMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cbComplaintFieldDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CbComplaintField in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCbComplaintField() throws Exception {
        // Initialize the database
        cbComplaintFieldRepository.saveAndFlush(cbComplaintField);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cbComplaintField
        restCbComplaintFieldMockMvc
            .perform(delete(ENTITY_API_URL_ID, cbComplaintField.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cbComplaintFieldRepository.count();
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

    protected CbComplaintField getPersistedCbComplaintField(CbComplaintField cbComplaintField) {
        return cbComplaintFieldRepository.findById(cbComplaintField.getId()).orElseThrow();
    }

    protected void assertPersistedCbComplaintFieldToMatchAllProperties(CbComplaintField expectedCbComplaintField) {
        assertCbComplaintFieldAllPropertiesEquals(expectedCbComplaintField, getPersistedCbComplaintField(expectedCbComplaintField));
    }

    protected void assertPersistedCbComplaintFieldToMatchUpdatableProperties(CbComplaintField expectedCbComplaintField) {
        assertCbComplaintFieldAllUpdatablePropertiesEquals(
            expectedCbComplaintField,
            getPersistedCbComplaintField(expectedCbComplaintField)
        );
    }
}
