package si.eclectic.complaints.da.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static si.eclectic.complaints.da.domain.CbComplaintChannelAsserts.*;
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
import si.eclectic.complaints.da.domain.CbComplaintChannel;
import si.eclectic.complaints.da.repository.CbComplaintChannelRepository;
import si.eclectic.complaints.da.service.dto.CbComplaintChannelDTO;
import si.eclectic.complaints.da.service.mapper.CbComplaintChannelMapper;

/**
 * Integration tests for the {@link CbComplaintChannelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CbComplaintChannelResourceIT {

    private static final String DEFAULT_COMPLAINT_CHANNEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPLAINT_CHANNEL_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cb-complaint-channels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CbComplaintChannelRepository cbComplaintChannelRepository;

    @Autowired
    private CbComplaintChannelMapper cbComplaintChannelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCbComplaintChannelMockMvc;

    private CbComplaintChannel cbComplaintChannel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CbComplaintChannel createEntity(EntityManager em) {
        CbComplaintChannel cbComplaintChannel = new CbComplaintChannel().complaintChannelName(DEFAULT_COMPLAINT_CHANNEL_NAME);
        return cbComplaintChannel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CbComplaintChannel createUpdatedEntity(EntityManager em) {
        CbComplaintChannel cbComplaintChannel = new CbComplaintChannel().complaintChannelName(UPDATED_COMPLAINT_CHANNEL_NAME);
        return cbComplaintChannel;
    }

    @BeforeEach
    public void initTest() {
        cbComplaintChannel = createEntity(em);
    }

    @Test
    @Transactional
    void createCbComplaintChannel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CbComplaintChannel
        CbComplaintChannelDTO cbComplaintChannelDTO = cbComplaintChannelMapper.toDto(cbComplaintChannel);
        var returnedCbComplaintChannelDTO = om.readValue(
            restCbComplaintChannelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbComplaintChannelDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CbComplaintChannelDTO.class
        );

        // Validate the CbComplaintChannel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCbComplaintChannel = cbComplaintChannelMapper.toEntity(returnedCbComplaintChannelDTO);
        assertCbComplaintChannelUpdatableFieldsEquals(
            returnedCbComplaintChannel,
            getPersistedCbComplaintChannel(returnedCbComplaintChannel)
        );
    }

    @Test
    @Transactional
    void createCbComplaintChannelWithExistingId() throws Exception {
        // Create the CbComplaintChannel with an existing ID
        cbComplaintChannel.setId(1L);
        CbComplaintChannelDTO cbComplaintChannelDTO = cbComplaintChannelMapper.toDto(cbComplaintChannel);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCbComplaintChannelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbComplaintChannelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintChannel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCbComplaintChannels() throws Exception {
        // Initialize the database
        cbComplaintChannelRepository.saveAndFlush(cbComplaintChannel);

        // Get all the cbComplaintChannelList
        restCbComplaintChannelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cbComplaintChannel.getId().intValue())))
            .andExpect(jsonPath("$.[*].complaintChannelName").value(hasItem(DEFAULT_COMPLAINT_CHANNEL_NAME)));
    }

    @Test
    @Transactional
    void getCbComplaintChannel() throws Exception {
        // Initialize the database
        cbComplaintChannelRepository.saveAndFlush(cbComplaintChannel);

        // Get the cbComplaintChannel
        restCbComplaintChannelMockMvc
            .perform(get(ENTITY_API_URL_ID, cbComplaintChannel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cbComplaintChannel.getId().intValue()))
            .andExpect(jsonPath("$.complaintChannelName").value(DEFAULT_COMPLAINT_CHANNEL_NAME));
    }

    @Test
    @Transactional
    void getNonExistingCbComplaintChannel() throws Exception {
        // Get the cbComplaintChannel
        restCbComplaintChannelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCbComplaintChannel() throws Exception {
        // Initialize the database
        cbComplaintChannelRepository.saveAndFlush(cbComplaintChannel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbComplaintChannel
        CbComplaintChannel updatedCbComplaintChannel = cbComplaintChannelRepository.findById(cbComplaintChannel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCbComplaintChannel are not directly saved in db
        em.detach(updatedCbComplaintChannel);
        updatedCbComplaintChannel.complaintChannelName(UPDATED_COMPLAINT_CHANNEL_NAME);
        CbComplaintChannelDTO cbComplaintChannelDTO = cbComplaintChannelMapper.toDto(updatedCbComplaintChannel);

        restCbComplaintChannelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cbComplaintChannelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbComplaintChannelDTO))
            )
            .andExpect(status().isOk());

        // Validate the CbComplaintChannel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCbComplaintChannelToMatchAllProperties(updatedCbComplaintChannel);
    }

    @Test
    @Transactional
    void putNonExistingCbComplaintChannel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintChannel.setId(longCount.incrementAndGet());

        // Create the CbComplaintChannel
        CbComplaintChannelDTO cbComplaintChannelDTO = cbComplaintChannelMapper.toDto(cbComplaintChannel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCbComplaintChannelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cbComplaintChannelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbComplaintChannelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintChannel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCbComplaintChannel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintChannel.setId(longCount.incrementAndGet());

        // Create the CbComplaintChannel
        CbComplaintChannelDTO cbComplaintChannelDTO = cbComplaintChannelMapper.toDto(cbComplaintChannel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbComplaintChannelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cbComplaintChannelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintChannel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCbComplaintChannel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintChannel.setId(longCount.incrementAndGet());

        // Create the CbComplaintChannel
        CbComplaintChannelDTO cbComplaintChannelDTO = cbComplaintChannelMapper.toDto(cbComplaintChannel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbComplaintChannelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cbComplaintChannelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CbComplaintChannel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCbComplaintChannelWithPatch() throws Exception {
        // Initialize the database
        cbComplaintChannelRepository.saveAndFlush(cbComplaintChannel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbComplaintChannel using partial update
        CbComplaintChannel partialUpdatedCbComplaintChannel = new CbComplaintChannel();
        partialUpdatedCbComplaintChannel.setId(cbComplaintChannel.getId());

        restCbComplaintChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCbComplaintChannel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCbComplaintChannel))
            )
            .andExpect(status().isOk());

        // Validate the CbComplaintChannel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCbComplaintChannelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCbComplaintChannel, cbComplaintChannel),
            getPersistedCbComplaintChannel(cbComplaintChannel)
        );
    }

    @Test
    @Transactional
    void fullUpdateCbComplaintChannelWithPatch() throws Exception {
        // Initialize the database
        cbComplaintChannelRepository.saveAndFlush(cbComplaintChannel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cbComplaintChannel using partial update
        CbComplaintChannel partialUpdatedCbComplaintChannel = new CbComplaintChannel();
        partialUpdatedCbComplaintChannel.setId(cbComplaintChannel.getId());

        partialUpdatedCbComplaintChannel.complaintChannelName(UPDATED_COMPLAINT_CHANNEL_NAME);

        restCbComplaintChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCbComplaintChannel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCbComplaintChannel))
            )
            .andExpect(status().isOk());

        // Validate the CbComplaintChannel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCbComplaintChannelUpdatableFieldsEquals(
            partialUpdatedCbComplaintChannel,
            getPersistedCbComplaintChannel(partialUpdatedCbComplaintChannel)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCbComplaintChannel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintChannel.setId(longCount.incrementAndGet());

        // Create the CbComplaintChannel
        CbComplaintChannelDTO cbComplaintChannelDTO = cbComplaintChannelMapper.toDto(cbComplaintChannel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCbComplaintChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cbComplaintChannelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cbComplaintChannelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintChannel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCbComplaintChannel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintChannel.setId(longCount.incrementAndGet());

        // Create the CbComplaintChannel
        CbComplaintChannelDTO cbComplaintChannelDTO = cbComplaintChannelMapper.toDto(cbComplaintChannel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbComplaintChannelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cbComplaintChannelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CbComplaintChannel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCbComplaintChannel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cbComplaintChannel.setId(longCount.incrementAndGet());

        // Create the CbComplaintChannel
        CbComplaintChannelDTO cbComplaintChannelDTO = cbComplaintChannelMapper.toDto(cbComplaintChannel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCbComplaintChannelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cbComplaintChannelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CbComplaintChannel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCbComplaintChannel() throws Exception {
        // Initialize the database
        cbComplaintChannelRepository.saveAndFlush(cbComplaintChannel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cbComplaintChannel
        restCbComplaintChannelMockMvc
            .perform(delete(ENTITY_API_URL_ID, cbComplaintChannel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cbComplaintChannelRepository.count();
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

    protected CbComplaintChannel getPersistedCbComplaintChannel(CbComplaintChannel cbComplaintChannel) {
        return cbComplaintChannelRepository.findById(cbComplaintChannel.getId()).orElseThrow();
    }

    protected void assertPersistedCbComplaintChannelToMatchAllProperties(CbComplaintChannel expectedCbComplaintChannel) {
        assertCbComplaintChannelAllPropertiesEquals(expectedCbComplaintChannel, getPersistedCbComplaintChannel(expectedCbComplaintChannel));
    }

    protected void assertPersistedCbComplaintChannelToMatchUpdatableProperties(CbComplaintChannel expectedCbComplaintChannel) {
        assertCbComplaintChannelAllUpdatablePropertiesEquals(
            expectedCbComplaintChannel,
            getPersistedCbComplaintChannel(expectedCbComplaintChannel)
        );
    }
}
