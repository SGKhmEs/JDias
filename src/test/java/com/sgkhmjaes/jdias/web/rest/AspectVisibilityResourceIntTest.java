package com.sgkhmjaes.jdias.web.rest;

import com.sgkhmjaes.jdias.JDiasApp;

import com.sgkhmjaes.jdias.domain.Aspectvisibility;
import com.sgkhmjaes.jdias.repository.AspectvisibilityRepository;
import com.sgkhmjaes.jdias.service.AspectvisibilityService;
import com.sgkhmjaes.jdias.repository.search.AspectvisibilitySearchRepository;
import com.sgkhmjaes.jdias.service.dto.AspectvisibilityDTO;
import com.sgkhmjaes.jdias.service.mapper.AspectvisibilityMapper;
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
 * Test class for the AspectvisibilityResource REST controller.
 *
 * @see AspectvisibilityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JDiasApp.class)
public class AspectvisibilityResourceIntTest {

    @Autowired
    private AspectvisibilityRepository aspectvisibilityRepository;

    @Autowired
    private AspectvisibilityMapper aspectvisibilityMapper;

    @Autowired
    private AspectvisibilityService aspectvisibilityService;

    @Autowired
    private AspectvisibilitySearchRepository aspectvisibilitySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAspectvisibilityMockMvc;

    private Aspectvisibility aspectvisibility;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AspectvisibilityResource aspectvisibilityResource = new AspectvisibilityResource(aspectvisibilityService);
        this.restAspectvisibilityMockMvc = MockMvcBuilders.standaloneSetup(aspectvisibilityResource)
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
    public static Aspectvisibility createEntity(EntityManager em) {
        Aspectvisibility aspectvisibility = new Aspectvisibility();
        return aspectvisibility;
    }

    @Before
    public void initTest() {
        aspectvisibilitySearchRepository.deleteAll();
        aspectvisibility = createEntity(em);
    }

