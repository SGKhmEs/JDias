package com.sgkhmjaes.jdias.web.rest;

import com.sgkhmjaes.jdias.JDiasApp;

import com.sgkhmjaes.jdias.domain.PollAnswer;
import com.sgkhmjaes.jdias.repository.PollAnswerRepository;
import com.sgkhmjaes.jdias.repository.search.PollAnswerSearchRepository;
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
 * Test class for the PollAnswerResource REST controller.
 *
 * @see PollAnswerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JDiasApp.class)
public class PollAnswerResourceIntTest {

    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    private static final String DEFAULT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER = "BBBBBBBBBB";

    @Autowired
    private PollAnswerRepository pollAnswerRepository;

    @Autowired
    private PollAnswerSearchRepository pollAnswerSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPollAnswerMockMvc;

    private PollAnswer pollAnswer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PollAnswerResource pollAnswerResource = new PollAnswerResource(pollAnswerRepository, pollAnswerSearchRepository);
        this.restPollAnswerMockMvc = MockMvcBuilders.standaloneSetup(pollAnswerResource)
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
    public static PollAnswer createEntity(EntityManager em) {
        PollAnswer pollAnswer = new PollAnswer()
            .guid(DEFAULT_GUID)
            .answer(DEFAULT_ANSWER);
        return pollAnswer;
    }

    @Before
    public void initTest() {
        pollAnswerSearchRepository.deleteAll();
        pollAnswer = createEntity(em);
    }

    @Test
    @Transactional
    public void createPollAnswer() throws Exception {
        int databaseSizeBeforeCreate = pollAnswerRepository.findAll().size();

        // Create the PollAnswer
        restPollAnswerMockMvc.perform(post("/api/poll-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pollAnswer)))
            .andExpect(status().isCreated());

        // Validate the PollAnswer in the database
        List<PollAnswer> pollAnswerList = pollAnswerRepository.findAll();
        assertThat(pollAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        PollAnswer testPollAnswer = pollAnswerList.get(pollAnswerList.size() - 1);
        assertThat(testPollAnswer.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testPollAnswer.getAnswer()).isEqualTo(DEFAULT_ANSWER);

        // Validate the PollAnswer in Elasticsearch
        PollAnswer pollAnswerEs = pollAnswerSearchRepository.findOne(testPollAnswer.getId());
        assertThat(pollAnswerEs).isEqualToComparingFieldByField(testPollAnswer);
    }

    @Test
    @Transactional
    public void createPollAnswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pollAnswerRepository.findAll().size();

        // Create the PollAnswer with an existing ID
        pollAnswer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPollAnswerMockMvc.perform(post("/api/poll-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pollAnswer)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PollAnswer> pollAnswerList = pollAnswerRepository.findAll();
        assertThat(pollAnswerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPollAnswers() throws Exception {
        // Initialize the database
        pollAnswerRepository.saveAndFlush(pollAnswer);

        // Get all the pollAnswerList
        restPollAnswerMockMvc.perform(get("/api/poll-answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pollAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID.toString())))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER.toString())));
    }

    @Test
    @Transactional
    public void getPollAnswer() throws Exception {
        // Initialize the database
        pollAnswerRepository.saveAndFlush(pollAnswer);

        // Get the pollAnswer
        restPollAnswerMockMvc.perform(get("/api/poll-answers/{id}", pollAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pollAnswer.getId().intValue()))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID.toString()))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPollAnswer() throws Exception {
        // Get the pollAnswer
        restPollAnswerMockMvc.perform(get("/api/poll-answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePollAnswer() throws Exception {
        // Initialize the database
        pollAnswerRepository.saveAndFlush(pollAnswer);
        pollAnswerSearchRepository.save(pollAnswer);
        int databaseSizeBeforeUpdate = pollAnswerRepository.findAll().size();

        // Update the pollAnswer
        PollAnswer updatedPollAnswer = pollAnswerRepository.findOne(pollAnswer.getId());
        updatedPollAnswer
            .guid(UPDATED_GUID)
            .answer(UPDATED_ANSWER);

        restPollAnswerMockMvc.perform(put("/api/poll-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPollAnswer)))
            .andExpect(status().isOk());

        // Validate the PollAnswer in the database
        List<PollAnswer> pollAnswerList = pollAnswerRepository.findAll();
        assertThat(pollAnswerList).hasSize(databaseSizeBeforeUpdate);
        PollAnswer testPollAnswer = pollAnswerList.get(pollAnswerList.size() - 1);
        assertThat(testPollAnswer.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testPollAnswer.getAnswer()).isEqualTo(UPDATED_ANSWER);

        // Validate the PollAnswer in Elasticsearch
        PollAnswer pollAnswerEs = pollAnswerSearchRepository.findOne(testPollAnswer.getId());
        assertThat(pollAnswerEs).isEqualToComparingFieldByField(testPollAnswer);
    }

    @Test
    @Transactional
    public void updateNonExistingPollAnswer() throws Exception {
        int databaseSizeBeforeUpdate = pollAnswerRepository.findAll().size();

        // Create the PollAnswer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPollAnswerMockMvc.perform(put("/api/poll-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pollAnswer)))
            .andExpect(status().isCreated());

        // Validate the PollAnswer in the database
        List<PollAnswer> pollAnswerList = pollAnswerRepository.findAll();
        assertThat(pollAnswerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePollAnswer() throws Exception {
        // Initialize the database
        pollAnswerRepository.saveAndFlush(pollAnswer);
        pollAnswerSearchRepository.save(pollAnswer);
        int databaseSizeBeforeDelete = pollAnswerRepository.findAll().size();

        // Get the pollAnswer
        restPollAnswerMockMvc.perform(delete("/api/poll-answers/{id}", pollAnswer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean pollAnswerExistsInEs = pollAnswerSearchRepository.exists(pollAnswer.getId());
        assertThat(pollAnswerExistsInEs).isFalse();

        // Validate the database is empty
        List<PollAnswer> pollAnswerList = pollAnswerRepository.findAll();
        assertThat(pollAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPollAnswer() throws Exception {
        // Initialize the database
        pollAnswerRepository.saveAndFlush(pollAnswer);
        pollAnswerSearchRepository.save(pollAnswer);

        // Search the pollAnswer
        restPollAnswerMockMvc.perform(get("/api/_search/poll-answers?query=id:" + pollAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pollAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID.toString())))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PollAnswer.class);
        PollAnswer pollAnswer1 = new PollAnswer();
        pollAnswer1.setId(1L);
        PollAnswer pollAnswer2 = new PollAnswer();
        pollAnswer2.setId(pollAnswer1.getId());
        assertThat(pollAnswer1).isEqualTo(pollAnswer2);
        pollAnswer2.setId(2L);
        assertThat(pollAnswer1).isNotEqualTo(pollAnswer2);
        pollAnswer1.setId(null);
        assertThat(pollAnswer1).isNotEqualTo(pollAnswer2);
    }
}
