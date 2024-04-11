package si.eclectic.complaints.da.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static si.eclectic.complaints.da.domain.CbComplaintSubFieldAsserts.*;
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
import si.eclectic.complaints.da.domain.CbComplaintSubField;
import si.eclectic.complaints.da.repository.CbComplaintSubFieldRepository;
import si.eclectic.complaints.da.service.dto.CbComplaintSubFieldDTO;
import si.eclectic.complaints.da.service.mapper.CbComplaintSubFieldMapper;

/**
 * Integration tests for the {@link CbComplaintSubFieldResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CbComplaintSubFieldResourceIT {

    private static final String DEFAULT_COMPLAINT_SUB_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPLAINT_SUB_FIELD_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cb-complaint-sub-fields";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CbComplaintSubFieldRepository cbComplaintSubFieldRepository;

    @Autowired
    private CbComplaintSubFieldMapper cbComplaintSubFieldMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCbComplaintSubFieldMockMvc;

    private CbComplaintSubField cbComplaintSubField;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CbComplaintSubField createEntity(EntityManager em) {
        CbComplaintSubField cbComplaintSubField = new CbComplaintSubField().complaintSubFieldName(DEFAULT_COMPLAINT_SUB_FIELD_NAME);
        return cbComplaintSubField;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CbComplaintSubField createUpdatedEntity(EntityManager em) {
        CbComplaintSubField cbComplaintSubField = new CbComplaintSubField().complaintSubFieldName(UPDATED_COMPLAINT_SUB_FIELD_NAME);
        return cbComplaintSubField;
    }

    @BeforeEach
    public void initTest() {
        cbComplaintSubField = createEntity(em);
    }

    @Test
    @Transactional
    void createCbComplaintSubField() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CbComplaintSubField
        CbComplaintSubFieldDTO cbComplaintSubFieldDTO = cbComplaintSubFieldMapper.toDto(cbComplaintSubField);
        var returnedCbComplaintSubFieldDTO = om.readValue(
            restCbComplaintSubFieldMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbComplaintSubFieldDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CbComplaintSubFieldDTO.class
        );

        // Validate the CbComplaintSubField in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCbComplaintSubField = cbComplaintSubFieldMapper.toEntity(returnedCbComplaintSubFieldDTO);
        assertCbComplaintSubFieldUpdatableFieldsEquals(
            returnedCbComplaintSubField,
            getPersistedCbComplaintSubField(returnedCbComplaintSubField)
        );
    }

    @Test
    @Transactional
    void createCbComplaintSubFieldWithExistingId() throws Exception {
        // Create the CbComplaintSubField with an existing ID
        cbComplaintSubField.setId(1L);
        CbComplaintSubFieldDTO cbComplaintSubFieldDTO = cbComplaintSubFieldMapper.toDto(cbComplaintSubField);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCbComplaintSubFieldMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbComplaintSubFieldDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintSubField in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCbComplaintSubFields() throws Exception {
        // Initialize the database
        cbComplaintSubFieldRepository.saveAndFlush(cbComplaintSubField);

        // Get all the cbComplaintSubFieldList
        restCbComplaintSubFieldMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cbComplaintSubField.getId().intValue())))
            .andExpect(jsonPath("$.[*].complaintSubFieldName").value(hasItem(DEFAULT_COMPLAINT_SUB_FIELD_NAME)));
    }

    @Test
    @Transactional
    void getCbComplaintSubField() throws Exception {
        // Initialize the database
        cbComplaintSubFieldRepository.saveAndFlush(cbComplaintSubField);

        // Get the cbComplaintSubField
        restCbComplaintSubFieldMockMvc
            .perform(get(ENTITY_API_URL_ID, cbComplaintSubField.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cbComplaintSubField.getId().intValue()))
            .andExpect(jsonPath("$.complaintSubFieldName").value(DEFAULT_COMPLAINT_SUB_FIELD_NAME));
    }

    @Test
    @Transactional
    void getNonExistingCbComplaintSubField() throws Exception {
        // Get the cbComplaintSubField
        restCbComplaintSubFieldMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCbComplaintSubField() throws Exception {
        // Initialize the database
        cbComplaintSubFieldRepository.saveAndFlush(cbComplaintSubField);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbComplaintSubField
        CbComplaintSubField updatedCbComplaintSubField = cbComplaintSubFieldRepository.findById(cbComplaintSubField.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCbComplaintSubField are not directly saved in db
        em.detach(updatedCbComplaintSubField);
        updatedCbComplaintSubField.complaintSubFieldName(UPDATED_COMPLAINT_SUB_FIELD_NAME);
        CbComplaintSubFieldDTO cbComplaintSubFieldDTO = cbComplaintSubFieldMapper.toDto(updatedCbComplaintSubField);

        restCbComplaintSubFieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cbComplaintSubFieldDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbComplaintSubFieldDTO))
            )
            .andExpect(status().isOk());

        // Validate the CbComplaintSubField in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCbComplaintSubFieldToMatchAllProperties(updatedCbComplaintSubField);
    }

    @Test
    @Transactional
    void putNonExistingCbComplaintSubField() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintSubField.setId(longCount.incrementAndGet());

        // Create the CbComplaintSubField
        CbComplaintSubFieldDTO cbComplaintSubFieldDTO = cbComplaintSubFieldMapper.toDto(cbComplaintSubField);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCbComplaintSubFieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cbComplaintSubFieldDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbComplaintSubFieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintSubField in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCbComplaintSubField() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintSubField.setId(longCount.incrementAndGet());

        // Create the CbComplaintSubField
        CbComplaintSubFieldDTO cbComplaintSubFieldDTO = cbComplaintSubFieldMapper.toDto(cbComplaintSubField);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbComplaintSubFieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbComplaintSubFieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintSubField in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCbComplaintSubField() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintSubField.setId(longCount.incrementAndGet());

        // Create the CbComplaintSubField
        CbComplaintSubFieldDTO cbComplaintSubFieldDTO = cbComplaintSubFieldMapper.toDto(cbComplaintSubField);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbComplaintSubFieldMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbComplaintSubFieldDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CbComplaintSubField in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCbComplaintSubFieldWithPatch() throws Exception {
        // Initialize the database
        cbComplaintSubFieldRepository.saveAndFlush(cbComplaintSubField);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbComplaintSubField using partial update
        CbComplaintSubField partialUpdatedCbComplaintSubField = new CbComplaintSubField();
        partialUpdatedCbComplaintSubField.setId(cbComplaintSubField.getId());

        restCbComplaintSubFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCbComplaintSubField.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCbComplaintSubField))
            )
            .andExpect(status().isOk());

        // Validate the CbComplaintSubField in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCbComplaintSubFieldUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCbComplaintSubField, cbComplaintSubField),
            getPersistedCbComplaintSubField(cbComplaintSubField)
        );
    }

    @Test
    @Transactional
    void fullUpdateCbComplaintSubFieldWithPatch() throws Exception {
        // Initialize the database
        cbComplaintSubFieldRepository.saveAndFlush(cbComplaintSubField);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbComplaintSubField using partial update
        CbComplaintSubField partialUpdatedCbComplaintSubField = new CbComplaintSubField();
        partialUpdatedCbComplaintSubField.setId(cbComplaintSubField.getId());

        partialUpdatedCbComplaintSubField.complaintSubFieldName(UPDATED_COMPLAINT_SUB_FIELD_NAME);

        restCbComplaintSubFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCbComplaintSubField.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCbComplaintSubField))
            )
            .andExpect(status().isOk());

        // Validate the CbComplaintSubField in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCbComplaintSubFieldUpdatableFieldsEquals(
            partialUpdatedCbComplaintSubField,
            getPersistedCbComplaintSubField(partialUpdatedCbComplaintSubField)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCbComplaintSubField() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintSubField.setId(longCount.incrementAndGet());

        // Create the CbComplaintSubField
        CbComplaintSubFieldDTO cbComplaintSubFieldDTO = cbComplaintSubFieldMapper.toDto(cbComplaintSubField);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCbComplaintSubFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cbComplaintSubFieldDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cbComplaintSubFieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintSubField in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCbComplaintSubField() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintSubField.setId(longCount.incrementAndGet());

        // Create the CbComplaintSubField
        CbComplaintSubFieldDTO cbComplaintSubFieldDTO = cbComplaintSubFieldMapper.toDto(cbComplaintSubField);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbComplaintSubFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cbComplaintSubFieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintSubField in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCbComplaintSubField() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintSubField.setId(longCount.incrementAndGet());

        // Create the CbComplaintSubField
        CbComplaintSubFieldDTO cbComplaintSubFieldDTO = cbComplaintSubFieldMapper.toDto(cbComplaintSubField);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbComplaintSubFieldMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cbComplaintSubFieldDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CbComplaintSubField in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCbComplaintSubField() throws Exception {
        // Initialize the database
        cbComplaintSubFieldRepository.saveAndFlush(cbComplaintSubField);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cbComplaintSubField
        restCbComplaintSubFieldMockMvc
            .perform(delete(ENTITY_API_URL_ID, cbComplaintSubField.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cbComplaintSubFieldRepository.count();
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

    protected CbComplaintSubField getPersistedCbComplaintSubField(CbComplaintSubField cbComplaintSubField) {
        return cbComplaintSubFieldRepository.findById(cbComplaintSubField.getId()).orElseThrow();
    }

    protected void assertPersistedCbComplaintSubFieldToMatchAllProperties(CbComplaintSubField expectedCbComplaintSubField) {
        assertCbComplaintSubFieldAllPropertiesEquals(
            expectedCbComplaintSubField,
            getPersistedCbComplaintSubField(expectedCbComplaintSubField)
        );
    }

    protected void assertPersistedCbComplaintSubFieldToMatchUpdatableProperties(CbComplaintSubField expectedCbComplaintSubField) {
        assertCbComplaintSubFieldAllUpdatablePropertiesEquals(
            expectedCbComplaintSubField,
            getPersistedCbComplaintSubField(expectedCbComplaintSubField)
        );
    }
}
