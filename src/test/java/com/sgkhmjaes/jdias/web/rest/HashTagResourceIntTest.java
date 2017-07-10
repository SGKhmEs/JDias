package com.sgkhmjaes.jdias.web.rest;

import com.sgkhmjaes.jdias.JDiasApp;

import com.sgkhmjaes.jdias.domain.HashTag;
import com.sgkhmjaes.jdias.repository.HashTagRepository;
import com.sgkhmjaes.jdias.service.HashTagService;
import com.sgkhmjaes.jdias.repository.search.HashTagSearchRepository;
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
 * Test class for the HashTagResource REST controller.
 *
 * @see HashTagResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JDiasApp.class)
public class HashTagResourceIntTest {

    @Autowired
    private HashTagRepository hashTagRepository;

    @Autowired
    private HashTagService hashTagService;

    @Autowired
    private HashTagSearchRepository hashTagSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHashTagMockMvc;

    private HashTag hashTag;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HashTagResource hashTagResource = new HashTagResource(hashTagService);
        this.restHashTagMockMvc = MockMvcBuilders.standaloneSetup(hashTagResource)
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
    public static HashTag createEntity(EntityManager em) {
        HashTag hashTag = new HashTag();
        return hashTag;
    }

    @Before
    public void initTest() {
        hashTagSearchRepository.deleteAll();
        hashTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createHashTag() throws Exception {
        int databaseSizeBeforeCreate = hashTagRepository.findAll().size();

        // Create the HashTag
        restHashTagMockMvc.perform(post("/api/hash-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hashTag)))
            .andExpect(status().isCreated());

        // Validate the HashTag in the database
        List<HashTag> hashTagList = hashTagRepository.findAll();
        assertThat(hashTagList).hasSize(databaseSizeBeforeCreate + 1);
        HashTag testHashTag = hashTagList.get(hashTagList.size() - 1);

        // Validate the HashTag in Elasticsearch
        HashTag hashTagEs = hashTagSearchRepository.findOne(testHashTag.getId());
        assertThat(hashTagEs).isEqualToComparingFieldByField(testHashTag);
    }

    @Test
    @Transactional
    public void createHashTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hashTagRepository.findAll().size();

        // Create the HashTag with an existing ID
        hashTag.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHashTagMockMvc.perform(post("/api/hash-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hashTag)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<HashTag> hashTagList = hashTagRepository.findAll();
        assertThat(hashTagList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHashTags() throws Exception {
        // Initialize the database
        hashTagRepository.saveAndFlush(hashTag);

        // Get all the hashTagList
        restHashTagMockMvc.perform(get("/api/hash-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hashTag.getId().intValue())));
    }

    @Test
    @Transactional
    public void getHashTag() throws Exception {
        // Initialize the database
        hashTagRepository.saveAndFlush(hashTag);

        // Get the hashTag
        restHashTagMockMvc.perform(get("/api/hash-tags/{id}", hashTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hashTag.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHashTag() throws Exception {
        // Get the hashTag
        restHashTagMockMvc.perform(get("/api/hash-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHashTag() throws Exception {
        // Initialize the database
        hashTagService.save(hashTag);

        int databaseSizeBeforeUpdate = hashTagRepository.findAll().size();

        // Update the hashTag
        HashTag updatedHashTag = hashTagRepository.findOne(hashTag.getId());

        restHashTagMockMvc.perform(put("/api/hash-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHashTag)))
            .andExpect(status().isOk());

        // Validate the HashTag in the database
        List<HashTag> hashTagList = hashTagRepository.findAll();
        assertThat(hashTagList).hasSize(databaseSizeBeforeUpdate);
        HashTag testHashTag = hashTagList.get(hashTagList.size() - 1);

        // Validate the HashTag in Elasticsearch
        HashTag hashTagEs = hashTagSearchRepository.findOne(testHashTag.getId());
        assertThat(hashTagEs).isEqualToComparingFieldByField(testHashTag);
    }

    @Test
    @Transactional
    public void updateNonExistingHashTag() throws Exception {
        int databaseSizeBeforeUpdate = hashTagRepository.findAll().size();

        // Create the HashTag

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHashTagMockMvc.perform(put("/api/hash-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hashTag)))
            .andExpect(status().isCreated());

        // Validate the HashTag in the database
        List<HashTag> hashTagList = hashTagRepository.findAll();
        assertThat(hashTagList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHashTag() throws Exception {
        // Initialize the database
        hashTagService.save(hashTag);

        int databaseSizeBeforeDelete = hashTagRepository.findAll().size();

        // Get the hashTag
        restHashTagMockMvc.perform(delete("/api/hash-tags/{id}", hashTag.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean hashTagExistsInEs = hashTagSearchRepository.exists(hashTag.getId());
        assertThat(hashTagExistsInEs).isFalse();

        // Validate the database is empty
        List<HashTag> hashTagList = hashTagRepository.findAll();
        assertThat(hashTagList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHashTag() throws Exception {
        // Initialize the database
        hashTagService.save(hashTag);

        // Search the hashTag
        restHashTagMockMvc.perform(get("/api/_search/hash-tags?query=id:" + hashTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hashTag.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HashTag.class);
        HashTag hashTag1 = new HashTag();
        hashTag1.setId(1L);
        HashTag hashTag2 = new HashTag();
        hashTag2.setId(hashTag1.getId());
        assertThat(hashTag1).isEqualTo(hashTag2);
        hashTag2.setId(2L);
        assertThat(hashTag1).isNotEqualTo(hashTag2);
        hashTag1.setId(null);
        assertThat(hashTag1).isNotEqualTo(hashTag2);
    }
}
