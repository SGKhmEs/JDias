package com.sgkhmjaes.jdias.web.rest;

import com.sgkhmjaes.jdias.JDiasApp;

import com.sgkhmjaes.jdias.domain.AccountDeletion;
import com.sgkhmjaes.jdias.repository.AccountDeletionRepository;
import com.sgkhmjaes.jdias.service.AccountDeletionService;
import com.sgkhmjaes.jdias.repository.search.AccountDeletionSearchRepository;
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
 * Test class for the AccountDeletionResource REST controller.
 *
 * @see AccountDeletionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JDiasApp.class)
public class AccountDeletionResourceIntTest {

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    @Autowired
    private AccountDeletionRepository accountDeletionRepository;

    @Autowired
    private AccountDeletionService accountDeletionService;

    @Autowired
    private AccountDeletionSearchRepository accountDeletionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAccountDeletionMockMvc;

    private AccountDeletion accountDeletion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AccountDeletionResource accountDeletionResource = new AccountDeletionResource(accountDeletionService);
        this.restAccountDeletionMockMvc = MockMvcBuilders.standaloneSetup(accountDeletionResource)
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
    public static AccountDeletion createEntity(EntityManager em) {
        AccountDeletion accountDeletion = new AccountDeletion()
                .author(DEFAULT_AUTHOR);
        return accountDeletion;
    }

    @Before
    public void initTest() {
        accountDeletionSearchRepository.deleteAll();
        accountDeletion = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccountDeletion() throws Exception {
        int databaseSizeBeforeCreate = accountDeletionRepository.findAll().size();

        // Create the AccountDeletion
        restAccountDeletionMockMvc.perform(post("/api/account-deletions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(accountDeletion)))
                .andExpect(status().isCreated());

        // Validate the AccountDeletion in the database
        List<AccountDeletion> accountDeletionList = accountDeletionRepository.findAll();
        assertThat(accountDeletionList).hasSize(databaseSizeBeforeCreate + 1);
        AccountDeletion testAccountDeletion = accountDeletionList.get(accountDeletionList.size() - 1);
        assertThat(testAccountDeletion.getAuthor()).isEqualTo(DEFAULT_AUTHOR);

        // Validate the AccountDeletion in Elasticsearch
        AccountDeletion accountDeletionEs = accountDeletionSearchRepository.findOne(testAccountDeletion.getId());
        assertThat(accountDeletionEs).isEqualToComparingFieldByField(testAccountDeletion);
    }

    @Test
    @Transactional
    public void createAccountDeletionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountDeletionRepository.findAll().size();

        // Create the AccountDeletion with an existing ID
        accountDeletion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountDeletionMockMvc.perform(post("/api/account-deletions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(accountDeletion)))
                .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AccountDeletion> accountDeletionList = accountDeletionRepository.findAll();
        assertThat(accountDeletionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAccountDeletions() throws Exception {
        // Initialize the database
        accountDeletionRepository.saveAndFlush(accountDeletion);

        // Get all the accountDeletionList
        restAccountDeletionMockMvc.perform(get("/api/account-deletions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(accountDeletion.getId().intValue())))
                .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())));
    }

    @Test
    @Transactional
    public void getAccountDeletion() throws Exception {
        // Initialize the database
        accountDeletionRepository.saveAndFlush(accountDeletion);

        // Get the accountDeletion
        restAccountDeletionMockMvc.perform(get("/api/account-deletions/{id}", accountDeletion.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(accountDeletion.getId().intValue()))
                .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAccountDeletion() throws Exception {
        // Get the accountDeletion
        restAccountDeletionMockMvc.perform(get("/api/account-deletions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccountDeletion() throws Exception {
        // Initialize the database
        accountDeletionService.save(accountDeletion);

        int databaseSizeBeforeUpdate = accountDeletionRepository.findAll().size();

        // Update the accountDeletion
        AccountDeletion updatedAccountDeletion = accountDeletionRepository.findOne(accountDeletion.getId());
        updatedAccountDeletion
                .author(UPDATED_AUTHOR);

        restAccountDeletionMockMvc.perform(put("/api/account-deletions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAccountDeletion)))
                .andExpect(status().isOk());

        // Validate the AccountDeletion in the database
        List<AccountDeletion> accountDeletionList = accountDeletionRepository.findAll();
        assertThat(accountDeletionList).hasSize(databaseSizeBeforeUpdate);
        AccountDeletion testAccountDeletion = accountDeletionList.get(accountDeletionList.size() - 1);
        assertThat(testAccountDeletion.getAuthor()).isEqualTo(UPDATED_AUTHOR);

        // Validate the AccountDeletion in Elasticsearch
        AccountDeletion accountDeletionEs = accountDeletionSearchRepository.findOne(testAccountDeletion.getId());
        assertThat(accountDeletionEs).isEqualToComparingFieldByField(testAccountDeletion);
    }

    @Test
    @Transactional
    public void updateNonExistingAccountDeletion() throws Exception {
        int databaseSizeBeforeUpdate = accountDeletionRepository.findAll().size();

        // Create the AccountDeletion
        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAccountDeletionMockMvc.perform(put("/api/account-deletions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(accountDeletion)))
                .andExpect(status().isCreated());

        // Validate the AccountDeletion in the database
        List<AccountDeletion> accountDeletionList = accountDeletionRepository.findAll();
        assertThat(accountDeletionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAccountDeletion() throws Exception {
        // Initialize the database
        accountDeletionService.save(accountDeletion);

        int databaseSizeBeforeDelete = accountDeletionRepository.findAll().size();

        // Get the accountDeletion
        restAccountDeletionMockMvc.perform(delete("/api/account-deletions/{id}", accountDeletion.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean accountDeletionExistsInEs = accountDeletionSearchRepository.exists(accountDeletion.getId());
        assertThat(accountDeletionExistsInEs).isFalse();

        // Validate the database is empty
        List<AccountDeletion> accountDeletionList = accountDeletionRepository.findAll();
        assertThat(accountDeletionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAccountDeletion() throws Exception {
        // Initialize the database
        accountDeletionService.save(accountDeletion);

        // Search the accountDeletion
        restAccountDeletionMockMvc.perform(get("/api/_search/account-deletions?query=id:" + accountDeletion.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(accountDeletion.getId().intValue())))
                .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountDeletion.class);
        AccountDeletion accountDeletion1 = new AccountDeletion();
        accountDeletion1.setId(1L);
        AccountDeletion accountDeletion2 = new AccountDeletion();
        accountDeletion2.setId(accountDeletion1.getId());
        assertThat(accountDeletion1).isEqualTo(accountDeletion2);
        accountDeletion2.setId(2L);
        assertThat(accountDeletion1).isNotEqualTo(accountDeletion2);
        accountDeletion1.setId(null);
        assertThat(accountDeletion1).isNotEqualTo(accountDeletion2);
    }
}
