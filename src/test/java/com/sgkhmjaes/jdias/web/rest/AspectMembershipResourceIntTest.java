package com.sgkhmjaes.jdias.web.rest;

import com.sgkhmjaes.jdias.JDiasApp;

import com.sgkhmjaes.jdias.domain.AspectMembership;
import com.sgkhmjaes.jdias.repository.AspectMembershipRepository;
import com.sgkhmjaes.jdias.service.AspectMembershipService;
import com.sgkhmjaes.jdias.repository.search.AspectMembershipSearchRepository;
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
 * Test class for the AspectMembershipResource REST controller.
 *
 * @see AspectMembershipResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JDiasApp.class)
public class AspectMembershipResourceIntTest {

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private AspectMembershipRepository aspectMembershipRepository;

    @Autowired
    private AspectMembershipService aspectMembershipService;

    @Autowired
    private AspectMembershipSearchRepository aspectMembershipSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAspectMembershipMockMvc;

    private AspectMembership aspectMembership;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AspectMembershipResource aspectMembershipResource = new AspectMembershipResource(aspectMembershipService);
        this.restAspectMembershipMockMvc = MockMvcBuilders.standaloneSetup(aspectMembershipResource)
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
    public static AspectMembership createEntity(EntityManager em) {
        AspectMembership aspectMembership = new AspectMembership()
                .createdAt(DEFAULT_CREATED_AT)
                .updatedAt(DEFAULT_UPDATED_AT);
        return aspectMembership;
    }

    @Before
    public void initTest() {
        aspectMembershipSearchRepository.deleteAll();
        aspectMembership = createEntity(em);
    }

    @Test
    @Transactional
    public void createAspectMembership() throws Exception {
        int databaseSizeBeforeCreate = aspectMembershipRepository.findAll().size();

        // Create the AspectMembership
        restAspectMembershipMockMvc.perform(post("/api/aspect-memberships")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aspectMembership)))
                .andExpect(status().isCreated());

        // Validate the AspectMembership in the database
        List<AspectMembership> aspectMembershipList = aspectMembershipRepository.findAll();
        assertThat(aspectMembershipList).hasSize(databaseSizeBeforeCreate + 1);
        AspectMembership testAspectMembership = aspectMembershipList.get(aspectMembershipList.size() - 1);
        assertThat(testAspectMembership.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testAspectMembership.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);

        // Validate the AspectMembership in Elasticsearch
        AspectMembership aspectMembershipEs = aspectMembershipSearchRepository.findOne(testAspectMembership.getId());
        assertThat(aspectMembershipEs).isEqualToComparingFieldByField(testAspectMembership);
    }

    @Test
    @Transactional
    public void createAspectMembershipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aspectMembershipRepository.findAll().size();

        // Create the AspectMembership with an existing ID
        aspectMembership.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAspectMembershipMockMvc.perform(post("/api/aspect-memberships")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aspectMembership)))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AspectMembership> aspectMembershipList = aspectMembershipRepository.findAll();
        assertThat(aspectMembershipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAspectMemberships() throws Exception {
        // Initialize the database
        aspectMembershipRepository.saveAndFlush(aspectMembership);

        // Get all the aspectMembershipList
        restAspectMembershipMockMvc.perform(get("/api/aspect-memberships?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(aspectMembership.getId().intValue())))
                .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
                .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    public void getAspectMembership() throws Exception {
        // Initialize the database
        aspectMembershipRepository.saveAndFlush(aspectMembership);

        // Get the aspectMembership
        restAspectMembershipMockMvc.perform(get("/api/aspect-memberships/{id}", aspectMembership.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(aspectMembership.getId().intValue()))
                .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
                .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAspectMembership() throws Exception {
        // Get the aspectMembership
        restAspectMembershipMockMvc.perform(get("/api/aspect-memberships/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAspectMembership() throws Exception {
        // Initialize the database
        aspectMembershipService.save(aspectMembership);

        int databaseSizeBeforeUpdate = aspectMembershipRepository.findAll().size();

        // Update the aspectMembership
        AspectMembership updatedAspectMembership = aspectMembershipRepository.findOne(aspectMembership.getId());
        updatedAspectMembership
                .createdAt(UPDATED_CREATED_AT)
                .updatedAt(UPDATED_UPDATED_AT);

        restAspectMembershipMockMvc.perform(put("/api/aspect-memberships")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAspectMembership)))
                .andExpect(status().isOk());

        // Validate the AspectMembership in the database
        List<AspectMembership> aspectMembershipList = aspectMembershipRepository.findAll();
        assertThat(aspectMembershipList).hasSize(databaseSizeBeforeUpdate);
        AspectMembership testAspectMembership = aspectMembershipList.get(aspectMembershipList.size() - 1);
        assertThat(testAspectMembership.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAspectMembership.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);

        // Validate the AspectMembership in Elasticsearch
        AspectMembership aspectMembershipEs = aspectMembershipSearchRepository.findOne(testAspectMembership.getId());
        assertThat(aspectMembershipEs).isEqualToComparingFieldByField(testAspectMembership);
    }

    @Test
    @Transactional
    public void updateNonExistingAspectMembership() throws Exception {
        int databaseSizeBeforeUpdate = aspectMembershipRepository.findAll().size();

        // Create the AspectMembership
        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAspectMembershipMockMvc.perform(put("/api/aspect-memberships")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aspectMembership)))
                .andExpect(status().isCreated());

        // Validate the AspectMembership in the database
        List<AspectMembership> aspectMembershipList = aspectMembershipRepository.findAll();
        assertThat(aspectMembershipList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAspectMembership() throws Exception {
        // Initialize the database
        aspectMembershipService.save(aspectMembership);

        int databaseSizeBeforeDelete = aspectMembershipRepository.findAll().size();

        // Get the aspectMembership
        restAspectMembershipMockMvc.perform(delete("/api/aspect-memberships/{id}", aspectMembership.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean aspectMembershipExistsInEs = aspectMembershipSearchRepository.exists(aspectMembership.getId());
        assertThat(aspectMembershipExistsInEs).isFalse();

        // Validate the database is empty
        List<AspectMembership> aspectMembershipList = aspectMembershipRepository.findAll();
        assertThat(aspectMembershipList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAspectMembership() throws Exception {
        // Initialize the database
        aspectMembershipService.save(aspectMembership);

        // Search the aspectMembership
        restAspectMembershipMockMvc.perform(get("/api/_search/aspect-memberships?query=id:" + aspectMembership.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(aspectMembership.getId().intValue())))
                .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
                .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AspectMembership.class);
        AspectMembership aspectMembership1 = new AspectMembership();
        aspectMembership1.setId(1L);
        AspectMembership aspectMembership2 = new AspectMembership();
        aspectMembership2.setId(aspectMembership1.getId());
        assertThat(aspectMembership1).isEqualTo(aspectMembership2);
        aspectMembership2.setId(2L);
        assertThat(aspectMembership1).isNotEqualTo(aspectMembership2);
        aspectMembership1.setId(null);
        assertThat(aspectMembership1).isNotEqualTo(aspectMembership2);
    }
}
