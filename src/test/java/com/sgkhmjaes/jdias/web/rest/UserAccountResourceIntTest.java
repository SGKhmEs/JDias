package com.sgkhmjaes.jdias.web.rest;

import com.sgkhmjaes.jdias.JDiasApp;

import com.sgkhmjaes.jdias.domain.UserAccount;
import com.sgkhmjaes.jdias.repository.UserAccountRepository;
import com.sgkhmjaes.jdias.service.UserAccountService;
import com.sgkhmjaes.jdias.repository.search.UserAccountSearchRepository;
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
 * Test class for the UserAccountResource REST controller.
 *
 * @see UserAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JDiasApp.class)
public class UserAccountResourceIntTest {

    private static final String DEFAULT_SERIALIZED_PRIVATE_KEY = "AAAAAAAAAA";
    private static final String UPDATED_SERIALIZED_PRIVATE_KEY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_GETTING_STARTED = false;
    private static final Boolean UPDATED_GETTING_STARTED = true;

    private static final Boolean DEFAULT_DISABLE_MAIL = false;
    private static final Boolean UPDATED_DISABLE_MAIL = true;

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_REMEMBER_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REMEMBER_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_SIGN_IN_COUNT = 1;
    private static final Integer UPDATED_SIGN_IN_COUNT = 2;

    private static final ZonedDateTime DEFAULT_CURRENT_SIGN_IN_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CURRENT_SIGN_IN_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LAST_SIGN_IN_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_SIGN_IN_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CURRENT_SIGN_IN_IP = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_SIGN_IN_IP = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_SIGN_IN_IP = "AAAAAAAAAA";
    private static final String UPDATED_LAST_SIGN_IN_IP = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_LOCKED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LOCKED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_SHOW_COMMUNITY_SPOTLIGHT_IN_STREAM = false;
    private static final Boolean UPDATED_SHOW_COMMUNITY_SPOTLIGHT_IN_STREAM = true;

    private static final Boolean DEFAULT_AUTO_FOLLOW_BACK = false;
    private static final Boolean UPDATED_AUTO_FOLLOW_BACK = true;

    private static final Integer DEFAULT_AUTO_FOLLOW_BACK_ASPECT_ID = 1;
    private static final Integer UPDATED_AUTO_FOLLOW_BACK_ASPECT_ID = 2;

    private static final String DEFAULT_HIDDEN_SHAREABLES = "AAAAAAAAAA";
    private static final String UPDATED_HIDDEN_SHAREABLES = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_SEEN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_SEEN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_EXPORT_E = "AAAAAAAAAA";
    private static final String UPDATED_EXPORT_E = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EXPORTED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXPORTED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_EXPORTING = false;
    private static final Boolean UPDATED_EXPORTING = true;

    private static final Boolean DEFAULT_STRIP_EXIF = false;
    private static final Boolean UPDATED_STRIP_EXIF = true;

    private static final String DEFAULT_EXPORTED_PHOTOS_FILE = "AAAAAAAAAA";
    private static final String UPDATED_EXPORTED_PHOTOS_FILE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EXPORTED_PHOTOS_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXPORTED_PHOTOS_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_EXPORTING_PHOTOS = false;
    private static final Boolean UPDATED_EXPORTING_PHOTOS = true;

    private static final String DEFAULT_COLOR_THEME = "AAAAAAAAAA";
    private static final String UPDATED_COLOR_THEME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_POST_DEFAULT_PUBLIC = false;
    private static final Boolean UPDATED_POST_DEFAULT_PUBLIC = true;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private UserAccountSearchRepository userAccountSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserAccountMockMvc;

