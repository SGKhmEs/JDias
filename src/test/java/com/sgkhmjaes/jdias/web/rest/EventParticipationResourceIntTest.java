package com.sgkhmjaes.jdias.web.rest;

import com.sgkhmjaes.jdias.JDiasApp;

import com.sgkhmjaes.jdias.domain.EventParticipation;
import com.sgkhmjaes.jdias.repository.EventParticipationRepository;
import com.sgkhmjaes.jdias.repository.search.EventParticipationSearchRepository;
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

import com.sgkhmjaes.jdias.domain.enumeration.EventStatus;
/**
 * Test class for the EventParticipationResource REST controller.
 *
 * @see EventParticipationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JDiasApp.class)
public class EventParticipationResourceIntTest {

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_GUID = "BBBBBBBBBB";

    private static final EventStatus DEFAULT_STATUS = EventStatus.ACCEPTED;
    private static final EventStatus UPDATED_STATUS = EventStatus.DECLINED;

    private static final String DEFAULT_AUTHOR_SIGNATURE = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR_SIGNATURE = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_AUTHOR_SIGNATURE = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_AUTHOR_SIGNATURE = "BBBBBBBBBB";

    @Autowired
    private EventParticipationRepository eventParticipationRepository;

    @Autowired
    private EventParticipationSearchRepository eventParticipationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEventParticipationMockMvc;

    private EventParticipation eventParticipation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EventParticipationResource eventParticipationResource = new EventParticipationResource(eventParticipationRepository, eventParticipationSearchRepository);
        this.restEventParticipationMockMvc = MockMvcBuilders.standaloneSetup(eventParticipationResource)
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
    public static EventParticipation createEntity(EntityManager em) {
        EventParticipation eventParticipation = new EventParticipation()
            .author(DEFAULT_AUTHOR)
            .guid(DEFAULT_GUID)
            .parentGuid(DEFAULT_PARENT_GUID)
            .status(DEFAULT_STATUS)
            .authorSignature(DEFAULT_AUTHOR_SIGNATURE)
            .parentAuthorSignature(DEFAULT_PARENT_AUTHOR_SIGNATURE);
        return eventParticipation;
    }

    @Before
    public void initTest() {
        eventParticipationSearchRepository.deleteAll();
        eventParticipation = createEntity(em);
    }

    @Test
    @Transactional
    public void createEventParticipation() throws Exception {
        int databaseSizeBeforeCreate = eventParticipationRepository.findAll().size();

        // Create the EventParticipation
        restEventParticipationMockMvc.perform(post("/api/event-participations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventParticipation)))
            .andExpect(status().isCreated());

        // Validate the EventParticipation in the database
        List<EventParticipation> eventParticipationList = eventParticipationRepository.findAll();
        assertThat(eventParticipationList).hasSize(databaseSizeBeforeCreate + 1);
        EventParticipation testEventParticipation = eventParticipationList.get(eventParticipationList.size() - 1);
        assertThat(testEventParticipation.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testEventParticipation.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testEventParticipation.getParentGuid()).isEqualTo(DEFAULT_PARENT_GUID);
        assertThat(testEventParticipation.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEventParticipation.getAuthorSignature()).isEqualTo(DEFAULT_AUTHOR_SIGNATURE);
        assertThat(testEventParticipation.getParentAuthorSignature()).isEqualTo(DEFAULT_PARENT_AUTHOR_SIGNATURE);

        // Validate the EventParticipation in Elasticsearch
        EventParticipation eventParticipationEs = eventParticipationSearchRepository.findOne(testEventParticipation.getId());
        assertThat(eventParticipationEs).isEqualToComparingFieldByField(testEventParticipation);
    }

    @Test
    @Transactional
    public void createEventParticipationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = eventParticipationRepository.findAll().size();

        // Create the EventParticipation with an existing ID
        eventParticipation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventParticipationMockMvc.perform(post("/api/event-participations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventParticipation)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<EventParticipation> eventParticipationList = eventParticipationRepository.findAll();
        assertThat(eventParticipationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEventParticipations() throws Exception {
        // Initialize the database
        eventParticipationRepository.saveAndFlush(eventParticipation);

        // Get all the eventParticipationList
        restEventParticipationMockMvc.perform(get("/api/event-participations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventParticipation.getId().intValue())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID.toString())))
            .andExpect(jsonPath("$.[*].parentGuid").value(hasItem(DEFAULT_PARENT_GUID.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].authorSignature").value(hasItem(DEFAULT_AUTHOR_SIGNATURE.toString())))
            .andExpect(jsonPath("$.[*].parentAuthorSignature").value(hasItem(DEFAULT_PARENT_AUTHOR_SIGNATURE.toString())));
    }

    @Test
    @Transactional
    public void getEventParticipation() throws Exception {
        // Initialize the database
        eventParticipationRepository.saveAndFlush(eventParticipation);

        // Get the eventParticipation
        restEventParticipationMockMvc.perform(get("/api/event-participations/{id}", eventParticipation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(eventParticipation.getId().intValue()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID.toString()))
            .andExpect(jsonPath("$.parentGuid").value(DEFAULT_PARENT_GUID.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.authorSignature").value(DEFAULT_AUTHOR_SIGNATURE.toString()))
            .andExpect(jsonPath("$.parentAuthorSignature").value(DEFAULT_PARENT_AUTHOR_SIGNATURE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEventParticipation() throws Exception {
        // Get the eventParticipation
        restEventParticipationMockMvc.perform(get("/api/event-participations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEventParticipation() throws Exception {
        // Initialize the database
        eventParticipationRepository.saveAndFlush(eventParticipation);
        eventParticipationSearchRepository.save(eventParticipation);
        int databaseSizeBeforeUpdate = eventParticipationRepository.findAll().size();

        // Update the eventParticipation
        EventParticipation updatedEventParticipation = eventParticipationRepository.findOne(eventParticipation.getId());
        updatedEventParticipation
            .author(UPDATED_AUTHOR)
            .guid(UPDATED_GUID)
            .parentGuid(UPDATED_PARENT_GUID)
            .status(UPDATED_STATUS)
            .authorSignature(UPDATED_AUTHOR_SIGNATURE)
            .parentAuthorSignature(UPDATED_PARENT_AUTHOR_SIGNATURE);

        restEventParticipationMockMvc.perform(put("/api/event-participations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEventParticipation)))
            .andExpect(status().isOk());

        // Validate the EventParticipation in the database
        List<EventParticipation> eventParticipationList = eventParticipationRepository.findAll();
        assertThat(eventParticipationList).hasSize(databaseSizeBeforeUpdate);
        EventParticipation testEventParticipation = eventParticipationList.get(eventParticipationList.size() - 1);
        assertThat(testEventParticipation.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testEventParticipation.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testEventParticipation.getParentGuid()).isEqualTo(UPDATED_PARENT_GUID);
        assertThat(testEventParticipation.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEventParticipation.getAuthorSignature()).isEqualTo(UPDATED_AUTHOR_SIGNATURE);
        assertThat(testEventParticipation.getParentAuthorSignature()).isEqualTo(UPDATED_PARENT_AUTHOR_SIGNATURE);

        // Validate the EventParticipation in Elasticsearch
        EventParticipation eventParticipationEs = eventParticipationSearchRepository.findOne(testEventParticipation.getId());
        assertThat(eventParticipationEs).isEqualToComparingFieldByField(testEventParticipation);
    }

    @Test
    @Transactional
    public void updateNonExistingEventParticipation() throws Exception {
        int databaseSizeBeforeUpdate = eventParticipationRepository.findAll().size();

        // Create the EventParticipation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEventParticipationMockMvc.perform(put("/api/event-participations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(eventParticipation)))
            .andExpect(status().isCreated());

        // Validate the EventParticipation in the database
        List<EventParticipation> eventParticipationList = eventParticipationRepository.findAll();
        assertThat(eventParticipationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEventParticipation() throws Exception {
        // Initialize the database
        eventParticipationRepository.saveAndFlush(eventParticipation);
        eventParticipationSearchRepository.save(eventParticipation);
        int databaseSizeBeforeDelete = eventParticipationRepository.findAll().size();

        // Get the eventParticipation
        restEventParticipationMockMvc.perform(delete("/api/event-participations/{id}", eventParticipation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean eventParticipationExistsInEs = eventParticipationSearchRepository.exists(eventParticipation.getId());
        assertThat(eventParticipationExistsInEs).isFalse();

        // Validate the database is empty
        List<EventParticipation> eventParticipationList = eventParticipationRepository.findAll();
        assertThat(eventParticipationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEventParticipation() throws Exception {
        // Initialize the database
        eventParticipationRepository.saveAndFlush(eventParticipation);
        eventParticipationSearchRepository.save(eventParticipation);

        // Search the eventParticipation
        restEventParticipationMockMvc.perform(get("/api/_search/event-participations?query=id:" + eventParticipation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventParticipation.getId().intValue())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID.toString())))
            .andExpect(jsonPath("$.[*].parentGuid").value(hasItem(DEFAULT_PARENT_GUID.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].authorSignature").value(hasItem(DEFAULT_AUTHOR_SIGNATURE.toString())))
            .andExpect(jsonPath("$.[*].parentAuthorSignature").value(hasItem(DEFAULT_PARENT_AUTHOR_SIGNATURE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventParticipation.class);
        EventParticipation eventParticipation1 = new EventParticipation();
        eventParticipation1.setId(1L);
        EventParticipation eventParticipation2 = new EventParticipation();
        eventParticipation2.setId(eventParticipation1.getId());
        assertThat(eventParticipation1).isEqualTo(eventParticipation2);
        eventParticipation2.setId(2L);
        assertThat(eventParticipation1).isNotEqualTo(eventParticipation2);
        eventParticipation1.setId(null);
        assertThat(eventParticipation1).isNotEqualTo(eventParticipation2);
    }
}
