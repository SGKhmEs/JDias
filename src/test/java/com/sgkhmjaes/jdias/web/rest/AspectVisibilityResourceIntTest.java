package com.sgkhmjaes.jdias.web.rest;

import com.sgkhmjaes.jdias.JDiasApp;

import com.sgkhmjaes.jdias.domain.AspectVisibility;
import com.sgkhmjaes.jdias.repository.AspectVisibilityRepository;
import com.sgkhmjaes.jdias.service.AspectVisibilityService;
import com.sgkhmjaes.jdias.repository.search.AspectVisibilitySearchRepository;
import com.sgkhmjaes.jdias.service.dto.AspectVisibilityDTO;
import com.sgkhmjaes.jdias.service.mapper.AspectVisibilityMapper;
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
 * Test class for the AspectVisibilityResource REST controller.
 *
 * @see AspectVisibilityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JDiasApp.class)
public class AspectVisibilityResourceIntTest {

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private AspectVisibilityRepository aspectVisibilityRepository;

    @Autowired
    private AspectVisibilityMapper aspectVisibilityMapper;

    @Autowired
    private AspectVisibilityService aspectVisibilityService;

    @Autowired
    private AspectVisibilitySearchRepository aspectVisibilitySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAspectVisibilityMockMvc;

    private AspectVisibility aspectVisibility;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AspectVisibilityResource aspectVisibilityResource = new AspectVisibilityResource(aspectVisibilityService);
        this.restAspectVisibilityMockMvc = MockMvcBuilders.standaloneSetup(aspectVisibilityResource)
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
    public static AspectVisibility createEntity(EntityManager em) {
        AspectVisibility aspectVisibility = new AspectVisibility()
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return aspectVisibility;
    }

    @Before
    public void initTest() {
        aspectVisibilitySearchRepository.deleteAll();
        aspectVisibility = createEntity(em);
    }

