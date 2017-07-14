package com.sgkhmjaes.jdias.web.rest;

import com.sgkhmjaes.jdias.JDiasApp;

import com.sgkhmjaes.jdias.domain.Conversation;
import com.sgkhmjaes.jdias.domain.StatusMessage;
import com.sgkhmjaes.jdias.domain.User;
import com.sgkhmjaes.jdias.repository.ConversationRepository;
import com.sgkhmjaes.jdias.repository.PersonRepository;
import com.sgkhmjaes.jdias.repository.StatusMessageRepository;
import com.sgkhmjaes.jdias.repository.UserRepository;
import com.sgkhmjaes.jdias.service.ConversationService;
import com.sgkhmjaes.jdias.repository.search.ConversationSearchRepository;
import com.sgkhmjaes.jdias.repository.search.StatusMessageSearchRepository;
import com.sgkhmjaes.jdias.security.SecurityUtils;
import com.sgkhmjaes.jdias.service.UserService;
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
import java.util.List;
import java.net.InetAddress;
import java.net.UnknownHostException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import org.junit.After;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ConversationResource REST controller.
 *
 * @see ConversationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JDiasApp.class)
public class ConversationResourceIntTest {
    
    private static Long userID;

    private static String DEFAULT_AUTHOR;// = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static String DEFAULT_GUID; // = "AAAAAAAAAA";
    private static final String UPDATED_GUID = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.now();
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now();

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private ConversationSearchRepository conversationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserService userService;
    
    private MockMvc restConversationMockMvc;
    
