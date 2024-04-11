package si.eclectic.complaints.da.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static si.eclectic.complaints.da.domain.ComplaintAsserts.*;
import static si.eclectic.complaints.da.web.rest.TestUtil.createUpdateProxyForBean;
import static si.eclectic.complaints.da.web.rest.TestUtil.sameInstant;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
import si.eclectic.complaints.da.domain.Complaint;
import si.eclectic.complaints.da.repository.ComplaintRepository;
import si.eclectic.complaints.da.service.dto.ComplaintDTO;
import si.eclectic.complaints.da.service.mapper.ComplaintMapper;

/**
 * Integration tests for the {@link ComplaintResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ComplaintResourceIT {

    private static final String DEFAULT_COMPLAINT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_COMPLAINT_TEXT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_AND_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_AND_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_WRITTEN_REPLY_BY_SMS = false;
    private static final Boolean UPDATED_WRITTEN_REPLY_BY_SMS = true;

    private static final String ENTITY_API_URL = "/api/complaints";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private ComplaintMapper complaintMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComplaintMockMvc;

    private Complaint complaint;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Complaint createEntity(EntityManager em) {
        Complaint complaint = new Complaint()
            .complaintText(DEFAULT_COMPLAINT_TEXT)
            .dateAndTime(DEFAULT_DATE_AND_TIME)
            .writtenReplyBySMS(DEFAULT_WRITTEN_REPLY_BY_SMS);
        return complaint;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Complaint createUpdatedEntity(EntityManager em) {
        Complaint complaint = new Complaint()
            .complaintText(UPDATED_COMPLAINT_TEXT)
            .dateAndTime(UPDATED_DATE_AND_TIME)
            .writtenReplyBySMS(UPDATED_WRITTEN_REPLY_BY_SMS);
        return complaint;
    }

    @BeforeEach
    public void initTest() {
        complaint = createEntity(em);
    }

    @Test
    @Transactional
    void createComplaint() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Complaint
        ComplaintDTO complaintDTO = complaintMapper.toDto(complaint);
        var returnedComplaintDTO = om.readValue(
            restComplaintMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(complaintDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ComplaintDTO.class
        );

        // Validate the Complaint in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedComplaint = complaintMapper.toEntity(returnedComplaintDTO);
        assertComplaintUpdatableFieldsEquals(returnedComplaint, getPersistedComplaint(returnedComplaint));
    }

    @Test
    @Transactional
    void createComplaintWithExistingId() throws Exception {
        // Create the Complaint with an existing ID
        complaint.setId(1L);
        ComplaintDTO complaintDTO = complaintMapper.toDto(complaint);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restComplaintMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(complaintDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Complaint in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllComplaints() throws Exception {
        // Initialize the database
        complaintRepository.saveAndFlush(complaint);

        // Get all the complaintList
        restComplaintMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(complaint.getId().intValue())))
            .andExpect(jsonPath("$.[*].complaintText").value(hasItem(DEFAULT_COMPLAINT_TEXT)))
            .andExpect(jsonPath("$.[*].dateAndTime").value(hasItem(sameInstant(DEFAULT_DATE_AND_TIME))))
            .andExpect(jsonPath("$.[*].writtenReplyBySMS").value(hasItem(DEFAULT_WRITTEN_REPLY_BY_SMS.booleanValue())));
    }

    @Test
    @Transactional
    void getComplaint() throws Exception {
        // Initialize the database
        complaintRepository.saveAndFlush(complaint);

        // Get the complaint
        restComplaintMockMvc
            .perform(get(ENTITY_API_URL_ID, complaint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(complaint.getId().intValue()))
            .andExpect(jsonPath("$.complaintText").value(DEFAULT_COMPLAINT_TEXT))
            .andExpect(jsonPath("$.dateAndTime").value(sameInstant(DEFAULT_DATE_AND_TIME)))
            .andExpect(jsonPath("$.writtenReplyBySMS").value(DEFAULT_WRITTEN_REPLY_BY_SMS.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingComplaint() throws Exception {
        // Get the complaint
        restComplaintMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingComplaint() throws Exception {
        // Initialize the database
        complaintRepository.saveAndFlush(complaint);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the complaint
        Complaint updatedComplaint = complaintRepository.findById(complaint.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedComplaint are not directly saved in db
        em.detach(updatedComplaint);
        updatedComplaint
            .complaintText(UPDATED_COMPLAINT_TEXT)
            .dateAndTime(UPDATED_DATE_AND_TIME)
            .writtenReplyBySMS(UPDATED_WRITTEN_REPLY_BY_SMS);
        ComplaintDTO complaintDTO = complaintMapper.toDto(updatedComplaint);

        restComplaintMockMvc
            .perform(
                put(ENTITY_API_URL_ID, complaintDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(complaintDTO))
            )
            .andExpect(status().isOk());

        // Validate the Complaint in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedComplaintToMatchAllProperties(updatedComplaint);
    }

    @Test
    @Transactional
    void putNonExistingComplaint() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        complaint.setId(longCount.incrementAndGet());

        // Create the Complaint
        ComplaintDTO complaintDTO = complaintMapper.toDto(complaint);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComplaintMockMvc
            .perform(
                put(ENTITY_API_URL_ID, complaintDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(complaintDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Complaint in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchComplaint() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        complaint.setId(longCount.incrementAndGet());

        // Create the Complaint
        ComplaintDTO complaintDTO = complaintMapper.toDto(complaint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComplaintMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(complaintDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Complaint in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamComplaint() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        complaint.setId(longCount.incrementAndGet());

        // Create the Complaint
        ComplaintDTO complaintDTO = complaintMapper.toDto(complaint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComplaintMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(complaintDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Complaint in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateComplaintWithPatch() throws Exception {
        // Initialize the database
        complaintRepository.saveAndFlush(complaint);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the complaint using partial update
        Complaint partialUpdatedComplaint = new Complaint();
        partialUpdatedComplaint.setId(complaint.getId());

        partialUpdatedComplaint.complaintText(UPDATED_COMPLAINT_TEXT).writtenReplyBySMS(UPDATED_WRITTEN_REPLY_BY_SMS);

        restComplaintMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComplaint.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedComplaint))
            )
            .andExpect(status().isOk());

        // Validate the Complaint in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertComplaintUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedComplaint, complaint),
            getPersistedComplaint(complaint)
        );
    }

    @Test
    @Transactional
    void fullUpdateComplaintWithPatch() throws Exception {
        // Initialize the database
        complaintRepository.saveAndFlush(complaint);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the complaint using partial update
        Complaint partialUpdatedComplaint = new Complaint();
        partialUpdatedComplaint.setId(complaint.getId());

        partialUpdatedComplaint
            .complaintText(UPDATED_COMPLAINT_TEXT)
            .dateAndTime(UPDATED_DATE_AND_TIME)
            .writtenReplyBySMS(UPDATED_WRITTEN_REPLY_BY_SMS);

        restComplaintMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComplaint.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedComplaint))
            )
            .andExpect(status().isOk());

        // Validate the Complaint in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertComplaintUpdatableFieldsEquals(partialUpdatedComplaint, getPersistedComplaint(partialUpdatedComplaint));
    }

    @Test
    @Transactional
    void patchNonExistingComplaint() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        complaint.setId(longCount.incrementAndGet());

        // Create the Complaint
        ComplaintDTO complaintDTO = complaintMapper.toDto(complaint);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComplaintMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, complaintDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(complaintDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Complaint in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchComplaint() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        complaint.setId(longCount.incrementAndGet());

        // Create the Complaint
        ComplaintDTO complaintDTO = complaintMapper.toDto(complaint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComplaintMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(complaintDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Complaint in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamComplaint() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        complaint.setId(longCount.incrementAndGet());

        // Create the Complaint
        ComplaintDTO complaintDTO = complaintMapper.toDto(complaint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComplaintMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(complaintDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Complaint in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteComplaint() throws Exception {
        // Initialize the database
        complaintRepository.saveAndFlush(complaint);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the complaint
        restComplaintMockMvc
            .perform(delete(ENTITY_API_URL_ID, complaint.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return complaintRepository.count();
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

    protected Complaint getPersistedComplaint(Complaint complaint) {
        return complaintRepository.findById(complaint.getId()).orElseThrow();
    }

    protected void assertPersistedComplaintToMatchAllProperties(Complaint expectedComplaint) {
        assertComplaintAllPropertiesEquals(expectedComplaint, getPersistedComplaint(expectedComplaint));
    }

    protected void assertPersistedComplaintToMatchUpdatableProperties(Complaint expectedComplaint) {
        assertComplaintAllUpdatablePropertiesEquals(expectedComplaint, getPersistedComplaint(expectedComplaint));
    }
}
