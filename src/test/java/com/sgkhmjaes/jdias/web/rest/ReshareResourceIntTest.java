package com.sgkhmjaes.jdias.web.rest;

import com.sgkhmjaes.jdias.JDiasApp;

import com.sgkhmjaes.jdias.domain.Reshare;
import com.sgkhmjaes.jdias.repository.ReshareRepository;
import com.sgkhmjaes.jdias.repository.search.ReshareSearchRepository;
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
 * Test class for the ReshareResource REST controller.
 *
 * @see ReshareResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JDiasApp.class)
public class ReshareResourceIntTest {

    private static final String DEFAULT_ROOT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_ROOT_AUTHOR = "BBBBBBBBBB";

    private static final String DEFAULT_ROOT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_ROOT_GUID = "BBBBBBBBBB";

    @Autowired
    private ReshareRepository reshareRepository;

    @Autowired
    private ReshareSearchRepository reshareSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReshareMockMvc;

    private Reshare reshare;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReshareResource reshareResource = new ReshareResource(reshareRepository, reshareSearchRepository);
        this.restReshareMockMvc = MockMvcBuilders.standaloneSetup(reshareResource)
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
    public static Reshare createEntity(EntityManager em) {
        Reshare reshare = new Reshare()
            .rootAuthor(DEFAULT_ROOT_AUTHOR)
            .rootGuid(DEFAULT_ROOT_GUID);
        return reshare;
    }

    @Before
    public void initTest() {
        reshareSearchRepository.deleteAll();
        reshare = createEntity(em);
    }

    @Test
    @Transactional
    public void createReshare() throws Exception {
        int databaseSizeBeforeCreate = reshareRepository.findAll().size();

        // Create the Reshare
        restReshareMockMvc.perform(post("/api/reshares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reshare)))
            .andExpect(status().isCreated());

        // Validate the Reshare in the database
        List<Reshare> reshareList = reshareRepository.findAll();
        assertThat(reshareList).hasSize(databaseSizeBeforeCreate + 1);
        Reshare testReshare = reshareList.get(reshareList.size() - 1);
        assertThat(testReshare.getRootAuthor()).isEqualTo(DEFAULT_ROOT_AUTHOR);
        assertThat(testReshare.getRootGuid()).isEqualTo(DEFAULT_ROOT_GUID);

        // Validate the Reshare in Elasticsearch
        Reshare reshareEs = reshareSearchRepository.findOne(testReshare.getId());
        assertThat(reshareEs).isEqualToComparingFieldByField(testReshare);
    }

    @Test
    @Transactional
    public void createReshareWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reshareRepository.findAll().size();

        // Create the Reshare with an existing ID
        reshare.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReshareMockMvc.perform(post("/api/reshares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reshare)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Reshare> reshareList = reshareRepository.findAll();
        assertThat(reshareList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReshares() throws Exception {
        // Initialize the database
        reshareRepository.saveAndFlush(reshare);

        // Get all the reshareList
        restReshareMockMvc.perform(get("/api/reshares?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reshare.getId().intValue())))
            .andExpect(jsonPath("$.[*].rootAuthor").value(hasItem(DEFAULT_ROOT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].rootGuid").value(hasItem(DEFAULT_ROOT_GUID.toString())));
    }

    @Test
    @Transactional
    public void getReshare() throws Exception {
        // Initialize the database
        reshareRepository.saveAndFlush(reshare);

        // Get the reshare
        restReshareMockMvc.perform(get("/api/reshares/{id}", reshare.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reshare.getId().intValue()))
            .andExpect(jsonPath("$.rootAuthor").value(DEFAULT_ROOT_AUTHOR.toString()))
            .andExpect(jsonPath("$.rootGuid").value(DEFAULT_ROOT_GUID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReshare() throws Exception {
        // Get the reshare
        restReshareMockMvc.perform(get("/api/reshares/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReshare() throws Exception {
        // Initialize the database
        reshareRepository.saveAndFlush(reshare);
        reshareSearchRepository.save(reshare);
        int databaseSizeBeforeUpdate = reshareRepository.findAll().size();

        // Update the reshare
        Reshare updatedReshare = reshareRepository.findOne(reshare.getId());
        updatedReshare
            .rootAuthor(UPDATED_ROOT_AUTHOR)
            .rootGuid(UPDATED_ROOT_GUID);

        restReshareMockMvc.perform(put("/api/reshares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReshare)))
            .andExpect(status().isOk());

        // Validate the Reshare in the database
        List<Reshare> reshareList = reshareRepository.findAll();
        assertThat(reshareList).hasSize(databaseSizeBeforeUpdate);
        Reshare testReshare = reshareList.get(reshareList.size() - 1);
        assertThat(testReshare.getRootAuthor()).isEqualTo(UPDATED_ROOT_AUTHOR);
        assertThat(testReshare.getRootGuid()).isEqualTo(UPDATED_ROOT_GUID);

        // Validate the Reshare in Elasticsearch
        Reshare reshareEs = reshareSearchRepository.findOne(testReshare.getId());
        assertThat(reshareEs).isEqualToComparingFieldByField(testReshare);
    }

    @Test
    @Transactional
    public void updateNonExistingReshare() throws Exception {
        int databaseSizeBeforeUpdate = reshareRepository.findAll().size();

        // Create the Reshare

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReshareMockMvc.perform(put("/api/reshares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reshare)))
            .andExpect(status().isCreated());

        // Validate the Reshare in the database
        List<Reshare> reshareList = reshareRepository.findAll();
        assertThat(reshareList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReshare() throws Exception {
        // Initialize the database
        reshareRepository.saveAndFlush(reshare);
        reshareSearchRepository.save(reshare);
        int databaseSizeBeforeDelete = reshareRepository.findAll().size();

        // Get the reshare
        restReshareMockMvc.perform(delete("/api/reshares/{id}", reshare.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean reshareExistsInEs = reshareSearchRepository.exists(reshare.getId());
        assertThat(reshareExistsInEs).isFalse();

        // Validate the database is empty
        List<Reshare> reshareList = reshareRepository.findAll();
        assertThat(reshareList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchReshare() throws Exception {
        // Initialize the database
        reshareRepository.saveAndFlush(reshare);
        reshareSearchRepository.save(reshare);

        // Search the reshare
        restReshareMockMvc.perform(get("/api/_search/reshares?query=id:" + reshare.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reshare.getId().intValue())))
            .andExpect(jsonPath("$.[*].rootAuthor").value(hasItem(DEFAULT_ROOT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].rootGuid").value(hasItem(DEFAULT_ROOT_GUID.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reshare.class);
        Reshare reshare1 = new Reshare();
        reshare1.setId(1L);
        Reshare reshare2 = new Reshare();
        reshare2.setId(reshare1.getId());
        assertThat(reshare1).isEqualTo(reshare2);
        reshare2.setId(2L);
        assertThat(reshare1).isNotEqualTo(reshare2);
        reshare1.setId(null);
        assertThat(reshare1).isNotEqualTo(reshare2);
    }
}