    private UserAccount userAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserAccountResource userAccountResource = new UserAccountResource(userAccountService);
        this.restUserAccountMockMvc = MockMvcBuilders.standaloneSetup(userAccountResource)
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
    public static UserAccount createEntity(EntityManager em) {
        UserAccount userAccount = new UserAccount()
            .serializedPrivateKey(DEFAULT_SERIALIZED_PRIVATE_KEY)
            .gettingStarted(DEFAULT_GETTING_STARTED)
            .disableMail(DEFAULT_DISABLE_MAIL)
            .language(DEFAULT_LANGUAGE)
            .rememberCreatedAt(DEFAULT_REMEMBER_CREATED_AT)
            .signInCount(DEFAULT_SIGN_IN_COUNT)
            .currentSignInAt(DEFAULT_CURRENT_SIGN_IN_AT)
            .lastSignInAt(DEFAULT_LAST_SIGN_IN_AT)
            .currentSignInIp(DEFAULT_CURRENT_SIGN_IN_IP)
            .lastSignInIp(DEFAULT_LAST_SIGN_IN_IP)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .lockedAt(DEFAULT_LOCKED_AT)
            .showCommunitySpotlightInStream(DEFAULT_SHOW_COMMUNITY_SPOTLIGHT_IN_STREAM)
            .autoFollowBack(DEFAULT_AUTO_FOLLOW_BACK)
            .autoFollowBackAspectId(DEFAULT_AUTO_FOLLOW_BACK_ASPECT_ID)
            .hiddenShareables(DEFAULT_HIDDEN_SHAREABLES)
            .lastSeen(DEFAULT_LAST_SEEN)
            .exportE(DEFAULT_EXPORT_E)
            .exportedAt(DEFAULT_EXPORTED_AT)
            .exporting(DEFAULT_EXPORTING)
            .stripExif(DEFAULT_STRIP_EXIF)
            .exportedPhotosFile(DEFAULT_EXPORTED_PHOTOS_FILE)
            .exportedPhotosAt(DEFAULT_EXPORTED_PHOTOS_AT)
            .exportingPhotos(DEFAULT_EXPORTING_PHOTOS)
            .colorTheme(DEFAULT_COLOR_THEME)
            .postDefaultPublic(DEFAULT_POST_DEFAULT_PUBLIC);
        return userAccount;
    }

