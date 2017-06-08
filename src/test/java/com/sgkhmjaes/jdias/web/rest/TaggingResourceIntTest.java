package com.sgkhmjaes.jdias.web.rest;

import com.sgkhmjaes.jdias.JDiasApp;

import com.sgkhmjaes.jdias.domain.Tagging;
import com.sgkhmjaes.jdias.repository.TaggingRepository;
import com.sgkhmjaes.jdias.service.TaggingService;
import com.sgkhmjaes.jdias.repository.search.TaggingSearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TaggingResource REST controller.
 *
 * @see TaggingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JDiasApp.class)
public class TaggingResourceIntTest {

    private static final String DEFAULT_CONTEXT = "AAAAAAAAAA";
    private static final String UPDATED_CONTEXT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TaggingRepository taggingRepository;

    @Autowired
    private TaggingService taggingService;

    @Autowired
    private TaggingSearchRepository taggingSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTaggingMockMvc;

    private Tagging tagging;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TaggingResource taggingResource = new TaggingResource(taggingService);
        this.restTaggingMockMvc = MockMvcBuilders.standaloneSetup(taggingResource)
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
    public static Tagging createEntity(EntityManager em) {
        Tagging tagging = new Tagging()
            .context(DEFAULT_CONTEXT)
            .createdAt(DEFAULT_CREATED_AT);
        return tagging;
    }

    @Before
    public void initTest() {
        taggingSearchRepository.deleteAll();
        tagging = createEntity(em);
    }

    @Test
    @Transactional
    public void createTagging() throws Exception {
        int databaseSizeBeforeCreate = taggingRepository.findAll().size();

        // Create the Tagging
        restTaggingMockMvc.perform(post("/api/taggings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tagging)))
            .andExpect(status().isCreated());

        // Validate the Tagging in the database
        List<Tagging> taggingList = taggingRepository.findAll();
        assertThat(taggingList).hasSize(databaseSizeBeforeCreate + 1);
        Tagging testTagging = taggingList.get(taggingList.size() - 1);
        assertThat(testTagging.getContext()).isEqualTo(DEFAULT_CONTEXT);
        assertThat(testTagging.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);

        // Validate the Tagging in Elasticsearch
        Tagging taggingEs = taggingSearchRepository.findOne(testTagging.getId());
        assertThat(taggingEs).isEqualToComparingFieldByField(testTagging);
    }

    @Test
    @Transactional
    public void createTaggingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taggingRepository.findAll().size();

        // Create the Tagging with an existing ID
        tagging.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaggingMockMvc.perform(post("/api/taggings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tagging)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Tagging> taggingList = taggingRepository.findAll();
        assertThat(taggingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTaggings() throws Exception {
        // Initialize the database
        taggingRepository.saveAndFlush(tagging);

        // Get all the taggingList
        restTaggingMockMvc.perform(get("/api/taggings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tagging.getId().intValue())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    public void getTagging() throws Exception {
        // Initialize the database
        taggingRepository.saveAndFlush(tagging);

        // Get the tagging
        restTaggingMockMvc.perform(get("/api/taggings/{id}", tagging.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tagging.getId().intValue()))
            .andExpect(jsonPath("$.context").value(DEFAULT_CONTEXT.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTagging() throws Exception {
        // Get the tagging
        restTaggingMockMvc.perform(get("/api/taggings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTagging() throws Exception {
        // Initialize the database
        taggingService.save(tagging);

        int databaseSizeBeforeUpdate = taggingRepository.findAll().size();

        // Update the tagging
        Tagging updatedTagging = taggingRepository.findOne(tagging.getId());
        updatedTagging
            .context(UPDATED_CONTEXT)
            .createdAt(UPDATED_CREATED_AT);

        restTaggingMockMvc.perform(put("/api/taggings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTagging)))
            .andExpect(status().isOk());

        // Validate the Tagging in the database
        List<Tagging> taggingList = taggingRepository.findAll();
        assertThat(taggingList).hasSize(databaseSizeBeforeUpdate);
        Tagging testTagging = taggingList.get(taggingList.size() - 1);
        assertThat(testTagging.getContext()).isEqualTo(UPDATED_CONTEXT);
        assertThat(testTagging.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);

        // Validate the Tagging in Elasticsearch
        Tagging taggingEs = taggingSearchRepository.findOne(testTagging.getId());
        assertThat(taggingEs).isEqualToComparingFieldByField(testTagging);
    }

    @Test
    @Transactional
    public void updateNonExistingTagging() throws Exception {
        int databaseSizeBeforeUpdate = taggingRepository.findAll().size();

        // Create the Tagging

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTaggingMockMvc.perform(put("/api/taggings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tagging)))
            .andExpect(status().isCreated());

        // Validate the Tagging in the database
        List<Tagging> taggingList = taggingRepository.findAll();
        assertThat(taggingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTagging() throws Exception {
        // Initialize the database
        taggingService.save(tagging);

        int databaseSizeBeforeDelete = taggingRepository.findAll().size();

        // Get the tagging
        restTaggingMockMvc.perform(delete("/api/taggings/{id}", tagging.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean taggingExistsInEs = taggingSearchRepository.exists(tagging.getId());
        assertThat(taggingExistsInEs).isFalse();

        // Validate the database is empty
        List<Tagging> taggingList = taggingRepository.findAll();
        assertThat(taggingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTagging() throws Exception {
        // Initialize the database
        taggingService.save(tagging);

        // Search the tagging
        restTaggingMockMvc.perform(get("/api/_search/taggings?query=id:" + tagging.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tagging.getId().intValue())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tagging.class);
        Tagging tagging1 = new Tagging();
        tagging1.setId(1L);
        Tagging tagging2 = new Tagging();
        tagging2.setId(tagging1.getId());
        assertThat(tagging1).isEqualTo(tagging2);
        tagging2.setId(2L);
        assertThat(tagging1).isNotEqualTo(tagging2);
        tagging1.setId(null);
        assertThat(tagging1).isNotEqualTo(tagging2);
    }
}
