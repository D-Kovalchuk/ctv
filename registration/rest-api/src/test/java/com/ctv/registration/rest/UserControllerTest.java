package com.ctv.registration.rest;


import com.ctv.registration.adapter.rest.UserMvcAdapter;
import com.ctv.registration.adapter.rest.dto.User;
import com.ctv.registration.core.exception.UserIdNotFoundException;
import com.ctv.registration.core.exception.UsernameAlreadyExistsException;
import com.ctv.test.EmbeddedRedis;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static com.ctv.registration.rest.Endpoint.*;
import static com.ctv.test.Converters.toJson;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {SecurityTestConfig.class, RestTestConfig.class})
@WebAppConfiguration
public class UserControllerTest {

    @Rule
    public EmbeddedRedis embeddedRedis = new EmbeddedRedis(6379);

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private UserMvcAdapter userMvcAdapter;

    private MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(this.wac)
                .addFilters(springSecurityFilterChain)
                .alwaysDo(print())
                .build();
    }

    @Test
    public void createUser() throws Exception {
        User user = new User();
        String content = toJson(user);

        mockMvc.perform(post(USER_PATH)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated());

        verify(userMvcAdapter).createUser(any(User.class));
    }

    @Test
    public void createUserWhenUserWithThatUsernameAlreadyExists() throws Exception {
        User user = new User();
        String content = toJson(user);

        doThrow(UsernameAlreadyExistsException.class).when(userMvcAdapter).createUser(any(User.class));

        mockMvc.perform(post(USER_PATH)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(content))
                .andExpect(status().isConflict());
    }

    @Ignore("@AuthenticationPrincipal does not support yet")
    @Test
    @WithUserDetails
    public void deleteUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        mockMvc.perform(delete(USER_PATH).with(authentication(authentication)).header(X_AUTH_TOKEN, X_AUTH_TOKEN))
                .andExpect(status().isNoContent());

        verify(userMvcAdapter).deleteUser(1);
    }

    @Test
    public void updateUser() throws Exception {
        User user = new User();
        String content = toJson(user);

        mockMvc.perform(put(USER_PATH)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(content)
                .header(X_AUTH_TOKEN, X_AUTH_TOKEN))
                .andExpect(status().isOk());

        verify(userMvcAdapter).updateUser(any(User.class));
    }

    @Test
    public void findUser() throws Exception {
        User user = new User();
        String content = toJson(user);
        when(userMvcAdapter.findUserById(1)).thenReturn(user);

        mockMvc.perform(get(USER_PATH + BY_ID, "1")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(content));

        verify(userMvcAdapter).findUserById(1);
    }

    @Test
    public void findUserWhenUserNotFoundExceptionThrown() throws Exception {
        when(userMvcAdapter.findUserById(1)).thenThrow(UserIdNotFoundException.class);

        mockMvc.perform(get(USER_PATH + BY_ID, "1")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findAllUsers() throws Exception {
        mockMvc.perform(get(USER_PATH).param(PAGE_PARAM, START_PAGE).param(SIZE_PARAM, PAGE_SIZE)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));

        verify(userMvcAdapter).findAllUsers(Integer.valueOf(START_PAGE), Integer.valueOf(PAGE_SIZE));
    }

}