    @Before
    public void initTest() {
        userAccountSearchRepository.deleteAll();
        userAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserAccount() throws Exception {
        int databaseSizeBeforeCreate = userAccountRepository.findAll().size();

        // Create the UserAccount
        restUserAccountMockMvc.perform(post("/api/user-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAccount)))
            .andExpect(status().isCreated());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeCreate + 1);
        UserAccount testUserAccount = userAccountList.get(userAccountList.size() - 1);
        assertThat(testUserAccount.getSerializedPrivateKey()).isEqualTo(DEFAULT_SERIALIZED_PRIVATE_KEY);
        assertThat(testUserAccount.isGettingStarted()).isEqualTo(DEFAULT_GETTING_STARTED);
        assertThat(testUserAccount.isDisableMail()).isEqualTo(DEFAULT_DISABLE_MAIL);
        assertThat(testUserAccount.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testUserAccount.getRememberCreatedAt()).isEqualTo(DEFAULT_REMEMBER_CREATED_AT);
        assertThat(testUserAccount.getSignInCount()).isEqualTo(DEFAULT_SIGN_IN_COUNT);
        assertThat(testUserAccount.getCurrentSignInAt()).isEqualTo(DEFAULT_CURRENT_SIGN_IN_AT);
        assertThat(testUserAccount.getLastSignInAt()).isEqualTo(DEFAULT_LAST_SIGN_IN_AT);
        assertThat(testUserAccount.getCurrentSignInIp()).isEqualTo(DEFAULT_CURRENT_SIGN_IN_IP);
        assertThat(testUserAccount.getLastSignInIp()).isEqualTo(DEFAULT_LAST_SIGN_IN_IP);
        assertThat(testUserAccount.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testUserAccount.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testUserAccount.getLockedAt()).isEqualTo(DEFAULT_LOCKED_AT);
        assertThat(testUserAccount.isShowCommunitySpotlightInStream()).isEqualTo(DEFAULT_SHOW_COMMUNITY_SPOTLIGHT_IN_STREAM);
        assertThat(testUserAccount.isAutoFollowBack()).isEqualTo(DEFAULT_AUTO_FOLLOW_BACK);
        assertThat(testUserAccount.getAutoFollowBackAspectId()).isEqualTo(DEFAULT_AUTO_FOLLOW_BACK_ASPECT_ID);
        assertThat(testUserAccount.getHiddenShareables()).isEqualTo(DEFAULT_HIDDEN_SHAREABLES);
        assertThat(testUserAccount.getLastSeen()).isEqualTo(DEFAULT_LAST_SEEN);
        assertThat(testUserAccount.getExportE()).isEqualTo(DEFAULT_EXPORT_E);
        assertThat(testUserAccount.getExportedAt()).isEqualTo(DEFAULT_EXPORTED_AT);
        assertThat(testUserAccount.isExporting()).isEqualTo(DEFAULT_EXPORTING);
        assertThat(testUserAccount.isStripExif()).isEqualTo(DEFAULT_STRIP_EXIF);
        assertThat(testUserAccount.getExportedPhotosFile()).isEqualTo(DEFAULT_EXPORTED_PHOTOS_FILE);
        assertThat(testUserAccount.getExportedPhotosAt()).isEqualTo(DEFAULT_EXPORTED_PHOTOS_AT);
        assertThat(testUserAccount.isExportingPhotos()).isEqualTo(DEFAULT_EXPORTING_PHOTOS);
        assertThat(testUserAccount.getColorTheme()).isEqualTo(DEFAULT_COLOR_THEME);
        assertThat(testUserAccount.isPostDefaultPublic()).isEqualTo(DEFAULT_POST_DEFAULT_PUBLIC);

        // Validate the UserAccount in Elasticsearch
        UserAccount userAccountEs = userAccountSearchRepository.findOne(testUserAccount.getId());
        assertThat(userAccountEs).isEqualToComparingFieldByField(testUserAccount);
    }

    @Test
    @Transactional
    public void createUserAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAccountRepository.findAll().size();

        // Create the UserAccount with an existing ID
        userAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAccountMockMvc.perform(post("/api/user-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAccount)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserAccounts() throws Exception {
        // Initialize the database
        userAccountRepository.saveAndFlush(userAccount);

        // Get all the userAccountList
        restUserAccountMockMvc.perform(get("/api/user-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].serializedPrivateKey").value(hasItem(DEFAULT_SERIALIZED_PRIVATE_KEY.toString())))
            .andExpect(jsonPath("$.[*].gettingStarted").value(hasItem(DEFAULT_GETTING_STARTED.booleanValue())))
            .andExpect(jsonPath("$.[*].disableMail").value(hasItem(DEFAULT_DISABLE_MAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].rememberCreatedAt").value(hasItem(sameInstant(DEFAULT_REMEMBER_CREATED_AT))))
            .andExpect(jsonPath("$.[*].signInCount").value(hasItem(DEFAULT_SIGN_IN_COUNT)))
            .andExpect(jsonPath("$.[*].currentSignInAt").value(hasItem(sameInstant(DEFAULT_CURRENT_SIGN_IN_AT))))
            .andExpect(jsonPath("$.[*].lastSignInAt").value(hasItem(sameInstant(DEFAULT_LAST_SIGN_IN_AT))))
            .andExpect(jsonPath("$.[*].currentSignInIp").value(hasItem(DEFAULT_CURRENT_SIGN_IN_IP.toString())))
            .andExpect(jsonPath("$.[*].lastSignInIp").value(hasItem(DEFAULT_LAST_SIGN_IN_IP.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].lockedAt").value(hasItem(sameInstant(DEFAULT_LOCKED_AT))))
            .andExpect(jsonPath("$.[*].showCommunitySpotlightInStream").value(hasItem(DEFAULT_SHOW_COMMUNITY_SPOTLIGHT_IN_STREAM.booleanValue())))
            .andExpect(jsonPath("$.[*].autoFollowBack").value(hasItem(DEFAULT_AUTO_FOLLOW_BACK.booleanValue())))
            .andExpect(jsonPath("$.[*].autoFollowBackAspectId").value(hasItem(DEFAULT_AUTO_FOLLOW_BACK_ASPECT_ID)))
            .andExpect(jsonPath("$.[*].hiddenShareables").value(hasItem(DEFAULT_HIDDEN_SHAREABLES.toString())))
            .andExpect(jsonPath("$.[*].lastSeen").value(hasItem(sameInstant(DEFAULT_LAST_SEEN))))
            .andExpect(jsonPath("$.[*].exportE").value(hasItem(DEFAULT_EXPORT_E.toString())))
            .andExpect(jsonPath("$.[*].exportedAt").value(hasItem(sameInstant(DEFAULT_EXPORTED_AT))))
            .andExpect(jsonPath("$.[*].exporting").value(hasItem(DEFAULT_EXPORTING.booleanValue())))
            .andExpect(jsonPath("$.[*].stripExif").value(hasItem(DEFAULT_STRIP_EXIF.booleanValue())))
            .andExpect(jsonPath("$.[*].exportedPhotosFile").value(hasItem(DEFAULT_EXPORTED_PHOTOS_FILE.toString())))
            .andExpect(jsonPath("$.[*].exportedPhotosAt").value(hasItem(sameInstant(DEFAULT_EXPORTED_PHOTOS_AT))))
            .andExpect(jsonPath("$.[*].exportingPhotos").value(hasItem(DEFAULT_EXPORTING_PHOTOS.booleanValue())))
            .andExpect(jsonPath("$.[*].colorTheme").value(hasItem(DEFAULT_COLOR_THEME.toString())))
            .andExpect(jsonPath("$.[*].postDefaultPublic").value(hasItem(DEFAULT_POST_DEFAULT_PUBLIC.booleanValue())));
    }

    @Test
    @Transactional
    public void getUserAccount() throws Exception {
        // Initialize the database
        userAccountRepository.saveAndFlush(userAccount);

        // Get the userAccount
        restUserAccountMockMvc.perform(get("/api/user-accounts/{id}", userAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userAccount.getId().intValue()))
            .andExpect(jsonPath("$.serializedPrivateKey").value(DEFAULT_SERIALIZED_PRIVATE_KEY.toString()))
            .andExpect(jsonPath("$.gettingStarted").value(DEFAULT_GETTING_STARTED.booleanValue()))
            .andExpect(jsonPath("$.disableMail").value(DEFAULT_DISABLE_MAIL.booleanValue()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.rememberCreatedAt").value(sameInstant(DEFAULT_REMEMBER_CREATED_AT)))
            .andExpect(jsonPath("$.signInCount").value(DEFAULT_SIGN_IN_COUNT))
            .andExpect(jsonPath("$.currentSignInAt").value(sameInstant(DEFAULT_CURRENT_SIGN_IN_AT)))
            .andExpect(jsonPath("$.lastSignInAt").value(sameInstant(DEFAULT_LAST_SIGN_IN_AT)))
            .andExpect(jsonPath("$.currentSignInIp").value(DEFAULT_CURRENT_SIGN_IN_IP.toString()))
            .andExpect(jsonPath("$.lastSignInIp").value(DEFAULT_LAST_SIGN_IN_IP.toString()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)))
            .andExpect(jsonPath("$.lockedAt").value(sameInstant(DEFAULT_LOCKED_AT)))
            .andExpect(jsonPath("$.showCommunitySpotlightInStream").value(DEFAULT_SHOW_COMMUNITY_SPOTLIGHT_IN_STREAM.booleanValue()))
            .andExpect(jsonPath("$.autoFollowBack").value(DEFAULT_AUTO_FOLLOW_BACK.booleanValue()))
            .andExpect(jsonPath("$.autoFollowBackAspectId").value(DEFAULT_AUTO_FOLLOW_BACK_ASPECT_ID))
            .andExpect(jsonPath("$.hiddenShareables").value(DEFAULT_HIDDEN_SHAREABLES.toString()))
            .andExpect(jsonPath("$.lastSeen").value(sameInstant(DEFAULT_LAST_SEEN)))
            .andExpect(jsonPath("$.exportE").value(DEFAULT_EXPORT_E.toString()))
            .andExpect(jsonPath("$.exportedAt").value(sameInstant(DEFAULT_EXPORTED_AT)))
            .andExpect(jsonPath("$.exporting").value(DEFAULT_EXPORTING.booleanValue()))
            .andExpect(jsonPath("$.stripExif").value(DEFAULT_STRIP_EXIF.booleanValue()))
            .andExpect(jsonPath("$.exportedPhotosFile").value(DEFAULT_EXPORTED_PHOTOS_FILE.toString()))
            .andExpect(jsonPath("$.exportedPhotosAt").value(sameInstant(DEFAULT_EXPORTED_PHOTOS_AT)))
            .andExpect(jsonPath("$.exportingPhotos").value(DEFAULT_EXPORTING_PHOTOS.booleanValue()))
            .andExpect(jsonPath("$.colorTheme").value(DEFAULT_COLOR_THEME.toString()))
            .andExpect(jsonPath("$.postDefaultPublic").value(DEFAULT_POST_DEFAULT_PUBLIC.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserAccount() throws Exception {
        // Get the userAccount
        restUserAccountMockMvc.perform(get("/api/user-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserAccount() throws Exception {
        // Initialize the database
        userAccountService.save(userAccount);

        int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();

        // Update the userAccount
        UserAccount updatedUserAccount = userAccountRepository.findOne(userAccount.getId());
        updatedUserAccount
            .serializedPrivateKey(UPDATED_SERIALIZED_PRIVATE_KEY)
            .gettingStarted(UPDATED_GETTING_STARTED)
            .disableMail(UPDATED_DISABLE_MAIL)
            .language(UPDATED_LANGUAGE)
            .rememberCreatedAt(UPDATED_REMEMBER_CREATED_AT)
            .signInCount(UPDATED_SIGN_IN_COUNT)
            .currentSignInAt(UPDATED_CURRENT_SIGN_IN_AT)
            .lastSignInAt(UPDATED_LAST_SIGN_IN_AT)
            .currentSignInIp(UPDATED_CURRENT_SIGN_IN_IP)
            .lastSignInIp(UPDATED_LAST_SIGN_IN_IP)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .lockedAt(UPDATED_LOCKED_AT)
            .showCommunitySpotlightInStream(UPDATED_SHOW_COMMUNITY_SPOTLIGHT_IN_STREAM)
            .autoFollowBack(UPDATED_AUTO_FOLLOW_BACK)
            .autoFollowBackAspectId(UPDATED_AUTO_FOLLOW_BACK_ASPECT_ID)
            .hiddenShareables(UPDATED_HIDDEN_SHAREABLES)
            .lastSeen(UPDATED_LAST_SEEN)
            .exportE(UPDATED_EXPORT_E)
            .exportedAt(UPDATED_EXPORTED_AT)
            .exporting(UPDATED_EXPORTING)
            .stripExif(UPDATED_STRIP_EXIF)
            .exportedPhotosFile(UPDATED_EXPORTED_PHOTOS_FILE)
            .exportedPhotosAt(UPDATED_EXPORTED_PHOTOS_AT)
            .exportingPhotos(UPDATED_EXPORTING_PHOTOS)
            .colorTheme(UPDATED_COLOR_THEME)
            .postDefaultPublic(UPDATED_POST_DEFAULT_PUBLIC);

        restUserAccountMockMvc.perform(put("/api/user-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserAccount)))
            .andExpect(status().isOk());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeUpdate);
        UserAccount testUserAccount = userAccountList.get(userAccountList.size() - 1);
        assertThat(testUserAccount.getSerializedPrivateKey()).isEqualTo(UPDATED_SERIALIZED_PRIVATE_KEY);
        assertThat(testUserAccount.isGettingStarted()).isEqualTo(UPDATED_GETTING_STARTED);
        assertThat(testUserAccount.isDisableMail()).isEqualTo(UPDATED_DISABLE_MAIL);
        assertThat(testUserAccount.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testUserAccount.getRememberCreatedAt()).isEqualTo(UPDATED_REMEMBER_CREATED_AT);
        assertThat(testUserAccount.getSignInCount()).isEqualTo(UPDATED_SIGN_IN_COUNT);
        assertThat(testUserAccount.getCurrentSignInAt()).isEqualTo(UPDATED_CURRENT_SIGN_IN_AT);
        assertThat(testUserAccount.getLastSignInAt()).isEqualTo(UPDATED_LAST_SIGN_IN_AT);
        assertThat(testUserAccount.getCurrentSignInIp()).isEqualTo(UPDATED_CURRENT_SIGN_IN_IP);
        assertThat(testUserAccount.getLastSignInIp()).isEqualTo(UPDATED_LAST_SIGN_IN_IP);
        assertThat(testUserAccount.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserAccount.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testUserAccount.getLockedAt()).isEqualTo(UPDATED_LOCKED_AT);
        assertThat(testUserAccount.isShowCommunitySpotlightInStream()).isEqualTo(UPDATED_SHOW_COMMUNITY_SPOTLIGHT_IN_STREAM);
        assertThat(testUserAccount.isAutoFollowBack()).isEqualTo(UPDATED_AUTO_FOLLOW_BACK);
        assertThat(testUserAccount.getAutoFollowBackAspectId()).isEqualTo(UPDATED_AUTO_FOLLOW_BACK_ASPECT_ID);
        assertThat(testUserAccount.getHiddenShareables()).isEqualTo(UPDATED_HIDDEN_SHAREABLES);
        assertThat(testUserAccount.getLastSeen()).isEqualTo(UPDATED_LAST_SEEN);
        assertThat(testUserAccount.getExportE()).isEqualTo(UPDATED_EXPORT_E);
        assertThat(testUserAccount.getExportedAt()).isEqualTo(UPDATED_EXPORTED_AT);
        assertThat(testUserAccount.isExporting()).isEqualTo(UPDATED_EXPORTING);
        assertThat(testUserAccount.isStripExif()).isEqualTo(UPDATED_STRIP_EXIF);
        assertThat(testUserAccount.getExportedPhotosFile()).isEqualTo(UPDATED_EXPORTED_PHOTOS_FILE);
        assertThat(testUserAccount.getExportedPhotosAt()).isEqualTo(UPDATED_EXPORTED_PHOTOS_AT);
        assertThat(testUserAccount.isExportingPhotos()).isEqualTo(UPDATED_EXPORTING_PHOTOS);
        assertThat(testUserAccount.getColorTheme()).isEqualTo(UPDATED_COLOR_THEME);
        assertThat(testUserAccount.isPostDefaultPublic()).isEqualTo(UPDATED_POST_DEFAULT_PUBLIC);

        // Validate the UserAccount in Elasticsearch
        UserAccount userAccountEs = userAccountSearchRepository.findOne(testUserAccount.getId());
        assertThat(userAccountEs).isEqualToComparingFieldByField(testUserAccount);
    }

    @Test
    @Transactional
    public void updateNonExistingUserAccount() throws Exception {
        int databaseSizeBeforeUpdate = userAccountRepository.findAll().size();

        // Create the UserAccount

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserAccountMockMvc.perform(put("/api/user-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAccount)))
            .andExpect(status().isCreated());

        // Validate the UserAccount in the database
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserAccount() throws Exception {
        // Initialize the database
        userAccountService.save(userAccount);

        int databaseSizeBeforeDelete = userAccountRepository.findAll().size();

        // Get the userAccount
        restUserAccountMockMvc.perform(delete("/api/user-accounts/{id}", userAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean userAccountExistsInEs = userAccountSearchRepository.exists(userAccount.getId());
        assertThat(userAccountExistsInEs).isFalse();

        // Validate the database is empty
        List<UserAccount> userAccountList = userAccountRepository.findAll();
        assertThat(userAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchUserAccount() throws Exception {
        // Initialize the database
        userAccountService.save(userAccount);

        // Search the userAccount
        restUserAccountMockMvc.perform(get("/api/_search/user-accounts?query=id:" + userAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].serializedPrivateKey").value(hasItem(DEFAULT_SERIALIZED_PRIVATE_KEY.toString())))
            .andExpect(jsonPath("$.[*].gettingStarted").value(hasItem(DEFAULT_GETTING_STARTED.booleanValue())))
            .andExpect(jsonPath("$.[*].disableMail").value(hasItem(DEFAULT_DISABLE_MAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].rememberCreatedAt").value(hasItem(sameInstant(DEFAULT_REMEMBER_CREATED_AT))))
            .andExpect(jsonPath("$.[*].signInCount").value(hasItem(DEFAULT_SIGN_IN_COUNT)))
            .andExpect(jsonPath("$.[*].currentSignInAt").value(hasItem(sameInstant(DEFAULT_CURRENT_SIGN_IN_AT))))
            .andExpect(jsonPath("$.[*].lastSignInAt").value(hasItem(sameInstant(DEFAULT_LAST_SIGN_IN_AT))))
            .andExpect(jsonPath("$.[*].currentSignInIp").value(hasItem(DEFAULT_CURRENT_SIGN_IN_IP.toString())))
            .andExpect(jsonPath("$.[*].lastSignInIp").value(hasItem(DEFAULT_LAST_SIGN_IN_IP.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))))
            .andExpect(jsonPath("$.[*].lockedAt").value(hasItem(sameInstant(DEFAULT_LOCKED_AT))))
            .andExpect(jsonPath("$.[*].showCommunitySpotlightInStream").value(hasItem(DEFAULT_SHOW_COMMUNITY_SPOTLIGHT_IN_STREAM.booleanValue())))
            .andExpect(jsonPath("$.[*].autoFollowBack").value(hasItem(DEFAULT_AUTO_FOLLOW_BACK.booleanValue())))
            .andExpect(jsonPath("$.[*].autoFollowBackAspectId").value(hasItem(DEFAULT_AUTO_FOLLOW_BACK_ASPECT_ID)))
            .andExpect(jsonPath("$.[*].hiddenShareables").value(hasItem(DEFAULT_HIDDEN_SHAREABLES.toString())))
            .andExpect(jsonPath("$.[*].lastSeen").value(hasItem(sameInstant(DEFAULT_LAST_SEEN))))
            .andExpect(jsonPath("$.[*].exportE").value(hasItem(DEFAULT_EXPORT_E.toString())))
            .andExpect(jsonPath("$.[*].exportedAt").value(hasItem(sameInstant(DEFAULT_EXPORTED_AT))))
            .andExpect(jsonPath("$.[*].exporting").value(hasItem(DEFAULT_EXPORTING.booleanValue())))
            .andExpect(jsonPath("$.[*].stripExif").value(hasItem(DEFAULT_STRIP_EXIF.booleanValue())))
            .andExpect(jsonPath("$.[*].exportedPhotosFile").value(hasItem(DEFAULT_EXPORTED_PHOTOS_FILE.toString())))
            .andExpect(jsonPath("$.[*].exportedPhotosAt").value(hasItem(sameInstant(DEFAULT_EXPORTED_PHOTOS_AT))))
            .andExpect(jsonPath("$.[*].exportingPhotos").value(hasItem(DEFAULT_EXPORTING_PHOTOS.booleanValue())))
            .andExpect(jsonPath("$.[*].colorTheme").value(hasItem(DEFAULT_COLOR_THEME.toString())))
            .andExpect(jsonPath("$.[*].postDefaultPublic").value(hasItem(DEFAULT_POST_DEFAULT_PUBLIC.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAccount.class);
        UserAccount userAccount1 = new UserAccount();
        userAccount1.setId(1L);
        UserAccount userAccount2 = new UserAccount();
        userAccount2.setId(userAccount1.getId());
        assertThat(userAccount1).isEqualTo(userAccount2);
        userAccount2.setId(2L);
        assertThat(userAccount1).isNotEqualTo(userAccount2);
        userAccount1.setId(null);
        assertThat(userAccount1).isNotEqualTo(userAccount2);
    }
}