    private Conversation conversation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConversationResource conversationResource = new ConversationResource(conversationService);
        this.restConversationMockMvc = MockMvcBuilders.standaloneSetup(conversationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
                
        DEFAULT_GUID = conversation.getGuid();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     * @param em
     * @return 
     */
    public static Conversation createEntity(EntityManager em) {
        Conversation conversation = new Conversation()
            .author(DEFAULT_AUTHOR)
            .guid(DEFAULT_GUID)
            .subject(DEFAULT_SUBJECT)
            .createdAt(DEFAULT_CREATED_AT)
            .message(DEFAULT_MESSAGE);
        return conversation;
    }

    @Before
    public void initTest() throws UnknownHostException {
        
        User user = userService.createUser("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "http://placehold.it/50x50", "en-US");
        user.setActivated(true);
        userRepository.saveAndFlush(user);
        
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("johndoe", "johndoe"));
        SecurityContextHolder.setContext(securityContext);
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get().getId();
        
        DEFAULT_AUTHOR = user.getLogin() + "@" + InetAddress.getLocalHost().getHostName();        
        userID = user.getId();
        
        conversationSearchRepository.deleteAll();
        conversation = createEntity(em);        
    }
        
    @After
    public void deleteCreatedAccount(){
        userService.deleteUser("johndoe");
    }

    @Test
    @Transactional
    public void createConversation() throws Exception {
        int databaseSizeBeforeCreate = conversationRepository.findAll().size();
        
        // Create the Conversation
        restConversationMockMvc.perform(post("/api/conversations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversation)))
            .andExpect(status().isCreated());
        
        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeCreate + 1);
        Conversation testConversation = conversationList.get(conversationList.size() - 1);        
        //DEFAULT_GUID = testConversation.getGuid();
        assertThat(testConversation.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testConversation.getGuid()).isNotNull();
        assertThat(testConversation.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testConversation.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        //assertThat(testConversation.getMessage()).isEqualTo(DEFAULT_MESSAGE);

        // Validate the Conversation in Elasticsearch
        Conversation conversationEs = conversationSearchRepository.findOne(testConversation.getId());
        assertThat(testConversation.getId()).isEqualTo(conversationEs.getId());
        assertThat(testConversation.getAuthor()).isEqualTo(conversationEs.getAuthor());
        assertThat(testConversation.getGuid()).isEqualTo(conversationEs.getGuid());
        assertThat(testConversation.getSubject()).isEqualTo(conversationEs.getSubject());
        assertThat(testConversation.getCreatedAt()).isEqualTo(conversationEs.getCreatedAt());
        assertThat(testConversation.getMessage()).isEqualTo(conversationEs.getMessage());
        //assertThat(conversationEs).isEqualToComparingFieldByField(testConversation);
    }

    @Test
    @Transactional
    public void createConversationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conversationRepository.findAll().size();

        // Create the Conversation with an existing ID
        conversation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConversationMockMvc.perform(post("/api/conversations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversation)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllConversations() throws Exception {
        // Initialize the database
        conversation.addParticipants(personRepository.findOne(userID));
        conversationRepository.saveAndFlush(conversation);

        // Get all the conversationList
        //restConversationMockMvc.perform(get("/api/conversations"))
        restConversationMockMvc.perform(get("/api/conversations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conversation.getId().intValue())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)));
    }

    @Test
    @Transactional
    public void getConversation() throws Exception {
        // Initialize the database
        conversation.addParticipants(personRepository.findOne(userID));
        conversationRepository.saveAndFlush(conversation);
        
        // Get the conversation
        ResultActions perform = restConversationMockMvc.perform(get("/api/conversations/{id}", conversation.getId()));
        perform.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(conversation.getId().intValue()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE));
    }

    @Test
    @Transactional
    public void getNonExistingConversation() throws Exception {
        // Get the conversation
        restConversationMockMvc.perform(get("/api/conversations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConversation() throws Exception {
        // Initialize the database
        conversation.addParticipants(personRepository.findOne(userID));
        conversationRepository.save(conversation);

        int databaseSizeBeforeUpdate = conversationRepository.findAll().size();

        // Update the conversation
        Conversation updatedConversation = conversationRepository.findOne(conversation.getId());
        updatedConversation
            .author(UPDATED_AUTHOR)
            .guid(UPDATED_GUID)
            .subject(UPDATED_SUBJECT)
            .createdAt(UPDATED_CREATED_AT)
            .message(UPDATED_MESSAGE);

        restConversationMockMvc.perform(put("/api/conversations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConversation)))
            .andExpect(status().isOk());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeUpdate);
        Conversation testConversation = conversationList.get(conversationList.size() - 1);
        assertThat(testConversation.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testConversation.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testConversation.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testConversation.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT.toString());
        assertThat(testConversation.getMessage()).isEqualTo(UPDATED_MESSAGE);;

        // Validate the Conversation in Elasticsearch
        Conversation conversationEs = conversationSearchRepository.findOne(testConversation.getId());
        testConversation.setUpdatedAt(conversationEs.getUpdatedAt());
        assertThat(conversationEs).isEqualToComparingFieldByField(testConversation);
    }

    @Test
    @Transactional
    public void updateNonExistingConversation() throws Exception {
        int databaseSizeBeforeUpdate = conversationRepository.findAll().size();

        // Create the Conversation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConversationMockMvc.perform(put("/api/conversations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversation)))
            .andExpect(status().isCreated());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConversation() throws Exception {
        // Initialize the database
        conversation.addParticipants(personRepository.findOne(userID));
        conversationRepository.save(conversation);
        int databaseSizeBeforeDelete = conversationRepository.findAll().size();
        
        // Get the conversation
        restConversationMockMvc.perform(delete("/api/conversations/{id}", conversation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean conversationExistsInEs = conversationSearchRepository.exists(conversation.getId());
        assertThat(conversationExistsInEs).isFalse();

        // Validate the database is empty
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchConversation() throws Exception {
        // Initialize the database
        conversation.addParticipants(personRepository.findOne(userID));
        Conversation saveConversation = conversationService.save(conversation);
        ResultActions perform = restConversationMockMvc.perform(get("/api/_search/conversations?query=id:" + saveConversation.getId()));
        DEFAULT_GUID = saveConversation.getGuid();
        
        //restConversationMockMvc.perform(get("/api/_search/conversations?query=id:" + saveConversation.getId()))
        perform.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saveConversation.getId().intValue())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
            //.andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)));
    }
    
    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Conversation.class);
        Conversation conversation1 = new Conversation();
        conversation1.setId(1L);
        Conversation conversation2 = new Conversation();
        conversation2.setId(conversation1.getId());
        assertThat(conversation1).isEqualTo(conversation2);
        conversation2.setId(2L);
        assertThat(conversation1).isNotEqualTo(conversation2);
        conversation1.setId(null);
        assertThat(conversation1).isNotEqualTo(conversation2);
    }
}
