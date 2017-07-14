package com.sgkhmjaes.jdias.web.rest;

import com.sgkhmjaes.jdias.JDiasApp;

import com.sgkhmjaes.jdias.domain.Profile;
import com.sgkhmjaes.jdias.domain.User;
import com.sgkhmjaes.jdias.domain.UserAccount;
import com.sgkhmjaes.jdias.repository.PersonRepository;
import com.sgkhmjaes.jdias.repository.ProfileRepository;
import com.sgkhmjaes.jdias.repository.UserRepository;
import com.sgkhmjaes.jdias.service.ProfileService;
import com.sgkhmjaes.jdias.repository.search.ProfileSearchRepository;
import com.sgkhmjaes.jdias.security.SecurityUtils;
import com.sgkhmjaes.jdias.service.PersonService;
import com.sgkhmjaes.jdias.service.UserService;
import static com.sgkhmjaes.jdias.web.rest.UserAccountResourceIntTest.createEntity;
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
import org.junit.After;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProfileResource REST controller.
 *
 * @see ProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JDiasApp.class)
public class ProfileResourceIntTest {

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL_SMALL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL_SMALL = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL_MEDIUM = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL_MEDIUM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTHDAY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDAY = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_BIO = "AAAAAAAAAA";
    private static final String UPDATED_BIO = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SEARCHABLE = false;
    private static final Boolean UPDATED_SEARCHABLE = true;

    private static final Boolean DEFAULT_NSFW = false;
    private static final Boolean UPDATED_NSFW = true;

    private static final String DEFAULT_TAG_STRING = "AAAAAAAAAA";
    private static final String UPDATED_TAG_STRING = "BBBBBBBBBB";
    
    private static Long userID;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileSearchRepository profileSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProfileMockMvc;

    private Profile profile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProfileResource profileResource = new ProfileResource(profileService);
        this.restProfileMockMvc = MockMvcBuilders.standaloneSetup(profileResource)
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
    public static Profile createEntity() {
        Profile profile = new Profile()
            .author(DEFAULT_AUTHOR)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .imageUrl(DEFAULT_IMAGE_URL)
            .imageUrlSmall(DEFAULT_IMAGE_URL_SMALL)
            .imageUrlMedium(DEFAULT_IMAGE_URL_MEDIUM)
            .birthday(DEFAULT_BIRTHDAY)
            .gender(DEFAULT_GENDER)
            .bio(DEFAULT_BIO)
            .location(DEFAULT_LOCATION)
            .searchable(DEFAULT_SEARCHABLE)
            .nsfw(DEFAULT_NSFW)
            .tagString(DEFAULT_TAG_STRING);
        return profile;
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
        
        profileSearchRepository.deleteAll();
        //profile = createEntity(em);
    }
    