    @Test
    @Transactional
    public void createAspectVisibility() throws Exception {
        int databaseSizeBeforeCreate = aspectVisibilityRepository.findAll().size();

        // Create the AspectVisibility
        AspectVisibilityDTO aspectVisibilityDTO = aspectVisibilityMapper.toDto(aspectVisibility);
        restAspectVisibilityMockMvc.perform(post("/api/aspect-visibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aspectVisibilityDTO)))
            .andExpect(status().isCreated());

        // Validate the AspectVisibility in the database
        List<AspectVisibility> aspectVisibilityList = aspectVisibilityRepository.findAll();
        assertThat(aspectVisibilityList).hasSize(databaseSizeBeforeCreate + 1);
        AspectVisibility testAspectVisibility = aspectVisibilityList.get(aspectVisibilityList.size() - 1);
        assertThat(testAspectVisibility.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testAspectVisibility.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);

        // Validate the AspectVisibility in Elasticsearch
        AspectVisibility aspectVisibilityEs = aspectVisibilitySearchRepository.findOne(testAspectVisibility.getId());
        assertThat(aspectVisibilityEs).isEqualToComparingFieldByField(testAspectVisibility);
    }

    @Test
    @Transactional
    public void createAspectVisibilityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aspectVisibilityRepository.findAll().size();

        // Create the AspectVisibility with an existing ID
        aspectVisibility.setId(1L);
        AspectVisibilityDTO aspectVisibilityDTO = aspectVisibilityMapper.toDto(aspectVisibility);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAspectVisibilityMockMvc.perform(post("/api/aspect-visibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aspectVisibilityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AspectVisibility> aspectVisibilityList = aspectVisibilityRepository.findAll();
        assertThat(aspectVisibilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAspectVisibilities() throws Exception {
        // Initialize the database
        aspectVisibilityRepository.saveAndFlush(aspectVisibility);

        // Get all the aspectVisibilityList
        restAspectVisibilityMockMvc.perform(get("/api/aspect-visibilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aspectVisibility.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    public void getAspectVisibility() throws Exception {
        // Initialize the database
        aspectVisibilityRepository.saveAndFlush(aspectVisibility);

        // Get the aspectVisibility
        restAspectVisibilityMockMvc.perform(get("/api/aspect-visibilities/{id}", aspectVisibility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aspectVisibility.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAspectVisibility() throws Exception {
        // Get the aspectVisibility
        restAspectVisibilityMockMvc.perform(get("/api/aspect-visibilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAspectVisibility() throws Exception {
        // Initialize the database
        aspectVisibilityRepository.saveAndFlush(aspectVisibility);
        aspectVisibilitySearchRepository.save(aspectVisibility);
        int databaseSizeBeforeUpdate = aspectVisibilityRepository.findAll().size();

        // Update the aspectVisibility
        AspectVisibility updatedAspectVisibility = aspectVisibilityRepository.findOne(aspectVisibility.getId());
        updatedAspectVisibility
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        AspectVisibilityDTO aspectVisibilityDTO = aspectVisibilityMapper.toDto(updatedAspectVisibility);

        restAspectVisibilityMockMvc.perform(put("/api/aspect-visibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aspectVisibilityDTO)))
            .andExpect(status().isOk());

        // Validate the AspectVisibility in the database
        List<AspectVisibility> aspectVisibilityList = aspectVisibilityRepository.findAll();
        assertThat(aspectVisibilityList).hasSize(databaseSizeBeforeUpdate);
        AspectVisibility testAspectVisibility = aspectVisibilityList.get(aspectVisibilityList.size() - 1);
        assertThat(testAspectVisibility.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAspectVisibility.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);

        // Validate the AspectVisibility in Elasticsearch
        AspectVisibility aspectVisibilityEs = aspectVisibilitySearchRepository.findOne(testAspectVisibility.getId());
        assertThat(aspectVisibilityEs).isEqualToComparingFieldByField(testAspectVisibility);
    }

    @Test
    @Transactional
    public void updateNonExistingAspectVisibility() throws Exception {
        int databaseSizeBeforeUpdate = aspectVisibilityRepository.findAll().size();

        // Create the AspectVisibility
        AspectVisibilityDTO aspectVisibilityDTO = aspectVisibilityMapper.toDto(aspectVisibility);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAspectVisibilityMockMvc.perform(put("/api/aspect-visibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aspectVisibilityDTO)))
            .andExpect(status().isCreated());

        // Validate the AspectVisibility in the database
        List<AspectVisibility> aspectVisibilityList = aspectVisibilityRepository.findAll();
        assertThat(aspectVisibilityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAspectVisibility() throws Exception {
        // Initialize the database
        aspectVisibilityRepository.saveAndFlush(aspectVisibility);
        aspectVisibilitySearchRepository.save(aspectVisibility);
        int databaseSizeBeforeDelete = aspectVisibilityRepository.findAll().size();

        // Get the aspectVisibility
        restAspectVisibilityMockMvc.perform(delete("/api/aspect-visibilities/{id}", aspectVisibility.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean aspectVisibilityExistsInEs = aspectVisibilitySearchRepository.exists(aspectVisibility.getId());
        assertThat(aspectVisibilityExistsInEs).isFalse();

        // Validate the database is empty
        List<AspectVisibility> aspectVisibilityList = aspectVisibilityRepository.findAll();
        assertThat(aspectVisibilityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAspectVisibility() throws Exception {
        // Initialize the database
        aspectVisibilityRepository.saveAndFlush(aspectVisibility);
        aspectVisibilitySearchRepository.save(aspectVisibility);

        // Search the aspectVisibility
        restAspectVisibilityMockMvc.perform(get("/api/_search/aspect-visibilities?query=id:" + aspectVisibility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aspectVisibility.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AspectVisibility.class);
        AspectVisibility aspectVisibility1 = new AspectVisibility();
        aspectVisibility1.setId(1L);
        AspectVisibility aspectVisibility2 = new AspectVisibility();
        aspectVisibility2.setId(aspectVisibility1.getId());
        assertThat(aspectVisibility1).isEqualTo(aspectVisibility2);
        aspectVisibility2.setId(2L);
        assertThat(aspectVisibility1).isNotEqualTo(aspectVisibility2);
        aspectVisibility1.setId(null);
        assertThat(aspectVisibility1).isNotEqualTo(aspectVisibility2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AspectVisibilityDTO.class);
        AspectVisibilityDTO aspectVisibilityDTO1 = new AspectVisibilityDTO();
        aspectVisibilityDTO1.setId(1L);
        AspectVisibilityDTO aspectVisibilityDTO2 = new AspectVisibilityDTO();
        assertThat(aspectVisibilityDTO1).isNotEqualTo(aspectVisibilityDTO2);
        aspectVisibilityDTO2.setId(aspectVisibilityDTO1.getId());
        assertThat(aspectVisibilityDTO1).isEqualTo(aspectVisibilityDTO2);
        aspectVisibilityDTO2.setId(2L);
        assertThat(aspectVisibilityDTO1).isNotEqualTo(aspectVisibilityDTO2);
        aspectVisibilityDTO1.setId(null);
        assertThat(aspectVisibilityDTO1).isNotEqualTo(aspectVisibilityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(aspectVisibilityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(aspectVisibilityMapper.fromId(null)).isNull();
    }
}
