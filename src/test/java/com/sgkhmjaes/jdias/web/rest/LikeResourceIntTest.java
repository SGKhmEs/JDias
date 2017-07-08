package com.sgkhmjaes.jdias.web.rest;

import com.sgkhmjaes.jdias.JDiasApp;

import com.sgkhmjaes.jdias.domain.Like;
import com.sgkhmjaes.jdias.repository.LikeRepository;
import com.sgkhmjaes.jdias.service.LikeService;
import com.sgkhmjaes.jdias.repository.search.LikeSearchRepository;
import com.sgkhmjaes.jdias.service.impl.LikeDTOServiceImpl;
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
 * Test class for the LikeResource REST controller.
 *
 * @see LikeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JDiasApp.class)
public class LikeResourceIntTest {

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_GUID = "BBBBBBBBBB";

    private static final Type DEFAULT_PARENT_TYPE = Type.ACCOUNTDELETION;
    private static final Type UPDATED_PARENT_TYPE = Type.COMMENT;

    private static final Boolean DEFAULT_POSITIVE = false;
    private static final Boolean UPDATED_POSITIVE = true;

    private static final String DEFAULT_AUTHOR_SIGNATURE = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR_SIGNATURE = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_AUTHOR_SIGNATURE = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_AUTHOR_SIGNATURE = "BBBBBBBBBB";

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private LikeService likeService;
    @Autowired
    private LikeDTOServiceImpl likeDTOService;
    @Autowired
    private LikeSearchRepository likeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLikeMockMvc;

    private Like like;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LikeResource likeResource = new LikeResource(likeService);
        this.restLikeMockMvc = MockMvcBuilders.standaloneSetup(likeResource)
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
    public static Like createEntity(EntityManager em) {
        Like like = new Like()
            .author(DEFAULT_AUTHOR)
            .guid(DEFAULT_GUID)
            .parentGuid(DEFAULT_PARENT_GUID)
            .parentType(DEFAULT_PARENT_TYPE)
            .positive(DEFAULT_POSITIVE)
            .authorSignature(DEFAULT_AUTHOR_SIGNATURE)
            .parentAuthorSignature(DEFAULT_PARENT_AUTHOR_SIGNATURE);
        return like;
    }

    @Before
    public void initTest() {
        likeSearchRepository.deleteAll();
        like = createEntity(em);
    }

