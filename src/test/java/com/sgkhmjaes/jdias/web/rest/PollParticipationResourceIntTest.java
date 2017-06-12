package com.sgkhmjaes.jdias.web.rest;

import com.sgkhmjaes.jdias.JDiasApp;

import com.sgkhmjaes.jdias.domain.PollParticipation;
import com.sgkhmjaes.jdias.repository.PollParticipationRepository;
import com.sgkhmjaes.jdias.service.PollParticipationService;
import com.sgkhmjaes.jdias.repository.search.PollParticipationSearchRepository;
import com.sgkhmjaes.jdias.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PollParticipationResource REST controller.
 *
 * @see PollParticipationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JDiasApp.class)
public class PollParticipationResourceIntTest {

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_GUID = "BBBBBBBBBB";

    private static final String DEFAULT_POLL_ANSWER_GUID = "AAAAAAAAAA";
    private static final String UPDATED_POLL_ANSWER_GUID = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR_SIGNATURE = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR_SIGNATURE = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_AUTHOR_SIGNATURE = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_AUTHOR_SIGNATURE = "BBBBBBBBBB";

    @Autowired
    private PollParticipationRepository pollParticipationRepository;

    @Autowired
    private PollParticipationService pollParticipationService;

    @Autowired
    private PollParticipationSearchRepository pollParticipationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPollParticipationMockMvc;

    private PollParticipation pollParticipation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PollParticipationResource pollParticipationResource = new PollParticipationResource(pollParticipationService);
        this.restPollParticipationMockMvc = MockMvcBuilders.standaloneSetup(pollParticipationResource)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(exceptionTranslator)
                .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PollParticipation createEntity(EntityManager em) {
        PollParticipation pollParticipation = new PollParticipation()
                .author(DEFAULT_AUTHOR)
                .guid(DEFAULT_GUID)
                .parentGuid(DEFAULT_PARENT_GUID)
                .pollAnswerGuid(DEFAULT_POLL_ANSWER_GUID)
                .authorSignature(DEFAULT_AUTHOR_SIGNATURE)
                .parentAuthorSignature(DEFAULT_PARENT_AUTHOR_SIGNATURE);
        return pollParticipation;
    }

    @Before
    public void initTest() {
        pollParticipationSearchRepository.deleteAll();
        pollParticipation = createEntity(em);
    }

