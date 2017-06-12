package com.sgkhmjaes.jdias.web.rest;

import com.sgkhmjaes.jdias.JDiasApp;

import com.sgkhmjaes.jdias.domain.AspectVisiblity;
import com.sgkhmjaes.jdias.repository.AspectVisiblityRepository;
import com.sgkhmjaes.jdias.service.AspectVisiblityService;
import com.sgkhmjaes.jdias.repository.search.AspectVisiblitySearchRepository;
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

import com.sgkhmjaes.jdias.domain.enumeration.PostType;

/**
 * Test class for the AspectVisiblityResource REST controller.
 *
 * @see AspectVisiblityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JDiasApp.class)
public class AspectVisiblityResourceIntTest {

    private static final PostType DEFAULT_POST_TYPE = PostType.STATUSMESSAGE;
    private static final PostType UPDATED_POST_TYPE = PostType.RESHARE;

    @Autowired
    private AspectVisiblityRepository aspectVisiblityRepository;

    @Autowired
    private AspectVisiblityService aspectVisiblityService;

    @Autowired
    private AspectVisiblitySearchRepository aspectVisiblitySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAspectVisiblityMockMvc;

    private AspectVisiblity aspectVisiblity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AspectVisiblityResource aspectVisiblityResource = new AspectVisiblityResource(aspectVisiblityService);
        this.restAspectVisiblityMockMvc = MockMvcBuilders.standaloneSetup(aspectVisiblityResource)
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
    public static AspectVisiblity createEntity(EntityManager em) {
        AspectVisiblity aspectVisiblity = new AspectVisiblity()
                .postType(DEFAULT_POST_TYPE);
        return aspectVisiblity;
    }

    @Before
    public void initTest() {
        aspectVisiblitySearchRepository.deleteAll();
        aspectVisiblity = createEntity(em);
    }

    @Test
    @Transactional
    public void createAspectVisiblity() throws Exception {
        int databaseSizeBeforeCreate = aspectVisiblityRepository.findAll().size();

        // Create the AspectVisiblity
        restAspectVisiblityMockMvc.perform(post("/api/aspect-visiblities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aspectVisiblity)))
                .andExpect(status().isCreated());

        // Validate the AspectVisiblity in the database
        List<AspectVisiblity> aspectVisiblityList = aspectVisiblityRepository.findAll();
        assertThat(aspectVisiblityList).hasSize(databaseSizeBeforeCreate + 1);
        AspectVisiblity testAspectVisiblity = aspectVisiblityList.get(aspectVisiblityList.size() - 1);
        assertThat(testAspectVisiblity.getPostType()).isEqualTo(DEFAULT_POST_TYPE);

        // Validate the AspectVisiblity in Elasticsearch
        AspectVisiblity aspectVisiblityEs = aspectVisiblitySearchRepository.findOne(testAspectVisiblity.getId());
        assertThat(aspectVisiblityEs).isEqualToComparingFieldByField(testAspectVisiblity);
    }

    @Test
    @Transactional
    public void createAspectVisiblityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aspectVisiblityRepository.findAll().size();

        // Create the AspectVisiblity with an existing ID
        aspectVisiblity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAspectVisiblityMockMvc.perform(post("/api/aspect-visiblities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aspectVisiblity)))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AspectVisiblity> aspectVisiblityList = aspectVisiblityRepository.findAll();
        assertThat(aspectVisiblityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAspectVisiblities() throws Exception {
        // Initialize the database
        aspectVisiblityRepository.saveAndFlush(aspectVisiblity);

        // Get all the aspectVisiblityList
        restAspectVisiblityMockMvc.perform(get("/api/aspect-visiblities?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(aspectVisiblity.getId().intValue())))
                .andExpect(jsonPath("$.[*].postType").value(hasItem(DEFAULT_POST_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getAspectVisiblity() throws Exception {
        // Initialize the database
        aspectVisiblityRepository.saveAndFlush(aspectVisiblity);

        // Get the aspectVisiblity
        restAspectVisiblityMockMvc.perform(get("/api/aspect-visiblities/{id}", aspectVisiblity.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(aspectVisiblity.getId().intValue()))
                .andExpect(jsonPath("$.postType").value(DEFAULT_POST_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAspectVisiblity() throws Exception {
        // Get the aspectVisiblity
        restAspectVisiblityMockMvc.perform(get("/api/aspect-visiblities/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAspectVisiblity() throws Exception {
        // Initialize the database
        aspectVisiblityService.save(aspectVisiblity);

        int databaseSizeBeforeUpdate = aspectVisiblityRepository.findAll().size();

        // Update the aspectVisiblity
        AspectVisiblity updatedAspectVisiblity = aspectVisiblityRepository.findOne(aspectVisiblity.getId());
        updatedAspectVisiblity
                .postType(UPDATED_POST_TYPE);

        restAspectVisiblityMockMvc.perform(put("/api/aspect-visiblities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAspectVisiblity)))
                .andExpect(status().isOk());

        // Validate the AspectVisiblity in the database
        List<AspectVisiblity> aspectVisiblityList = aspectVisiblityRepository.findAll();
        assertThat(aspectVisiblityList).hasSize(databaseSizeBeforeUpdate);
        AspectVisiblity testAspectVisiblity = aspectVisiblityList.get(aspectVisiblityList.size() - 1);
        assertThat(testAspectVisiblity.getPostType()).isEqualTo(UPDATED_POST_TYPE);

        // Validate the AspectVisiblity in Elasticsearch
        AspectVisiblity aspectVisiblityEs = aspectVisiblitySearchRepository.findOne(testAspectVisiblity.getId());
        assertThat(aspectVisiblityEs).isEqualToComparingFieldByField(testAspectVisiblity);
    }

    @Test
    @Transactional
    public void updateNonExistingAspectVisiblity() throws Exception {
        int databaseSizeBeforeUpdate = aspectVisiblityRepository.findAll().size();

        // Create the AspectVisiblity
        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAspectVisiblityMockMvc.perform(put("/api/aspect-visiblities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aspectVisiblity)))
                .andExpect(status().isCreated());

        // Validate the AspectVisiblity in the database
        List<AspectVisiblity> aspectVisiblityList = aspectVisiblityRepository.findAll();
        assertThat(aspectVisiblityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAspectVisiblity() throws Exception {
        // Initialize the database
        aspectVisiblityService.save(aspectVisiblity);

        int databaseSizeBeforeDelete = aspectVisiblityRepository.findAll().size();

        // Get the aspectVisiblity
        restAspectVisiblityMockMvc.perform(delete("/api/aspect-visiblities/{id}", aspectVisiblity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean aspectVisiblityExistsInEs = aspectVisiblitySearchRepository.exists(aspectVisiblity.getId());
        assertThat(aspectVisiblityExistsInEs).isFalse();

        // Validate the database is empty
        List<AspectVisiblity> aspectVisiblityList = aspectVisiblityRepository.findAll();
        assertThat(aspectVisiblityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAspectVisiblity() throws Exception {
        // Initialize the database
        aspectVisiblityService.save(aspectVisiblity);

        // Search the aspectVisiblity
        restAspectVisiblityMockMvc.perform(get("/api/_search/aspect-visiblities?query=id:" + aspectVisiblity.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(aspectVisiblity.getId().intValue())))
                .andExpect(jsonPath("$.[*].postType").value(hasItem(DEFAULT_POST_TYPE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AspectVisiblity.class);
        AspectVisiblity aspectVisiblity1 = new AspectVisiblity();
        aspectVisiblity1.setId(1L);
        AspectVisiblity aspectVisiblity2 = new AspectVisiblity();
        aspectVisiblity2.setId(aspectVisiblity1.getId());
        assertThat(aspectVisiblity1).isEqualTo(aspectVisiblity2);
        aspectVisiblity2.setId(2L);
        assertThat(aspectVisiblity1).isNotEqualTo(aspectVisiblity2);
        aspectVisiblity1.setId(null);
        assertThat(aspectVisiblity1).isNotEqualTo(aspectVisiblity2);
    }
}
