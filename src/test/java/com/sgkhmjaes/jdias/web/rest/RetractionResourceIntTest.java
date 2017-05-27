package com.sgkhmjaes.jdias.web.rest;

import com.sgkhmjaes.jdias.JDiasApp;

import com.sgkhmjaes.jdias.domain.Retraction;
import com.sgkhmjaes.jdias.repository.RetractionRepository;
import com.sgkhmjaes.jdias.repository.search.RetractionSearchRepository;
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

import com.sgkhmjaes.jdias.domain.enumeration.Type;
/**
 * Test class for the RetractionResource REST controller.
 *
 * @see RetractionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JDiasApp.class)
public class RetractionResourceIntTest {

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET_GUID = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_GUID = "BBBBBBBBBB";

    private static final Type DEFAULT_TARGET_TYPE = Type.ACCOUNTDELETION;
    private static final Type UPDATED_TARGET_TYPE = Type.COMMENT;

    @Autowired
    private RetractionRepository retractionRepository;

    @Autowired
    private RetractionSearchRepository retractionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRetractionMockMvc;

    private Retraction retraction;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RetractionResource retractionResource = new RetractionResource(retractionRepository, retractionSearchRepository);
        this.restRetractionMockMvc = MockMvcBuilders.standaloneSetup(retractionResource)
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
    public static Retraction createEntity(EntityManager em) {
        Retraction retraction = new Retraction()
            .author(DEFAULT_AUTHOR)
            .targetGuid(DEFAULT_TARGET_GUID)
            .targetType(DEFAULT_TARGET_TYPE);
        return retraction;
    }

    @Before
    public void initTest() {
        retractionSearchRepository.deleteAll();
        retraction = createEntity(em);
    }

    @Test
    @Transactional
    public void createRetraction() throws Exception {
        int databaseSizeBeforeCreate = retractionRepository.findAll().size();

        // Create the Retraction
        restRetractionMockMvc.perform(post("/api/retractions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(retraction)))
            .andExpect(status().isCreated());

        // Validate the Retraction in the database
        List<Retraction> retractionList = retractionRepository.findAll();
        assertThat(retractionList).hasSize(databaseSizeBeforeCreate + 1);
        Retraction testRetraction = retractionList.get(retractionList.size() - 1);
        assertThat(testRetraction.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testRetraction.getTargetGuid()).isEqualTo(DEFAULT_TARGET_GUID);
        assertThat(testRetraction.getTargetType()).isEqualTo(DEFAULT_TARGET_TYPE);

        // Validate the Retraction in Elasticsearch
        Retraction retractionEs = retractionSearchRepository.findOne(testRetraction.getId());
        assertThat(retractionEs).isEqualToComparingFieldByField(testRetraction);
    }

    @Test
    @Transactional
    public void createRetractionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = retractionRepository.findAll().size();

        // Create the Retraction with an existing ID
        retraction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRetractionMockMvc.perform(post("/api/retractions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(retraction)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Retraction> retractionList = retractionRepository.findAll();
        assertThat(retractionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRetractions() throws Exception {
        // Initialize the database
        retractionRepository.saveAndFlush(retraction);

        // Get all the retractionList
        restRetractionMockMvc.perform(get("/api/retractions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(retraction.getId().intValue())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].targetGuid").value(hasItem(DEFAULT_TARGET_GUID.toString())))
            .andExpect(jsonPath("$.[*].targetType").value(hasItem(DEFAULT_TARGET_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getRetraction() throws Exception {
        // Initialize the database
        retractionRepository.saveAndFlush(retraction);

        // Get the retraction
        restRetractionMockMvc.perform(get("/api/retractions/{id}", retraction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(retraction.getId().intValue()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.targetGuid").value(DEFAULT_TARGET_GUID.toString()))
            .andExpect(jsonPath("$.targetType").value(DEFAULT_TARGET_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRetraction() throws Exception {
        // Get the retraction
        restRetractionMockMvc.perform(get("/api/retractions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRetraction() throws Exception {
        // Initialize the database
        retractionRepository.saveAndFlush(retraction);
        retractionSearchRepository.save(retraction);
        int databaseSizeBeforeUpdate = retractionRepository.findAll().size();

        // Update the retraction
        Retraction updatedRetraction = retractionRepository.findOne(retraction.getId());
        updatedRetraction
            .author(UPDATED_AUTHOR)
            .targetGuid(UPDATED_TARGET_GUID)
            .targetType(UPDATED_TARGET_TYPE);

        restRetractionMockMvc.perform(put("/api/retractions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRetraction)))
            .andExpect(status().isOk());

        // Validate the Retraction in the database
        List<Retraction> retractionList = retractionRepository.findAll();
        assertThat(retractionList).hasSize(databaseSizeBeforeUpdate);
        Retraction testRetraction = retractionList.get(retractionList.size() - 1);
        assertThat(testRetraction.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testRetraction.getTargetGuid()).isEqualTo(UPDATED_TARGET_GUID);
        assertThat(testRetraction.getTargetType()).isEqualTo(UPDATED_TARGET_TYPE);

        // Validate the Retraction in Elasticsearch
        Retraction retractionEs = retractionSearchRepository.findOne(testRetraction.getId());
        assertThat(retractionEs).isEqualToComparingFieldByField(testRetraction);
    }

    @Test
    @Transactional
    public void updateNonExistingRetraction() throws Exception {
        int databaseSizeBeforeUpdate = retractionRepository.findAll().size();

        // Create the Retraction

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRetractionMockMvc.perform(put("/api/retractions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(retraction)))
            .andExpect(status().isCreated());

        // Validate the Retraction in the database
        List<Retraction> retractionList = retractionRepository.findAll();
        assertThat(retractionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRetraction() throws Exception {
        // Initialize the database
        retractionRepository.saveAndFlush(retraction);
        retractionSearchRepository.save(retraction);
        int databaseSizeBeforeDelete = retractionRepository.findAll().size();

        // Get the retraction
        restRetractionMockMvc.perform(delete("/api/retractions/{id}", retraction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean retractionExistsInEs = retractionSearchRepository.exists(retraction.getId());
        assertThat(retractionExistsInEs).isFalse();

        // Validate the database is empty
        List<Retraction> retractionList = retractionRepository.findAll();
        assertThat(retractionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRetraction() throws Exception {
        // Initialize the database
        retractionRepository.saveAndFlush(retraction);
        retractionSearchRepository.save(retraction);

        // Search the retraction
        restRetractionMockMvc.perform(get("/api/_search/retractions?query=id:" + retraction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(retraction.getId().intValue())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].targetGuid").value(hasItem(DEFAULT_TARGET_GUID.toString())))
            .andExpect(jsonPath("$.[*].targetType").value(hasItem(DEFAULT_TARGET_TYPE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Retraction.class);
        Retraction retraction1 = new Retraction();
        retraction1.setId(1L);
        Retraction retraction2 = new Retraction();
        retraction2.setId(retraction1.getId());
        assertThat(retraction1).isEqualTo(retraction2);
        retraction2.setId(2L);
        assertThat(retraction1).isNotEqualTo(retraction2);
        retraction1.setId(null);
        assertThat(retraction1).isNotEqualTo(retraction2);
    }
}
