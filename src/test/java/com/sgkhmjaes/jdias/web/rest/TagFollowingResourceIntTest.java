package com.sgkhmjaes.jdias.web.rest;

import com.sgkhmjaes.jdias.JDiasApp;

import com.sgkhmjaes.jdias.domain.TagFollowing;
import com.sgkhmjaes.jdias.repository.TagFollowingRepository;
import com.sgkhmjaes.jdias.service.TagFollowingService;
import com.sgkhmjaes.jdias.repository.search.TagFollowingSearchRepository;
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
 * Test class for the TagFollowingResource REST controller.
 *
 * @see TagFollowingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JDiasApp.class)
public class TagFollowingResourceIntTest {

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TagFollowingRepository tagFollowingRepository;

    @Autowired
    private TagFollowingService tagFollowingService;

    @Autowired
    private TagFollowingSearchRepository tagFollowingSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTagFollowingMockMvc;

    private TagFollowing tagFollowing;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TagFollowingResource tagFollowingResource = new TagFollowingResource(tagFollowingService);
        this.restTagFollowingMockMvc = MockMvcBuilders.standaloneSetup(tagFollowingResource)
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
    public static TagFollowing createEntity(EntityManager em) {
        TagFollowing tagFollowing = new TagFollowing()
                .createdAt(DEFAULT_CREATED_AT)
                .updatedAt(DEFAULT_UPDATED_AT);
        return tagFollowing;
    }

    @Before
    public void initTest() {
        tagFollowingSearchRepository.deleteAll();
        tagFollowing = createEntity(em);
    }

    @Test
    @Transactional
    public void createTagFollowing() throws Exception {
        int databaseSizeBeforeCreate = tagFollowingRepository.findAll().size();

        // Create the TagFollowing
        restTagFollowingMockMvc.perform(post("/api/tag-followings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tagFollowing)))
                .andExpect(status().isCreated());

        // Validate the TagFollowing in the database
        List<TagFollowing> tagFollowingList = tagFollowingRepository.findAll();
        assertThat(tagFollowingList).hasSize(databaseSizeBeforeCreate + 1);
        TagFollowing testTagFollowing = tagFollowingList.get(tagFollowingList.size() - 1);
        assertThat(testTagFollowing.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testTagFollowing.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);

        // Validate the TagFollowing in Elasticsearch
        TagFollowing tagFollowingEs = tagFollowingSearchRepository.findOne(testTagFollowing.getId());
        assertThat(tagFollowingEs).isEqualToComparingFieldByField(testTagFollowing);
    }

    @Test
    @Transactional
    public void createTagFollowingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tagFollowingRepository.findAll().size();

        // Create the TagFollowing with an existing ID
        tagFollowing.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTagFollowingMockMvc.perform(post("/api/tag-followings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tagFollowing)))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TagFollowing> tagFollowingList = tagFollowingRepository.findAll();
        assertThat(tagFollowingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTagFollowings() throws Exception {
        // Initialize the database
        tagFollowingRepository.saveAndFlush(tagFollowing);

        // Get all the tagFollowingList
        restTagFollowingMockMvc.perform(get("/api/tag-followings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tagFollowing.getId().intValue())))
                .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
                .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    public void getTagFollowing() throws Exception {
        // Initialize the database
        tagFollowingRepository.saveAndFlush(tagFollowing);

        // Get the tagFollowing
        restTagFollowingMockMvc.perform(get("/api/tag-followings/{id}", tagFollowing.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(tagFollowing.getId().intValue()))
                .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
                .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTagFollowing() throws Exception {
        // Get the tagFollowing
        restTagFollowingMockMvc.perform(get("/api/tag-followings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTagFollowing() throws Exception {
        // Initialize the database
        tagFollowingService.save(tagFollowing);

        int databaseSizeBeforeUpdate = tagFollowingRepository.findAll().size();

        // Update the tagFollowing
        TagFollowing updatedTagFollowing = tagFollowingRepository.findOne(tagFollowing.getId());
        updatedTagFollowing
                .createdAt(UPDATED_CREATED_AT)
                .updatedAt(UPDATED_UPDATED_AT);

        restTagFollowingMockMvc.perform(put("/api/tag-followings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTagFollowing)))
                .andExpect(status().isOk());

        // Validate the TagFollowing in the database
        List<TagFollowing> tagFollowingList = tagFollowingRepository.findAll();
        assertThat(tagFollowingList).hasSize(databaseSizeBeforeUpdate);
        TagFollowing testTagFollowing = tagFollowingList.get(tagFollowingList.size() - 1);
        assertThat(testTagFollowing.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTagFollowing.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);

        // Validate the TagFollowing in Elasticsearch
        TagFollowing tagFollowingEs = tagFollowingSearchRepository.findOne(testTagFollowing.getId());
        assertThat(tagFollowingEs).isEqualToComparingFieldByField(testTagFollowing);
    }

    @Test
    @Transactional
    public void updateNonExistingTagFollowing() throws Exception {
        int databaseSizeBeforeUpdate = tagFollowingRepository.findAll().size();

        // Create the TagFollowing
        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTagFollowingMockMvc.perform(put("/api/tag-followings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tagFollowing)))
                .andExpect(status().isCreated());

        // Validate the TagFollowing in the database
        List<TagFollowing> tagFollowingList = tagFollowingRepository.findAll();
        assertThat(tagFollowingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTagFollowing() throws Exception {
        // Initialize the database
        tagFollowingService.save(tagFollowing);

        int databaseSizeBeforeDelete = tagFollowingRepository.findAll().size();

        // Get the tagFollowing
        restTagFollowingMockMvc.perform(delete("/api/tag-followings/{id}", tagFollowing.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean tagFollowingExistsInEs = tagFollowingSearchRepository.exists(tagFollowing.getId());
        assertThat(tagFollowingExistsInEs).isFalse();

        // Validate the database is empty
        List<TagFollowing> tagFollowingList = tagFollowingRepository.findAll();
        assertThat(tagFollowingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTagFollowing() throws Exception {
        // Initialize the database
        tagFollowingService.save(tagFollowing);

        // Search the tagFollowing
        restTagFollowingMockMvc.perform(get("/api/_search/tag-followings?query=id:" + tagFollowing.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tagFollowing.getId().intValue())))
                .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
                .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TagFollowing.class);
        TagFollowing tagFollowing1 = new TagFollowing();
        tagFollowing1.setId(1L);
        TagFollowing tagFollowing2 = new TagFollowing();
        tagFollowing2.setId(tagFollowing1.getId());
        assertThat(tagFollowing1).isEqualTo(tagFollowing2);
        tagFollowing2.setId(2L);
        assertThat(tagFollowing1).isNotEqualTo(tagFollowing2);
        tagFollowing1.setId(null);
        assertThat(tagFollowing1).isNotEqualTo(tagFollowing2);
    }
}
