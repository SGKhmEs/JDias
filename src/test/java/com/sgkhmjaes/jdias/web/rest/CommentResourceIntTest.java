package com.sgkhmjaes.jdias.web.rest;

import com.sgkhmjaes.jdias.JDiasApp;

import com.sgkhmjaes.jdias.domain.Comment;
import com.sgkhmjaes.jdias.repository.CommentRepository;
import com.sgkhmjaes.jdias.service.CommentService;
import com.sgkhmjaes.jdias.repository.search.CommentSearchRepository;
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
 * Test class for the CommentResource REST controller.
 *
 * @see CommentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JDiasApp.class)
public class CommentResourceIntTest {

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_GUID = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_AUTHOR_SIGNATURE = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR_SIGNATURE = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_AUTHOR_SIGNATURE = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_AUTHOR_SIGNATURE = "BBBBBBBBBB";

    private static final String DEFAULT_THREAD_PARENT_GUID = "AAAAAAAAAA";
    private static final String UPDATED_THREAD_PARENT_GUID = "BBBBBBBBBB";

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentSearchRepository commentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommentMockMvc;

    private Comment comment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CommentResource commentResource = new CommentResource(commentService);
        this.restCommentMockMvc = MockMvcBuilders.standaloneSetup(commentResource)
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
    public static Comment createEntity(EntityManager em) {
        Comment comment = new Comment()
            .author(DEFAULT_AUTHOR)
            .guid(DEFAULT_GUID)
            .parentGuid(DEFAULT_PARENT_GUID)
            .text(DEFAULT_TEXT)
            .createdAt(DEFAULT_CREATED_AT)
            .authorSignature(DEFAULT_AUTHOR_SIGNATURE)
            .parentAuthorSignature(DEFAULT_PARENT_AUTHOR_SIGNATURE)
            .threadParentGuid(DEFAULT_THREAD_PARENT_GUID);
        return comment;
    }

    @Before
    public void initTest() {
        commentSearchRepository.deleteAll();
        comment = createEntity(em);
    }

