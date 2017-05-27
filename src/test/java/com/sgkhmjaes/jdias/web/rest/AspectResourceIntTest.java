package com.sgkhmjaes.jdias.web.rest;

import com.sgkhmjaes.jdias.JDiasApp;

import com.sgkhmjaes.jdias.domain.Aspect;
import com.sgkhmjaes.jdias.repository.AspectRepository;
import com.sgkhmjaes.jdias.repository.search.AspectSearchRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.sgkhmjaes.jdias.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AspectResource REST controller.
 *
 * @see AspectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JDiasApp.class)
public class AspectResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_CONTACT_VISIBLE = false;
    private static final Boolean UPDATED_CONTACT_VISIBLE = true;

    private static final Boolean DEFAULT_CHAT_ENABLED = false;
    private static final Boolean UPDATED_CHAT_ENABLED = true;

    private static final Boolean DEFAULT_POST_DEFAULT = false;
    private static final Boolean UPDATED_POST_DEFAULT = true;

    @Autowired
    private AspectRepository aspectRepository;

    @Autowired
    private AspectSearchRepository aspectSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAspectMockMvc;

    private Aspect aspect;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AspectResource aspectResource = new AspectResource(aspectRepository, aspectSearchRepository);
        this.restAspectMockMvc = MockMvcBuilders.standaloneSetup(aspectResource)
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
    public static Aspect createEntity(EntityManager em) {
        Aspect aspect = new Aspect()
            .name(DEFAULT_NAME)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .contactVisible(DEFAULT_CONTACT_VISIBLE)
            .chatEnabled(DEFAULT_CHAT_ENABLED)
            .postDefault(DEFAULT_POST_DEFAULT);
        return aspect;
    }

    @Before
    public void initTest() {
        aspectSearchRepository.deleteAll();
        aspect = createEntity(em);
    }

    @Test
    @Transactional
    public void createAspect() throws Exception {
        int databaseSizeBeforeCreate = aspectRepository.findAll().size();

        // Create the Aspect
        restAspectMockMvc.perform(post("/api/aspects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aspect)))
            .andExpect(status().isCreated());

        // Validate the Aspect in the database
        List<Aspect> aspectList = aspectRepository.findAll();
        assertThat(aspectList).hasSize(databaseSizeBeforeCreate + 1);
        Aspect testAspect = aspectList.get(aspectList.size() - 1);
        assertThat(testAspect.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAspect.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testAspect.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testAspect.isContactVisible()).isEqualTo(DEFAULT_CONTACT_VISIBLE);
        assertThat(testAspect.isChatEnabled()).isEqualTo(DEFAULT_CHAT_ENABLED);
        assertThat(testAspect.isPostDefault()).isEqualTo(DEFAULT_POST_DEFAULT);

        // Validate the Aspect in Elasticsearch
        Aspect aspectEs = aspectSearchRepository.findOne(testAspect.getId());
        assertThat(aspectEs).isEqualToComparingFieldByField(testAspect);
    }

    @Test
    @Transactional
    public void createAspectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aspectRepository.findAll().size();

        // Create the Aspect with an existing ID
        aspect.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAspectMockMvc.perform(post("/api/aspects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aspect)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Aspect> aspectList = aspectRepository.findAll();
        assertThat(aspectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAspects() throws Exception {
        // Initialize the database
        aspectRepository.saveAndFlush(aspect);

        // Get all the aspectList
        restAspectMockMvc.perform(get("/api/aspects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aspect.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].contactVisible").value(hasItem(DEFAULT_CONTACT_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].chatEnabled").value(hasItem(DEFAULT_CHAT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].postDefault").value(hasItem(DEFAULT_POST_DEFAULT.booleanValue())));
    }

    @Test
    @Transactional
    public void getAspect() throws Exception {
        // Initialize the database
        aspectRepository.saveAndFlush(aspect);

        // Get the aspect
        restAspectMockMvc.perform(get("/api/aspects/{id}", aspect.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aspect.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.contactVisible").value(DEFAULT_CONTACT_VISIBLE.booleanValue()))
            .andExpect(jsonPath("$.chatEnabled").value(DEFAULT_CHAT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.postDefault").value(DEFAULT_POST_DEFAULT.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAspect() throws Exception {
        // Get the aspect
        restAspectMockMvc.perform(get("/api/aspects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAspect() throws Exception {
        // Initialize the database
        aspectRepository.saveAndFlush(aspect);
        aspectSearchRepository.save(aspect);
        int databaseSizeBeforeUpdate = aspectRepository.findAll().size();

        // Update the aspect
        Aspect updatedAspect = aspectRepository.findOne(aspect.getId());
        updatedAspect
            .name(UPDATED_NAME)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .contactVisible(UPDATED_CONTACT_VISIBLE)
            .chatEnabled(UPDATED_CHAT_ENABLED)
            .postDefault(UPDATED_POST_DEFAULT);

        restAspectMockMvc.perform(put("/api/aspects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAspect)))
            .andExpect(status().isOk());

        // Validate the Aspect in the database
        List<Aspect> aspectList = aspectRepository.findAll();
        assertThat(aspectList).hasSize(databaseSizeBeforeUpdate);
        Aspect testAspect = aspectList.get(aspectList.size() - 1);
        assertThat(testAspect.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAspect.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAspect.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testAspect.isContactVisible()).isEqualTo(UPDATED_CONTACT_VISIBLE);
        assertThat(testAspect.isChatEnabled()).isEqualTo(UPDATED_CHAT_ENABLED);
        assertThat(testAspect.isPostDefault()).isEqualTo(UPDATED_POST_DEFAULT);

        // Validate the Aspect in Elasticsearch
        Aspect aspectEs = aspectSearchRepository.findOne(testAspect.getId());
        assertThat(aspectEs).isEqualToComparingFieldByField(testAspect);
    }

    @Test
    @Transactional
    public void updateNonExistingAspect() throws Exception {
        int databaseSizeBeforeUpdate = aspectRepository.findAll().size();

        // Create the Aspect

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAspectMockMvc.perform(put("/api/aspects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aspect)))
            .andExpect(status().isCreated());

        // Validate the Aspect in the database
        List<Aspect> aspectList = aspectRepository.findAll();
        assertThat(aspectList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAspect() throws Exception {
        // Initialize the database
        aspectRepository.saveAndFlush(aspect);
        aspectSearchRepository.save(aspect);
        int databaseSizeBeforeDelete = aspectRepository.findAll().size();

        // Get the aspect
        restAspectMockMvc.perform(delete("/api/aspects/{id}", aspect.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean aspectExistsInEs = aspectSearchRepository.exists(aspect.getId());
        assertThat(aspectExistsInEs).isFalse();

        // Validate the database is empty
        List<Aspect> aspectList = aspectRepository.findAll();
        assertThat(aspectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAspect() throws Exception {
        // Initialize the database
        aspectRepository.saveAndFlush(aspect);
        aspectSearchRepository.save(aspect);

        // Search the aspect
        restAspectMockMvc.perform(get("/api/_search/aspects?query=id:" + aspect.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aspect.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].contactVisible").value(hasItem(DEFAULT_CONTACT_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].chatEnabled").value(hasItem(DEFAULT_CHAT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].postDefault").value(hasItem(DEFAULT_POST_DEFAULT.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aspect.class);
        Aspect aspect1 = new Aspect();
        aspect1.setId(1L);
        Aspect aspect2 = new Aspect();
        aspect2.setId(aspect1.getId());
        assertThat(aspect1).isEqualTo(aspect2);
        aspect2.setId(2L);
        assertThat(aspect1).isNotEqualTo(aspect2);
        aspect1.setId(null);
        assertThat(aspect1).isNotEqualTo(aspect2);
    }
}
