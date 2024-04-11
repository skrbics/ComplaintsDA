package si.eclectic.complaints.da.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static si.eclectic.complaints.da.domain.ComplaintAttachmentAsserts.*;
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
import si.eclectic.complaints.da.domain.ComplaintAttachment;
import si.eclectic.complaints.da.repository.ComplaintAttachmentRepository;
import si.eclectic.complaints.da.service.dto.ComplaintAttachmentDTO;
import si.eclectic.complaints.da.service.mapper.ComplaintAttachmentMapper;

/**
 * Integration tests for the {@link ComplaintAttachmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ComplaintAttachmentResourceIT {

    private static final Integer DEFAULT_ORDINAL_NO = 1;
    private static final Integer UPDATED_ORDINAL_NO = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/complaint-attachments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ComplaintAttachmentRepository complaintAttachmentRepository;

    @Autowired
    private ComplaintAttachmentMapper complaintAttachmentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComplaintAttachmentMockMvc;

    private ComplaintAttachment complaintAttachment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComplaintAttachment createEntity(EntityManager em) {
        ComplaintAttachment complaintAttachment = new ComplaintAttachment()
            .ordinalNo(DEFAULT_ORDINAL_NO)
            .name(DEFAULT_NAME)
            .path(DEFAULT_PATH);
        return complaintAttachment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComplaintAttachment createUpdatedEntity(EntityManager em) {
        ComplaintAttachment complaintAttachment = new ComplaintAttachment()
            .ordinalNo(UPDATED_ORDINAL_NO)
            .name(UPDATED_NAME)
            .path(UPDATED_PATH);
        return complaintAttachment;
    }

    @BeforeEach
    public void initTest() {
        complaintAttachment = createEntity(em);
    }

    @Test
    @Transactional
    void createComplaintAttachment() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ComplaintAttachment
        ComplaintAttachmentDTO complaintAttachmentDTO = complaintAttachmentMapper.toDto(complaintAttachment);
        var returnedComplaintAttachmentDTO = om.readValue(
            restComplaintAttachmentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(complaintAttachmentDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ComplaintAttachmentDTO.class
        );

        // Validate the ComplaintAttachment in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedComplaintAttachment = complaintAttachmentMapper.toEntity(returnedComplaintAttachmentDTO);
        assertComplaintAttachmentUpdatableFieldsEquals(
            returnedComplaintAttachment,
            getPersistedComplaintAttachment(returnedComplaintAttachment)
        );
    }

    @Test
    @Transactional
    void createComplaintAttachmentWithExistingId() throws Exception {
        // Create the ComplaintAttachment with an existing ID
        complaintAttachment.setId(1L);
        ComplaintAttachmentDTO complaintAttachmentDTO = complaintAttachmentMapper.toDto(complaintAttachment);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restComplaintAttachmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(complaintAttachmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ComplaintAttachment in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllComplaintAttachments() throws Exception {
        // Initialize the database
        complaintAttachmentRepository.saveAndFlush(complaintAttachment);

        // Get all the complaintAttachmentList
        restComplaintAttachmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(complaintAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].ordinalNo").value(hasItem(DEFAULT_ORDINAL_NO)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)));
    }

    @Test
    @Transactional
    void getComplaintAttachment() throws Exception {
        // Initialize the database
        complaintAttachmentRepository.saveAndFlush(complaintAttachment);

        // Get the complaintAttachment
        restComplaintAttachmentMockMvc
            .perform(get(ENTITY_API_URL_ID, complaintAttachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(complaintAttachment.getId().intValue()))
            .andExpect(jsonPath("$.ordinalNo").value(DEFAULT_ORDINAL_NO))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH));
    }

    @Test
    @Transactional
    void getNonExistingComplaintAttachment() throws Exception {
        // Get the complaintAttachment
        restComplaintAttachmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingComplaintAttachment() throws Exception {
        // Initialize the database
        complaintAttachmentRepository.saveAndFlush(complaintAttachment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the complaintAttachment
        ComplaintAttachment updatedComplaintAttachment = complaintAttachmentRepository.findById(complaintAttachment.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedComplaintAttachment are not directly saved in db
        em.detach(updatedComplaintAttachment);
        updatedComplaintAttachment.ordinalNo(UPDATED_ORDINAL_NO).name(UPDATED_NAME).path(UPDATED_PATH);
        ComplaintAttachmentDTO complaintAttachmentDTO = complaintAttachmentMapper.toDto(updatedComplaintAttachment);

        restComplaintAttachmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, complaintAttachmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(complaintAttachmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the ComplaintAttachment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedComplaintAttachmentToMatchAllProperties(updatedComplaintAttachment);
    }

    @Test
    @Transactional
    void putNonExistingComplaintAttachment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        complaintAttachment.setId(longCount.incrementAndGet());

        // Create the ComplaintAttachment
        ComplaintAttachmentDTO complaintAttachmentDTO = complaintAttachmentMapper.toDto(complaintAttachment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComplaintAttachmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, complaintAttachmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(complaintAttachmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComplaintAttachment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchComplaintAttachment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        complaintAttachment.setId(longCount.incrementAndGet());

        // Create the ComplaintAttachment
        ComplaintAttachmentDTO complaintAttachmentDTO = complaintAttachmentMapper.toDto(complaintAttachment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComplaintAttachmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(complaintAttachmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComplaintAttachment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamComplaintAttachment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        complaintAttachment.setId(longCount.incrementAndGet());

        // Create the ComplaintAttachment
        ComplaintAttachmentDTO complaintAttachmentDTO = complaintAttachmentMapper.toDto(complaintAttachment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComplaintAttachmentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(complaintAttachmentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ComplaintAttachment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateComplaintAttachmentWithPatch() throws Exception {
        // Initialize the database
        complaintAttachmentRepository.saveAndFlush(complaintAttachment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the complaintAttachment using partial update
        ComplaintAttachment partialUpdatedComplaintAttachment = new ComplaintAttachment();
        partialUpdatedComplaintAttachment.setId(complaintAttachment.getId());

        partialUpdatedComplaintAttachment.path(UPDATED_PATH);

        restComplaintAttachmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComplaintAttachment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedComplaintAttachment))
            )
            .andExpect(status().isOk());

        // Validate the ComplaintAttachment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertComplaintAttachmentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedComplaintAttachment, complaintAttachment),
            getPersistedComplaintAttachment(complaintAttachment)
        );
    }

    @Test
    @Transactional
    void fullUpdateComplaintAttachmentWithPatch() throws Exception {
        // Initialize the database
        complaintAttachmentRepository.saveAndFlush(complaintAttachment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the complaintAttachment using partial update
        ComplaintAttachment partialUpdatedComplaintAttachment = new ComplaintAttachment();
        partialUpdatedComplaintAttachment.setId(complaintAttachment.getId());

        partialUpdatedComplaintAttachment.ordinalNo(UPDATED_ORDINAL_NO).name(UPDATED_NAME).path(UPDATED_PATH);

        restComplaintAttachmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComplaintAttachment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedComplaintAttachment))
            )
            .andExpect(status().isOk());

        // Validate the ComplaintAttachment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertComplaintAttachmentUpdatableFieldsEquals(
            partialUpdatedComplaintAttachment,
            getPersistedComplaintAttachment(partialUpdatedComplaintAttachment)
        );
    }

    @Test
    @Transactional
    void patchNonExistingComplaintAttachment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        complaintAttachment.setId(longCount.incrementAndGet());

        // Create the ComplaintAttachment
        ComplaintAttachmentDTO complaintAttachmentDTO = complaintAttachmentMapper.toDto(complaintAttachment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComplaintAttachmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, complaintAttachmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(complaintAttachmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComplaintAttachment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchComplaintAttachment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        complaintAttachment.setId(longCount.incrementAndGet());

        // Create the ComplaintAttachment
        ComplaintAttachmentDTO complaintAttachmentDTO = complaintAttachmentMapper.toDto(complaintAttachment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComplaintAttachmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(complaintAttachmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComplaintAttachment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamComplaintAttachment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        complaintAttachment.setId(longCount.incrementAndGet());

        // Create the ComplaintAttachment
        ComplaintAttachmentDTO complaintAttachmentDTO = complaintAttachmentMapper.toDto(complaintAttachment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComplaintAttachmentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(complaintAttachmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ComplaintAttachment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteComplaintAttachment() throws Exception {
        // Initialize the database
        complaintAttachmentRepository.saveAndFlush(complaintAttachment);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the complaintAttachment
        restComplaintAttachmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, complaintAttachment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return complaintAttachmentRepository.count();
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

    protected ComplaintAttachment getPersistedComplaintAttachment(ComplaintAttachment complaintAttachment) {
        return complaintAttachmentRepository.findById(complaintAttachment.getId()).orElseThrow();
    }

    protected void assertPersistedComplaintAttachmentToMatchAllProperties(ComplaintAttachment expectedComplaintAttachment) {
        assertComplaintAttachmentAllPropertiesEquals(
            expectedComplaintAttachment,
            getPersistedComplaintAttachment(expectedComplaintAttachment)
        );
    }

    protected void assertPersistedComplaintAttachmentToMatchUpdatableProperties(ComplaintAttachment expectedComplaintAttachment) {
        assertComplaintAttachmentAllUpdatablePropertiesEquals(
            expectedComplaintAttachment,
            getPersistedComplaintAttachment(expectedComplaintAttachment)
        );
    }
}