    @After
    public void deleteCreatedAccount(){
        userService.deleteUser("johndoe");
    }

/*
    @Test
    @Transactional
    public void createProfile() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate + 1);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testProfile.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testProfile.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testProfile.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testProfile.getImageUrlSmall()).isEqualTo(DEFAULT_IMAGE_URL_SMALL);
        assertThat(testProfile.getImageUrlMedium()).isEqualTo(DEFAULT_IMAGE_URL_MEDIUM);
        assertThat(testProfile.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testProfile.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testProfile.getBio()).isEqualTo(DEFAULT_BIO);
        assertThat(testProfile.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testProfile.isSearchable()).isEqualTo(DEFAULT_SEARCHABLE);
        assertThat(testProfile.isNsfw()).isEqualTo(DEFAULT_NSFW);
        assertThat(testProfile.getTagString()).isEqualTo(DEFAULT_TAG_STRING);

        // Validate the Profile in Elasticsearch
        Profile profileEs = profileSearchRepository.findOne(testProfile.getId());
        assertThat(profileEs).isEqualToComparingFieldByField(testProfile);
    }

    @Test
    @Transactional
    public void createProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile with an existing ID
        profile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate);
    }
*/
    @Test
    @Transactional
    public void getAllProfiles() throws Exception {
        // Initialize the database
        //profileRepository.saveAndFlush(profile);
        
        Profile createEntity = createEntity();
        createEntity.setId(userID);
        profileRepository.saveAndFlush(createEntity);
        profile = profileRepository.findOne(userID);
        profileSearchRepository.save(profile);

        // Get all the profileList
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].imageUrlSmall").value(hasItem(DEFAULT_IMAGE_URL_SMALL.toString())))
            .andExpect(jsonPath("$.[*].imageUrlMedium").value(hasItem(DEFAULT_IMAGE_URL_MEDIUM.toString())))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].bio").value(hasItem(DEFAULT_BIO.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].searchable").value(hasItem(DEFAULT_SEARCHABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].nsfw").value(hasItem(DEFAULT_NSFW.booleanValue())))
            .andExpect(jsonPath("$.[*].tagString").value(hasItem(DEFAULT_TAG_STRING.toString())));
    }

    @Test
    @Transactional
    public void getProfile() throws Exception {
        // Initialize the database
        //profileRepository.saveAndFlush(profile);
        
        Profile createEntity = createEntity();
        createEntity.setId(userID);
        profileRepository.saveAndFlush(createEntity);
        profile = profileRepository.findOne(userID);
        profileSearchRepository.save(profile);

        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profile.getId().intValue()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()))
            .andExpect(jsonPath("$.imageUrlSmall").value(DEFAULT_IMAGE_URL_SMALL.toString()))
            .andExpect(jsonPath("$.imageUrlMedium").value(DEFAULT_IMAGE_URL_MEDIUM.toString()))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.bio").value(DEFAULT_BIO.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.searchable").value(DEFAULT_SEARCHABLE.booleanValue()))
            .andExpect(jsonPath("$.nsfw").value(DEFAULT_NSFW.booleanValue()))
            .andExpect(jsonPath("$.tagString").value(DEFAULT_TAG_STRING.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProfile() throws Exception {
        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfile() throws Exception {
        // Initialize the database
        //profileService.save(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile
        Profile updatedProfile = profileRepository.findOne(userID);
        updatedProfile
            .author(UPDATED_AUTHOR)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .imageUrl(UPDATED_IMAGE_URL)
            .imageUrlSmall(UPDATED_IMAGE_URL_SMALL)
            .imageUrlMedium(UPDATED_IMAGE_URL_MEDIUM)
            .birthday(UPDATED_BIRTHDAY)
            .gender(UPDATED_GENDER)
            .bio(UPDATED_BIO)
            .location(UPDATED_LOCATION)
            .searchable(UPDATED_SEARCHABLE)
            .nsfw(UPDATED_NSFW)
            .tagString(UPDATED_TAG_STRING);

        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProfile)))
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testProfile.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testProfile.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testProfile.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testProfile.getImageUrlSmall()).isEqualTo(UPDATED_IMAGE_URL_SMALL);
        assertThat(testProfile.getImageUrlMedium()).isEqualTo(UPDATED_IMAGE_URL_MEDIUM);
        assertThat(testProfile.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testProfile.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testProfile.getBio()).isEqualTo(UPDATED_BIO);
        assertThat(testProfile.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testProfile.isSearchable()).isEqualTo(UPDATED_SEARCHABLE);
        assertThat(testProfile.isNsfw()).isEqualTo(UPDATED_NSFW);
        assertThat(testProfile.getTagString()).isEqualTo(UPDATED_TAG_STRING);

        // Validate the Profile in Elasticsearch
        Profile profileEs = profileSearchRepository.findOne(testProfile.getId());
        assertThat(profileEs).isEqualToComparingFieldByField(testProfile);
    }
/*
    @Test
    @Transactional
    public void updateNonExistingProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Create the Profile

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProfile() throws Exception {
        // Initialize the database
        //profileService.save(profile);

        int databaseSizeBeforeDelete = profileRepository.findAll().size();

        // Get the profile
        restProfileMockMvc.perform(delete("/api/profiles/{id}", profile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean profileExistsInEs = profileSearchRepository.exists(profile.getId());
        assertThat(profileExistsInEs).isFalse();

        // Validate the database is empty
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeDelete - 1);
    }
*/
    @Test
    @Transactional
    public void searchProfile() throws Exception {
        // Initialize the database
        //profileService.save(profile);
        
        Profile createEntity = createEntity();
        createEntity.setId(userID);
        profileRepository.saveAndFlush(createEntity);
        profile = profileRepository.findOne(userID);
        profileSearchRepository.save(profile);

        // Search the profile
        restProfileMockMvc.perform(get("/api/_search/profiles?query=id:" + profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())))
            .andExpect(jsonPath("$.[*].imageUrlSmall").value(hasItem(DEFAULT_IMAGE_URL_SMALL.toString())))
            .andExpect(jsonPath("$.[*].imageUrlMedium").value(hasItem(DEFAULT_IMAGE_URL_MEDIUM.toString())))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].bio").value(hasItem(DEFAULT_BIO.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].searchable").value(hasItem(DEFAULT_SEARCHABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].nsfw").value(hasItem(DEFAULT_NSFW.booleanValue())))
            .andExpect(jsonPath("$.[*].tagString").value(hasItem(DEFAULT_TAG_STRING.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profile.class);
        Profile profile1 = new Profile();
        profile1.setId(1L);
        Profile profile2 = new Profile();
        profile2.setId(profile1.getId());
        assertThat(profile1).isEqualTo(profile2);
        profile2.setId(2L);
        assertThat(profile1).isNotEqualTo(profile2);
        profile1.setId(null);
        assertThat(profile1).isNotEqualTo(profile2);
    }
}