    @Test
    @Transactional
    public void createComment() throws Exception {
        int databaseSizeBeforeCreate = commentRepository.findAll().size();

        // Create the Comment
        restCommentMockMvc.perform(post("/api/comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comment)))
            .andExpect(status().isCreated());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeCreate + 1);
        Comment testComment = commentList.get(commentList.size() - 1);
        assertThat(testComment.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testComment.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testComment.getParentGuid()).isEqualTo(DEFAULT_PARENT_GUID);
        assertThat(testComment.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testComment.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testComment.getAuthorSignature()).isEqualTo(DEFAULT_AUTHOR_SIGNATURE);
        assertThat(testComment.getParentAuthorSignature()).isEqualTo(DEFAULT_PARENT_AUTHOR_SIGNATURE);
        assertThat(testComment.getThreadParentGuid()).isEqualTo(DEFAULT_THREAD_PARENT_GUID);

        // Validate the Comment in Elasticsearch
        Comment commentEs = commentSearchRepository.findOne(testComment.getId());
        assertThat(commentEs).isEqualToComparingFieldByField(testComment);
    }

    @Test
    @Transactional
    public void createCommentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commentRepository.findAll().size();

        // Create the Comment with an existing ID
        comment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentMockMvc.perform(post("/api/comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comment)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllComments() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get all the commentList
        restCommentMockMvc.perform(get("/api/comments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comment.getId().intValue())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID.toString())))
            .andExpect(jsonPath("$.[*].parentGuid").value(hasItem(DEFAULT_PARENT_GUID.toString())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].authorSignature").value(hasItem(DEFAULT_AUTHOR_SIGNATURE.toString())))
            .andExpect(jsonPath("$.[*].parentAuthorSignature").value(hasItem(DEFAULT_PARENT_AUTHOR_SIGNATURE.toString())))
            .andExpect(jsonPath("$.[*].threadParentGuid").value(hasItem(DEFAULT_THREAD_PARENT_GUID.toString())));
    }

    @Test
    @Transactional
    public void getComment() throws Exception {
        // Initialize the database
        commentRepository.saveAndFlush(comment);

        // Get the comment
        restCommentMockMvc.perform(get("/api/comments/{id}", comment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comment.getId().intValue()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID.toString()))
            .andExpect(jsonPath("$.parentGuid").value(DEFAULT_PARENT_GUID.toString()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.authorSignature").value(DEFAULT_AUTHOR_SIGNATURE.toString()))
            .andExpect(jsonPath("$.parentAuthorSignature").value(DEFAULT_PARENT_AUTHOR_SIGNATURE.toString()))
            .andExpect(jsonPath("$.threadParentGuid").value(DEFAULT_THREAD_PARENT_GUID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingComment() throws Exception {
        // Get the comment
        restCommentMockMvc.perform(get("/api/comments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComment() throws Exception {
        // Initialize the database
        commentService.save(comment);

        int databaseSizeBeforeUpdate = commentRepository.findAll().size();

        // Update the comment
        Comment updatedComment = commentRepository.findOne(comment.getId());
        updatedComment
            .author(UPDATED_AUTHOR)
            .guid(UPDATED_GUID)
            .parentGuid(UPDATED_PARENT_GUID)
            .text(UPDATED_TEXT)
            .createdAt(UPDATED_CREATED_AT)
            .authorSignature(UPDATED_AUTHOR_SIGNATURE)
            .parentAuthorSignature(UPDATED_PARENT_AUTHOR_SIGNATURE)
            .threadParentGuid(UPDATED_THREAD_PARENT_GUID);

        restCommentMockMvc.perform(put("/api/comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComment)))
            .andExpect(status().isOk());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
        Comment testComment = commentList.get(commentList.size() - 1);
        assertThat(testComment.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testComment.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testComment.getParentGuid()).isEqualTo(UPDATED_PARENT_GUID);
        assertThat(testComment.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testComment.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testComment.getAuthorSignature()).isEqualTo(UPDATED_AUTHOR_SIGNATURE);
        assertThat(testComment.getParentAuthorSignature()).isEqualTo(UPDATED_PARENT_AUTHOR_SIGNATURE);
        assertThat(testComment.getThreadParentGuid()).isEqualTo(UPDATED_THREAD_PARENT_GUID);

        // Validate the Comment in Elasticsearch
        Comment commentEs = commentSearchRepository.findOne(testComment.getId());
        assertThat(commentEs).isEqualToComparingFieldByField(testComment);
    }

    @Test
    @Transactional
    public void updateNonExistingComment() throws Exception {
        int databaseSizeBeforeUpdate = commentRepository.findAll().size();

        // Create the Comment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommentMockMvc.perform(put("/api/comments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comment)))
            .andExpect(status().isCreated());

        // Validate the Comment in the database
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteComment() throws Exception {
        // Initialize the database
        commentService.save(comment);

        int databaseSizeBeforeDelete = commentRepository.findAll().size();

        // Get the comment
        restCommentMockMvc.perform(delete("/api/comments/{id}", comment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean commentExistsInEs = commentSearchRepository.exists(comment.getId());
        assertThat(commentExistsInEs).isFalse();

        // Validate the database is empty
        List<Comment> commentList = commentRepository.findAll();
        assertThat(commentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchComment() throws Exception {
        // Initialize the database
        commentService.save(comment);

        // Search the comment
        restCommentMockMvc.perform(get("/api/_search/comments?query=id:" + comment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comment.getId().intValue())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID.toString())))
            .andExpect(jsonPath("$.[*].parentGuid").value(hasItem(DEFAULT_PARENT_GUID.toString())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].authorSignature").value(hasItem(DEFAULT_AUTHOR_SIGNATURE.toString())))
            .andExpect(jsonPath("$.[*].parentAuthorSignature").value(hasItem(DEFAULT_PARENT_AUTHOR_SIGNATURE.toString())))
            .andExpect(jsonPath("$.[*].threadParentGuid").value(hasItem(DEFAULT_THREAD_PARENT_GUID.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comment.class);
        Comment comment1 = new Comment();
        comment1.setId(1L);
        Comment comment2 = new Comment();
        comment2.setId(comment1.getId());
        assertThat(comment1).isEqualTo(comment2);
        comment2.setId(2L);
        assertThat(comment1).isNotEqualTo(comment2);
        comment1.setId(null);
        assertThat(comment1).isNotEqualTo(comment2);
    }
}
