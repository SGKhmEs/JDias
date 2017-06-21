package com.sgkhmjaes.jdias.web.rest;

import com.sgkhmjaes.jdias.JDiasApp;

import com.sgkhmjaes.jdias.domain.Post;
import com.sgkhmjaes.jdias.repository.PostRepository;
import com.sgkhmjaes.jdias.service.PostService;
import com.sgkhmjaes.jdias.repository.search.PostSearchRepository;
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

import com.sgkhmjaes.jdias.domain.enumeration.PostType;
import com.sgkhmjaes.jdias.service.impl.PostDTOServiceImpl;

/**
 * Test class for the PostResource REST controller.
 *
 * @see PostResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JDiasApp.class)
public class PostResourceIntTest {

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_PUB = false;
    private static final Boolean UPDATED_PUB = true;

    private static final String DEFAULT_PROVIDER_DISPLAY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROVIDER_DISPLAY_NAME = "BBBBBBBBBB";

    private static final PostType DEFAULT_POST_TYPE = PostType.STATUSMESSAGE;
    private static final PostType UPDATED_POST_TYPE = PostType.RESHARE;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;
    
    @Autowired
    private PostDTOServiceImpl postDTOServiceImpl;

    @Autowired
    private PostSearchRepository postSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPostMockMvc;

    private Post post;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PostResource postResource = new PostResource(postService, postDTOServiceImpl);
        this.restPostMockMvc = MockMvcBuilders.standaloneSetup(postResource)
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
    public static Post createEntity(EntityManager em) {
        Post post = new Post()
                .author(DEFAULT_AUTHOR)
                .guid(DEFAULT_GUID)
                .createdAt(DEFAULT_CREATED_AT)
                .pub(DEFAULT_PUB)
                .providerDisplayName(DEFAULT_PROVIDER_DISPLAY_NAME)
                .postType(DEFAULT_POST_TYPE);
        return post;
    }

    @Before
    public void initTest() {
        postSearchRepository.deleteAll();
        post = createEntity(em);
    }

    @Test
    @Transactional
    public void createPost() throws Exception {
        int databaseSizeBeforeCreate = postRepository.findAll().size();

        // Create the Post
        restPostMockMvc.perform(post("/api/posts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(post)))
                .andExpect(status().isCreated());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeCreate + 1);
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testPost.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testPost.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPost.isPub()).isEqualTo(DEFAULT_PUB);
        assertThat(testPost.getProviderDisplayName()).isEqualTo(DEFAULT_PROVIDER_DISPLAY_NAME);
        assertThat(testPost.getPostType()).isEqualTo(DEFAULT_POST_TYPE);

        // Validate the Post in Elasticsearch
        Post postEs = postSearchRepository.findOne(testPost.getId());
        assertThat(postEs).isEqualToComparingFieldByField(testPost);
    }

    @Test
    @Transactional
    public void createPostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postRepository.findAll().size();

        // Create the Post with an existing ID
        post.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostMockMvc.perform(post("/api/posts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(post)))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPosts() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get all the postList
        restPostMockMvc.perform(get("/api/posts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(post.getId().intValue())))
                .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
                .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID.toString())))
                .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
                .andExpect(jsonPath("$.[*].pub").value(hasItem(DEFAULT_PUB.booleanValue())))
                .andExpect(jsonPath("$.[*].providerDisplayName").value(hasItem(DEFAULT_PROVIDER_DISPLAY_NAME.toString())))
                .andExpect(jsonPath("$.[*].postType").value(hasItem(DEFAULT_POST_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getPost() throws Exception {
        // Initialize the database
        postRepository.saveAndFlush(post);

        // Get the post
        restPostMockMvc.perform(get("/api/posts/{id}", post.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(post.getId().intValue()))
                .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
                .andExpect(jsonPath("$.guid").value(DEFAULT_GUID.toString()))
                .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
                .andExpect(jsonPath("$.pub").value(DEFAULT_PUB.booleanValue()))
                .andExpect(jsonPath("$.providerDisplayName").value(DEFAULT_PROVIDER_DISPLAY_NAME.toString()))
                .andExpect(jsonPath("$.postType").value(DEFAULT_POST_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPost() throws Exception {
        // Get the post
        restPostMockMvc.perform(get("/api/posts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePost() throws Exception {
        // Initialize the database
        postService.save(post);

        int databaseSizeBeforeUpdate = postRepository.findAll().size();

        // Update the post
        Post updatedPost = postRepository.findOne(post.getId());
        updatedPost
                .author(UPDATED_AUTHOR)
                .guid(UPDATED_GUID)
                .createdAt(UPDATED_CREATED_AT)
                .pub(UPDATED_PUB)
                .providerDisplayName(UPDATED_PROVIDER_DISPLAY_NAME)
                .postType(UPDATED_POST_TYPE);

        restPostMockMvc.perform(put("/api/posts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPost)))
                .andExpect(status().isOk());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate);
        Post testPost = postList.get(postList.size() - 1);
        assertThat(testPost.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testPost.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testPost.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPost.isPub()).isEqualTo(UPDATED_PUB);
        assertThat(testPost.getProviderDisplayName()).isEqualTo(UPDATED_PROVIDER_DISPLAY_NAME);
        assertThat(testPost.getPostType()).isEqualTo(UPDATED_POST_TYPE);

        // Validate the Post in Elasticsearch
        Post postEs = postSearchRepository.findOne(testPost.getId());
        assertThat(postEs).isEqualToComparingFieldByField(testPost);
    }

    @Test
    @Transactional
    public void updateNonExistingPost() throws Exception {
        int databaseSizeBeforeUpdate = postRepository.findAll().size();

        // Create the Post
        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPostMockMvc.perform(put("/api/posts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(post)))
                .andExpect(status().isCreated());

        // Validate the Post in the database
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePost() throws Exception {
        // Initialize the database
        postService.save(post);

        int databaseSizeBeforeDelete = postRepository.findAll().size();

        // Get the post
        restPostMockMvc.perform(delete("/api/posts/{id}", post.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean postExistsInEs = postSearchRepository.exists(post.getId());
        assertThat(postExistsInEs).isFalse();

        // Validate the database is empty
        List<Post> postList = postRepository.findAll();
        assertThat(postList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPost() throws Exception {
        // Initialize the database
        postService.save(post);

        // Search the post
        restPostMockMvc.perform(get("/api/_search/posts?query=id:" + post.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(post.getId().intValue())))
                .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
                .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID.toString())))
                .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
                .andExpect(jsonPath("$.[*].pub").value(hasItem(DEFAULT_PUB.booleanValue())))
                .andExpect(jsonPath("$.[*].providerDisplayName").value(hasItem(DEFAULT_PROVIDER_DISPLAY_NAME.toString())))
                .andExpect(jsonPath("$.[*].postType").value(hasItem(DEFAULT_POST_TYPE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Post.class);
        Post post1 = new Post();
        post1.setId(1L);
        Post post2 = new Post();
        post2.setId(post1.getId());
        assertThat(post1).isEqualTo(post2);
        post2.setId(2L);
        assertThat(post1).isNotEqualTo(post2);
        post1.setId(null);
        assertThat(post1).isNotEqualTo(post2);
    }
}