    @Test
    @Transactional
    public void createPollParticipation() throws Exception {
        int databaseSizeBeforeCreate = pollParticipationRepository.findAll().size();

        // Create the PollParticipation
        restPollParticipationMockMvc.perform(post("/api/poll-participations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pollParticipation)))
                .andExpect(status().isCreated());

        // Validate the PollParticipation in the database
        List<PollParticipation> pollParticipationList = pollParticipationRepository.findAll();
        assertThat(pollParticipationList).hasSize(databaseSizeBeforeCreate + 1);
        PollParticipation testPollParticipation = pollParticipationList.get(pollParticipationList.size() - 1);
        assertThat(testPollParticipation.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testPollParticipation.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testPollParticipation.getParentGuid()).isEqualTo(DEFAULT_PARENT_GUID);
        assertThat(testPollParticipation.getPollAnswerGuid()).isEqualTo(DEFAULT_POLL_ANSWER_GUID);
        assertThat(testPollParticipation.getAuthorSignature()).isEqualTo(DEFAULT_AUTHOR_SIGNATURE);
        assertThat(testPollParticipation.getParentAuthorSignature()).isEqualTo(DEFAULT_PARENT_AUTHOR_SIGNATURE);

        // Validate the PollParticipation in Elasticsearch
        PollParticipation pollParticipationEs = pollParticipationSearchRepository.findOne(testPollParticipation.getId());
        assertThat(pollParticipationEs).isEqualToComparingFieldByField(testPollParticipation);
    }

    @Test
    @Transactional
    public void createPollParticipationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pollParticipationRepository.findAll().size();

        // Create the PollParticipation with an existing ID
        pollParticipation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPollParticipationMockMvc.perform(post("/api/poll-participations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pollParticipation)))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PollParticipation> pollParticipationList = pollParticipationRepository.findAll();
        assertThat(pollParticipationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPollParticipations() throws Exception {
        // Initialize the database
        pollParticipationRepository.saveAndFlush(pollParticipation);

        // Get all the pollParticipationList
        restPollParticipationMockMvc.perform(get("/api/poll-participations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pollParticipation.getId().intValue())))
                .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
                .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID.toString())))
                .andExpect(jsonPath("$.[*].parentGuid").value(hasItem(DEFAULT_PARENT_GUID.toString())))
                .andExpect(jsonPath("$.[*].pollAnswerGuid").value(hasItem(DEFAULT_POLL_ANSWER_GUID.toString())))
                .andExpect(jsonPath("$.[*].authorSignature").value(hasItem(DEFAULT_AUTHOR_SIGNATURE.toString())))
                .andExpect(jsonPath("$.[*].parentAuthorSignature").value(hasItem(DEFAULT_PARENT_AUTHOR_SIGNATURE.toString())));
    }

    @Test
    @Transactional
    public void getPollParticipation() throws Exception {
        // Initialize the database
        pollParticipationRepository.saveAndFlush(pollParticipation);

        // Get the pollParticipation
        restPollParticipationMockMvc.perform(get("/api/poll-participations/{id}", pollParticipation.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(pollParticipation.getId().intValue()))
                .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
                .andExpect(jsonPath("$.guid").value(DEFAULT_GUID.toString()))
                .andExpect(jsonPath("$.parentGuid").value(DEFAULT_PARENT_GUID.toString()))
                .andExpect(jsonPath("$.pollAnswerGuid").value(DEFAULT_POLL_ANSWER_GUID.toString()))
                .andExpect(jsonPath("$.authorSignature").value(DEFAULT_AUTHOR_SIGNATURE.toString()))
                .andExpect(jsonPath("$.parentAuthorSignature").value(DEFAULT_PARENT_AUTHOR_SIGNATURE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPollParticipation() throws Exception {
        // Get the pollParticipation
        restPollParticipationMockMvc.perform(get("/api/poll-participations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePollParticipation() throws Exception {
        // Initialize the database
        pollParticipationService.save(pollParticipation);

        int databaseSizeBeforeUpdate = pollParticipationRepository.findAll().size();

        // Update the pollParticipation
        PollParticipation updatedPollParticipation = pollParticipationRepository.findOne(pollParticipation.getId());
        updatedPollParticipation
                .author(UPDATED_AUTHOR)
                .guid(UPDATED_GUID)
                .parentGuid(UPDATED_PARENT_GUID)
                .pollAnswerGuid(UPDATED_POLL_ANSWER_GUID)
                .authorSignature(UPDATED_AUTHOR_SIGNATURE)
                .parentAuthorSignature(UPDATED_PARENT_AUTHOR_SIGNATURE);

        restPollParticipationMockMvc.perform(put("/api/poll-participations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPollParticipation)))
                .andExpect(status().isOk());

        // Validate the PollParticipation in the database
        List<PollParticipation> pollParticipationList = pollParticipationRepository.findAll();
        assertThat(pollParticipationList).hasSize(databaseSizeBeforeUpdate);
        PollParticipation testPollParticipation = pollParticipationList.get(pollParticipationList.size() - 1);
        assertThat(testPollParticipation.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testPollParticipation.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testPollParticipation.getParentGuid()).isEqualTo(UPDATED_PARENT_GUID);
        assertThat(testPollParticipation.getPollAnswerGuid()).isEqualTo(UPDATED_POLL_ANSWER_GUID);
        assertThat(testPollParticipation.getAuthorSignature()).isEqualTo(UPDATED_AUTHOR_SIGNATURE);
        assertThat(testPollParticipation.getParentAuthorSignature()).isEqualTo(UPDATED_PARENT_AUTHOR_SIGNATURE);

        // Validate the PollParticipation in Elasticsearch
        PollParticipation pollParticipationEs = pollParticipationSearchRepository.findOne(testPollParticipation.getId());
        assertThat(pollParticipationEs).isEqualToComparingFieldByField(testPollParticipation);
    }

    @Test
    @Transactional
    public void updateNonExistingPollParticipation() throws Exception {
        int databaseSizeBeforeUpdate = pollParticipationRepository.findAll().size();

        // Create the PollParticipation
        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPollParticipationMockMvc.perform(put("/api/poll-participations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pollParticipation)))
                .andExpect(status().isCreated());

        // Validate the PollParticipation in the database
        List<PollParticipation> pollParticipationList = pollParticipationRepository.findAll();
        assertThat(pollParticipationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePollParticipation() throws Exception {
        // Initialize the database
        pollParticipationService.save(pollParticipation);

        int databaseSizeBeforeDelete = pollParticipationRepository.findAll().size();

        // Get the pollParticipation
        restPollParticipationMockMvc.perform(delete("/api/poll-participations/{id}", pollParticipation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean pollParticipationExistsInEs = pollParticipationSearchRepository.exists(pollParticipation.getId());
        assertThat(pollParticipationExistsInEs).isFalse();

        // Validate the database is empty
        List<PollParticipation> pollParticipationList = pollParticipationRepository.findAll();
        assertThat(pollParticipationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPollParticipation() throws Exception {
        // Initialize the database
        pollParticipationService.save(pollParticipation);

        // Search the pollParticipation
        restPollParticipationMockMvc.perform(get("/api/_search/poll-participations?query=id:" + pollParticipation.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pollParticipation.getId().intValue())))
                .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
                .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID.toString())))
                .andExpect(jsonPath("$.[*].parentGuid").value(hasItem(DEFAULT_PARENT_GUID.toString())))
                .andExpect(jsonPath("$.[*].pollAnswerGuid").value(hasItem(DEFAULT_POLL_ANSWER_GUID.toString())))
                .andExpect(jsonPath("$.[*].authorSignature").value(hasItem(DEFAULT_AUTHOR_SIGNATURE.toString())))
                .andExpect(jsonPath("$.[*].parentAuthorSignature").value(hasItem(DEFAULT_PARENT_AUTHOR_SIGNATURE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PollParticipation.class);
        PollParticipation pollParticipation1 = new PollParticipation();
        pollParticipation1.setId(1L);
        PollParticipation pollParticipation2 = new PollParticipation();
        pollParticipation2.setId(pollParticipation1.getId());
        assertThat(pollParticipation1).isEqualTo(pollParticipation2);
        pollParticipation2.setId(2L);
        assertThat(pollParticipation1).isNotEqualTo(pollParticipation2);
        pollParticipation1.setId(null);
        assertThat(pollParticipation1).isNotEqualTo(pollParticipation2);
    }
}