    @Test
    @Transactional
    public void createAspectvisibility() throws Exception {
        int databaseSizeBeforeCreate = aspectvisibilityRepository.findAll().size();

        // Create the Aspectvisibility
        AspectvisibilityDTO aspectvisibilityDTO = aspectvisibilityMapper.toDto(aspectvisibility);
        restAspectvisibilityMockMvc.perform(post("/api/aspectvisibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aspectvisibilityDTO)))
            .andExpect(status().isCreated());

        // Validate the Aspectvisibility in the database
        List<Aspectvisibility> aspectvisibilityList = aspectvisibilityRepository.findAll();
        assertThat(aspectvisibilityList).hasSize(databaseSizeBeforeCreate + 1);
        Aspectvisibility testAspectvisibility = aspectvisibilityList.get(aspectvisibilityList.size() - 1);

        // Validate the Aspectvisibility in Elasticsearch
        Aspectvisibility aspectvisibilityEs = aspectvisibilitySearchRepository.findOne(testAspectvisibility.getId());
        assertThat(aspectvisibilityEs).isEqualToComparingFieldByField(testAspectvisibility);
    }

    @Test
    @Transactional
    public void createAspectvisibilityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aspectvisibilityRepository.findAll().size();

        // Create the Aspectvisibility with an existing ID
        aspectvisibility.setId(1L);
        AspectvisibilityDTO aspectvisibilityDTO = aspectvisibilityMapper.toDto(aspectvisibility);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAspectvisibilityMockMvc.perform(post("/api/aspectvisibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aspectvisibilityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Aspectvisibility> aspectvisibilityList = aspectvisibilityRepository.findAll();
        assertThat(aspectvisibilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAspectvisibilities() throws Exception {
        // Initialize the database
        aspectvisibilityRepository.saveAndFlush(aspectvisibility);

        // Get all the aspectvisibilityList
        restAspectvisibilityMockMvc.perform(get("/api/aspectvisibilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aspectvisibility.getId().intValue())));
    }

    @Test
    @Transactional
    public void getAspectvisibility() throws Exception {
        // Initialize the database
        aspectvisibilityRepository.saveAndFlush(aspectvisibility);

        // Get the aspectvisibility
        restAspectvisibilityMockMvc.perform(get("/api/aspectvisibilities/{id}", aspectvisibility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aspectvisibility.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAspectvisibility() throws Exception {
        // Get the aspectvisibility
        restAspectvisibilityMockMvc.perform(get("/api/aspectvisibilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAspectvisibility() throws Exception {
        // Initialize the database
        aspectvisibilityRepository.saveAndFlush(aspectvisibility);
        aspectvisibilitySearchRepository.save(aspectvisibility);
        int databaseSizeBeforeUpdate = aspectvisibilityRepository.findAll().size();

        // Update the aspectvisibility
        Aspectvisibility updatedAspectvisibility = aspectvisibilityRepository.findOne(aspectvisibility.getId());
        AspectvisibilityDTO aspectvisibilityDTO = aspectvisibilityMapper.toDto(updatedAspectvisibility);

        restAspectvisibilityMockMvc.perform(put("/api/aspectvisibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aspectvisibilityDTO)))
            .andExpect(status().isOk());

        // Validate the Aspectvisibility in the database
        List<Aspectvisibility> aspectvisibilityList = aspectvisibilityRepository.findAll();
        assertThat(aspectvisibilityList).hasSize(databaseSizeBeforeUpdate);
        Aspectvisibility testAspectvisibility = aspectvisibilityList.get(aspectvisibilityList.size() - 1);

        // Validate the Aspectvisibility in Elasticsearch
        Aspectvisibility aspectvisibilityEs = aspectvisibilitySearchRepository.findOne(testAspectvisibility.getId());
        assertThat(aspectvisibilityEs).isEqualToComparingFieldByField(testAspectvisibility);
    }

    @Test
    @Transactional
    public void updateNonExistingAspectvisibility() throws Exception {
        int databaseSizeBeforeUpdate = aspectvisibilityRepository.findAll().size();

        // Create the Aspectvisibility
        AspectvisibilityDTO aspectvisibilityDTO = aspectvisibilityMapper.toDto(aspectvisibility);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAspectvisibilityMockMvc.perform(put("/api/aspectvisibilities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aspectvisibilityDTO)))
            .andExpect(status().isCreated());

        // Validate the Aspectvisibility in the database
        List<Aspectvisibility> aspectvisibilityList = aspectvisibilityRepository.findAll();
        assertThat(aspectvisibilityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAspectvisibility() throws Exception {
        // Initialize the database
        aspectvisibilityRepository.saveAndFlush(aspectvisibility);
        aspectvisibilitySearchRepository.save(aspectvisibility);
        int databaseSizeBeforeDelete = aspectvisibilityRepository.findAll().size();

        // Get the aspectvisibility
        restAspectvisibilityMockMvc.perform(delete("/api/aspectvisibilities/{id}", aspectvisibility.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean aspectvisibilityExistsInEs = aspectvisibilitySearchRepository.exists(aspectvisibility.getId());
        assertThat(aspectvisibilityExistsInEs).isFalse();

        // Validate the database is empty
        List<Aspectvisibility> aspectvisibilityList = aspectvisibilityRepository.findAll();
        assertThat(aspectvisibilityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAspectvisibility() throws Exception {
        // Initialize the database
        aspectvisibilityRepository.saveAndFlush(aspectvisibility);
        aspectvisibilitySearchRepository.save(aspectvisibility);

        // Search the aspectvisibility
        restAspectvisibilityMockMvc.perform(get("/api/_search/aspectvisibilities?query=id:" + aspectvisibility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aspectvisibility.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aspectvisibility.class);
        Aspectvisibility aspectvisibility1 = new Aspectvisibility();
        aspectvisibility1.setId(1L);
        Aspectvisibility aspectvisibility2 = new Aspectvisibility();
        aspectvisibility2.setId(aspectvisibility1.getId());
        assertThat(aspectvisibility1).isEqualTo(aspectvisibility2);
        aspectvisibility2.setId(2L);
        assertThat(aspectvisibility1).isNotEqualTo(aspectvisibility2);
        aspectvisibility1.setId(null);
        assertThat(aspectvisibility1).isNotEqualTo(aspectvisibility2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AspectvisibilityDTO.class);
        AspectvisibilityDTO aspectvisibilityDTO1 = new AspectvisibilityDTO();
        aspectvisibilityDTO1.setId(1L);
        AspectvisibilityDTO aspectvisibilityDTO2 = new AspectvisibilityDTO();
        assertThat(aspectvisibilityDTO1).isNotEqualTo(aspectvisibilityDTO2);
        aspectvisibilityDTO2.setId(aspectvisibilityDTO1.getId());
        assertThat(aspectvisibilityDTO1).isEqualTo(aspectvisibilityDTO2);
        aspectvisibilityDTO2.setId(2L);
        assertThat(aspectvisibilityDTO1).isNotEqualTo(aspectvisibilityDTO2);
        aspectvisibilityDTO1.setId(null);
        assertThat(aspectvisibilityDTO1).isNotEqualTo(aspectvisibilityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(aspectvisibilityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(aspectvisibilityMapper.fromId(null)).isNull();
    }
}
