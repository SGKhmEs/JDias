package com.sgkhmjaes.jdias.web.rest;

import com.sgkhmjaes.jdias.JDiasApp;

import com.sgkhmjaes.jdias.domain.Message;
import com.sgkhmjaes.jdias.domain.Person;
import com.sgkhmjaes.jdias.domain.User;
import com.sgkhmjaes.jdias.repository.MessageRepository;
import com.sgkhmjaes.jdias.repository.PersonRepository;
import com.sgkhmjaes.jdias.repository.UserRepository;
import com.sgkhmjaes.jdias.service.MessageService;
import com.sgkhmjaes.jdias.repository.search.MessageSearchRepository;
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
import java.time.ZonedDateTime;
import java.util.List;
import static com.sgkhmjaes.jdias.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import org.junit.After;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MessageResource REST controller.
 *
 * @see MessageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JDiasApp.class)
public class MessageResourceIntTest {
    
    private static Long userID;
    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String DEFAULT_GUID = "AAAAAAAAAA";
    private static final String DEFAULT_CONVERSATION_GUID = "AAAAAAAAAA";
    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.now();

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageService messageService;
    
    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;

    @Autowired
    private MessageSearchRepository messageSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMessageMockMvc;

    private Message message;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MessageResource messageResource = new MessageResource(messageService);
        this.restMessageMockMvc = MockMvcBuilders.standaloneSetup(messageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     * @param em
     * @return 
     */
    public static Message createEntity(EntityManager em) {
        Message message = new Message()
            .author(DEFAULT_AUTHOR)
            .guid(DEFAULT_GUID)
            .conversationGuid(DEFAULT_CONVERSATION_GUID)
            .text(DEFAULT_TEXT)
            .createdAt(DEFAULT_CREATED_AT);
        return message;
    }

    @Before
    public void initTest() {
        
        User user = userService.createUser("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "http://placehold.it/50x50", "en-US");
        user.setActivated(true);
        userRepository.saveAndFlush(user);
        
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("johndoe", "johndoe"));
        SecurityContextHolder.setContext(securityContext);
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get().getId();
        
        userID = user.getId();
        
        messageSearchRepository.deleteAll();
        message = createEntity(em);
    }
    
    @After
    public void deleteCreatedAccount(){
        userService.deleteUser("johndoe");
    }

    @Test
    @Transactional
    public void createMessage() throws Exception {
        int databaseSizeBeforeCreate = messageRepository.findAll().size();
        Person findPerson = personRepository.findOne(userID);

        // Create the Message
        restMessageMockMvc.perform(post("/api/messages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(message)))
                .andExpect(status().isCreated());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeCreate + 1);
        Message testMessage = messageList.get(messageList.size() - 1);
        assertThat(testMessage.getAuthor()).isEqualTo(findPerson.getDiasporaId());
        assertThat(testMessage.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testMessage.getCreatedAt()).isNotNull();//isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testMessage.getGuid()).isNotNull();//isEqualTo(DEFAULT_GUID);
        assertThat(testMessage.getConversationGuid()).isNotNull();//isEqualTo(DEFAULT_CONVERSATION_GUID);

        // Validate the Message in Elasticsearch
        Message messageEs = messageSearchRepository.findOne(testMessage.getId());
        assertThat(testMessage.getId()).isEqualTo(messageEs.getId());
        assertThat(testMessage.getConversationGuid()).isEqualTo(messageEs.getConversationGuid());
        
        assertThat(testMessage.getGuid()).isEqualTo(messageEs.getGuid());
        assertThat(testMessage.getText()).isEqualTo(messageEs.getText());
        assertThat(testMessage.getConversation()).isEqualTo(messageEs.getConversation());
        assertThat(testMessage.getCreatedAt()).isEqualTo(messageEs.getCreatedAt());
        assertThat(testMessage.getPerson()).isEqualTo(messageEs.getPerson());
        //assertThat(messageEs).isEqualToComparingFieldByField(testMessage);
    }

    @Test
    @Transactional
    public void createMessageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = messageRepository.findAll().size();

        // Create the Message with an existing ID
        message.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageMockMvc.perform(post("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(message)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMessages() throws Exception {
        // Initialize the database
        message.setPerson(personRepository.getOne(userID));
        Message saveMessage = messageService.save(message);

        // Get all the messageList
        restMessageMockMvc.perform(get("/api/messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saveMessage.getId().intValue())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(saveMessage.getAuthor())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(saveMessage.getGuid())))
            .andExpect(jsonPath("$.[*].conversationGuid").value(hasItem(saveMessage.getConversationGuid())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(saveMessage.getCreatedAt()))));
    }

    @Test
    @Transactional
    public void getMessage() throws Exception {
        // Initialize the database
        message.setPerson(personRepository.getOne(userID));
        Message saveMessage = messageService.save(message);

        // Get the message
        restMessageMockMvc.perform(get("/api/messages/{id}", saveMessage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(saveMessage.getId().intValue()))
            .andExpect(jsonPath("$.author").value(saveMessage.getAuthor()))
            .andExpect(jsonPath("$.guid").value(saveMessage.getGuid()))
            .andExpect(jsonPath("$.conversationGuid").value(saveMessage.getConversationGuid()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(saveMessage.getCreatedAt())));
    }

    @Test
    @Transactional
    public void getNonExistingMessage() throws Exception {
        // Get the message
        restMessageMockMvc.perform(get("/api/messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }
/*
    @Test
    @Transactional
    public void updateMessage() throws Exception {
        // Initialize the database
        Message saveMessage = messageService.save(message);

        int databaseSizeBeforeUpdate = messageRepository.findAll().size();

        // Update the message
        Message updatedMessage = messageRepository.findOne(saveMessage.getId());
        updatedMessage
            .author(UPDATED_AUTHOR)
            .guid(UPDATED_GUID)
            .conversationGuid(UPDATED_CONVERSATION_GUID)
            .text(UPDATED_TEXT)
            .createdAt(UPDATED_CREATED_AT);
        
        System.err.println(updatedMessage);

        restMessageMockMvc.perform(put("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMessage)))
            .andExpect(status().isOk());
   
        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        for (Message message : messageList) {
            System.err.println("* "+message);
        }
             
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
        Message testMessage = messageList.get(messageList.size() - 1);
        assertThat(testMessage.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testMessage.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testMessage.getConversationGuid()).isEqualTo(UPDATED_CONVERSATION_GUID);
        assertThat(testMessage.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testMessage.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);

        // Validate the Message in Elasticsearch
        Message messageEs = messageSearchRepository.findOne(testMessage.getId());
        assertThat(messageEs).isEqualToComparingFieldByField(testMessage);
    }
*/
    @Test
    @Transactional
    public void updateNonExistingMessage() throws Exception {
        int databaseSizeBeforeUpdate = messageRepository.findAll().size();

        // Create the Message

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMessageMockMvc.perform(put("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(message)))
            .andExpect(status().isCreated());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMessage() throws Exception {
        // Initialize the database
        message.setPerson(personRepository.getOne(userID));
        Message save = messageService.save(message);
        
        int databaseSizeBeforeDelete = messageRepository.findAll().size();

        // Get the message
        restMessageMockMvc.perform(delete("/api/messages/{id}", save.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean messageExistsInEs = messageSearchRepository.exists(save.getId());
        assertThat(messageExistsInEs).isFalse();

        // Validate the database is empty
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMessage() throws Exception {
        // Initialize the database
        message.setPerson(personRepository.findOne(userID));
        Message saveMessage = messageService.save(message);

        // Search the message
        restMessageMockMvc.perform(get("/api/_search/messages?query=id:" + saveMessage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saveMessage.getId().intValue())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(saveMessage.getAuthor())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(saveMessage.getGuid())))
            .andExpect(jsonPath("$.[*].conversationGuid").value(hasItem(saveMessage.getConversationGuid())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(saveMessage.getCreatedAt()))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Message.class);
        Message message1 = new Message();
        message1.setId(1L);
        Message message2 = new Message();
        message2.setId(message1.getId());
        assertThat(message1).isEqualTo(message2);
        message2.setId(2L);
        assertThat(message1).isNotEqualTo(message2);
        message1.setId(null);
        assertThat(message1).isNotEqualTo(message2);
    }
}
