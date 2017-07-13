package com.sgkhmjaes.jdias.web.rest;

import io.github.jhipster.config.JHipsterProperties;
import com.sgkhmjaes.jdias.JDiasApp;
import com.sgkhmjaes.jdias.domain.User;
import com.sgkhmjaes.jdias.repository.PersonRepository;
import com.sgkhmjaes.jdias.repository.UserRepository;
import com.sgkhmjaes.jdias.security.SecurityUtils;
import com.sgkhmjaes.jdias.service.PersonService;
import com.sgkhmjaes.jdias.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the ProfileInfoResource REST controller.
 *
 * @see ProfileInfoResource
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JDiasApp.class)
public class ProfileInfoResourceIntTest {
    
    private static Long userID;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Mock
    private Environment environment;

    @Mock
    private JHipsterProperties jHipsterProperties;

    private MockMvc restProfileMockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        String mockProfile[] = {"test"};
        JHipsterProperties.Ribbon ribbon = new JHipsterProperties.Ribbon();
        ribbon.setDisplayOnActiveProfiles(mockProfile);
        when(jHipsterProperties.getRibbon()).thenReturn(ribbon);

        String activeProfiles[] = {"test"};
        when(environment.getDefaultProfiles()).thenReturn(activeProfiles);
        when(environment.getActiveProfiles()).thenReturn(activeProfiles);

        ProfileInfoResource profileInfoResource = new ProfileInfoResource(environment, jHipsterProperties);
        this.restProfileMockMvc = MockMvcBuilders
            .standaloneSetup(profileInfoResource)
            .build();
    }
    
    @Before
    public void initAutorization() {
        
        User user = userService.createUser("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "http://placehold.it/50x50", "en-US");
        user.setActivated(true);
        userRepository.saveAndFlush(user);
        
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("johndoe", "johndoe"));
        SecurityContextHolder.setContext(securityContext);
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get().getId();
        
        userID = user.getId();
            
    }
    
    @After
    public void deleteCreatedAccount(){
        userService.deleteUser("johndoe");
    }

    @Test
    public void getProfileInfoWithRibbon() throws Exception {
        restProfileMockMvc.perform(get("/api/profile-info"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void getProfileInfoWithoutRibbon() throws Exception {
        JHipsterProperties.Ribbon ribbon = new JHipsterProperties.Ribbon();
        ribbon.setDisplayOnActiveProfiles(null);
        when(jHipsterProperties.getRibbon()).thenReturn(ribbon);

        restProfileMockMvc.perform(get("/api/profile-info"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void getProfileInfoWithoutActiveProfiles() throws Exception {
        String emptyProfile[] = {};
        when(environment.getDefaultProfiles()).thenReturn(emptyProfile);
        when(environment.getActiveProfiles()).thenReturn(emptyProfile);

        restProfileMockMvc.perform(get("/api/profile-info"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }
}