    @Test
    @Transactional
    public void createLike() throws Exception {
        int databaseSizeBeforeCreate = likeRepository.findAll().size();

        // Create the Like
        restLikeMockMvc.perform(post("/api/likes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(like)))
            .andExpect(status().isCreated());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeCreate + 1);
        Like testLike = likeList.get(likeList.size() - 1);
        assertThat(testLike.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testLike.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testLike.getParentGuid()).isEqualTo(DEFAULT_PARENT_GUID);
        assertThat(testLike.getParentType()).isEqualTo(DEFAULT_PARENT_TYPE);
        assertThat(testLike.isPositive()).isEqualTo(DEFAULT_POSITIVE);
        assertThat(testLike.getAuthorSignature()).isEqualTo(DEFAULT_AUTHOR_SIGNATURE);
        assertThat(testLike.getParentAuthorSignature()).isEqualTo(DEFAULT_PARENT_AUTHOR_SIGNATURE);

        // Validate the Like in Elasticsearch
        Like likeEs = likeSearchRepository.findOne(testLike.getId());
        assertThat(likeEs).isEqualToComparingFieldByField(testLike);
    }

    @Test
    @Transactional
    public void createLikeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = likeRepository.findAll().size();

        // Create the Like with an existing ID
        like.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLikeMockMvc.perform(post("/api/likes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(like)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLikes() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get all the likeList
        restLikeMockMvc.perform(get("/api/likes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(like.getId().intValue())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID.toString())))
            .andExpect(jsonPath("$.[*].parentGuid").value(hasItem(DEFAULT_PARENT_GUID.toString())))
            .andExpect(jsonPath("$.[*].parentType").value(hasItem(DEFAULT_PARENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].positive").value(hasItem(DEFAULT_POSITIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].authorSignature").value(hasItem(DEFAULT_AUTHOR_SIGNATURE.toString())))
            .andExpect(jsonPath("$.[*].parentAuthorSignature").value(hasItem(DEFAULT_PARENT_AUTHOR_SIGNATURE.toString())));
    }

    @Test
    @Transactional
    public void getLike() throws Exception {
        // Initialize the database
        likeRepository.saveAndFlush(like);

        // Get the like
        restLikeMockMvc.perform(get("/api/likes/{id}", like.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(like.getId().intValue()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID.toString()))
            .andExpect(jsonPath("$.parentGuid").value(DEFAULT_PARENT_GUID.toString()))
            .andExpect(jsonPath("$.parentType").value(DEFAULT_PARENT_TYPE.toString()))
            .andExpect(jsonPath("$.positive").value(DEFAULT_POSITIVE.booleanValue()))
            .andExpect(jsonPath("$.authorSignature").value(DEFAULT_AUTHOR_SIGNATURE.toString()))
            .andExpect(jsonPath("$.parentAuthorSignature").value(DEFAULT_PARENT_AUTHOR_SIGNATURE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLike() throws Exception {
        // Get the like
        restLikeMockMvc.perform(get("/api/likes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLike() throws Exception {
        // Initialize the database
        likeService.save(like);

        int databaseSizeBeforeUpdate = likeRepository.findAll().size();

        // Update the like
        Like updatedLike = likeRepository.findOne(like.getId());
        updatedLike
            .author(UPDATED_AUTHOR)
            .guid(UPDATED_GUID)
            .parentGuid(UPDATED_PARENT_GUID)
            .parentType(UPDATED_PARENT_TYPE)
            .positive(UPDATED_POSITIVE)
            .authorSignature(UPDATED_AUTHOR_SIGNATURE)
            .parentAuthorSignature(UPDATED_PARENT_AUTHOR_SIGNATURE);

        restLikeMockMvc.perform(put("/api/likes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLike)))
            .andExpect(status().isOk());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeUpdate);
        Like testLike = likeList.get(likeList.size() - 1);
        assertThat(testLike.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testLike.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testLike.getParentGuid()).isEqualTo(UPDATED_PARENT_GUID);
        assertThat(testLike.getParentType()).isEqualTo(UPDATED_PARENT_TYPE);
        assertThat(testLike.isPositive()).isEqualTo(UPDATED_POSITIVE);
        assertThat(testLike.getAuthorSignature()).isEqualTo(UPDATED_AUTHOR_SIGNATURE);
        assertThat(testLike.getParentAuthorSignature()).isEqualTo(UPDATED_PARENT_AUTHOR_SIGNATURE);

        // Validate the Like in Elasticsearch
        Like likeEs = likeSearchRepository.findOne(testLike.getId());
        assertThat(likeEs).isEqualToComparingFieldByField(testLike);
    }

    @Test
    @Transactional
    public void updateNonExistingLike() throws Exception {
        int databaseSizeBeforeUpdate = likeRepository.findAll().size();

        // Create the Like

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLikeMockMvc.perform(put("/api/likes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(like)))
            .andExpect(status().isCreated());

        // Validate the Like in the database
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLike() throws Exception {
        // Initialize the database
        likeService.save(like);

        int databaseSizeBeforeDelete = likeRepository.findAll().size();

        // Get the like
        restLikeMockMvc.perform(delete("/api/likes/{id}", like.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean likeExistsInEs = likeSearchRepository.exists(like.getId());
        assertThat(likeExistsInEs).isFalse();

        // Validate the database is empty
        List<Like> likeList = likeRepository.findAll();
        assertThat(likeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLike() throws Exception {
        // Initialize the database
        likeService.save(like);

        // Search the like
        restLikeMockMvc.perform(get("/api/_search/likes?query=id:" + like.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(like.getId().intValue())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID.toString())))
            .andExpect(jsonPath("$.[*].parentGuid").value(hasItem(DEFAULT_PARENT_GUID.toString())))
            .andExpect(jsonPath("$.[*].parentType").value(hasItem(DEFAULT_PARENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].positive").value(hasItem(DEFAULT_POSITIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].authorSignature").value(hasItem(DEFAULT_AUTHOR_SIGNATURE.toString())))
            .andExpect(jsonPath("$.[*].parentAuthorSignature").value(hasItem(DEFAULT_PARENT_AUTHOR_SIGNATURE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Like.class);
        Like like1 = new Like();
        like1.setId(1L);
        Like like2 = new Like();
        like2.setId(like1.getId());
        assertThat(like1).isEqualTo(like2);
        like2.setId(2L);
        assertThat(like1).isNotEqualTo(like2);
        like1.setId(null);
        assertThat(like1).isNotEqualTo(like2);
    }
}
