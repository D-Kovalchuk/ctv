package com.ctv.registration.rest;

import com.ctv.registration.adapter.rest.dto.AuthenticationRequest;
import com.ctv.registration.rest.dto.ErrorInfo;
import com.ctv.test.EmbeddedRedis;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.http.HttpStatus;
import org.springframework.session.ExpiringSession;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.io.IOException;

import static com.ctv.registration.rest.Endpoint.TOKEN_PATH;
import static com.ctv.registration.rest.Endpoint.X_AUTH_TOKEN;
import static com.ctv.test.Converters.toJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Dmitry Kovalchuk
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SecurityTestConfig.class, RestTestConfig.class})
@WebAppConfiguration
public class AuthenticationControllerTest {

    public static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String WRONG_USERNAME = "wrong-username";
    public static final String WRONG_PASSWORD = "wrong-password";

    @Rule
    public EmbeddedRedis embeddedRedis = new EmbeddedRedis(6379);

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    SessionRepository<? extends ExpiringSession> sessionRepository;

    @Autowired
    RedisOperations<String, ExpiringSession> redisOperations;

    private MockMvc mockMvc;


    @Before
    public void setup() throws IOException {
        this.mockMvc = webAppContextSetup(this.wac)
                .addFilters(springSecurityFilterChain)
                .alwaysDo(print())
                .build();
    }

    @Test
    public void authenticateUserWhenCredentialsIsGood() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest(USERNAME, PASSWORD);
        ObjectMapper mapper = new ObjectMapper();
        String content1 = mapper.writeValueAsString(request);
        mockMvc.perform(post(TOKEN_PATH).content(content1)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(header().string(X_AUTH_TOKEN, any(String.class)));
    }

    @Test
    public void authenticateUserWhenCredentialsIsBad() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest(WRONG_USERNAME, WRONG_PASSWORD);

        ErrorInfo errorInfo = new ErrorInfo(HttpStatus.UNAUTHORIZED.value(), "Bad credentials");

        mockMvc.perform(post(TOKEN_PATH).content(toJson(request))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andExpect(header().doesNotExist(X_AUTH_TOKEN))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(toJson(errorInfo)));
    }

    @Test
    public void logout() throws Exception {
        String sessionId = "9ce60ab3-3566-4dfd-a991-b2869966f5e8";
        String key = "spring:session:sessions:" + sessionId;
        redisOperations.boundHashOps(key).put("sessionAttr:" + SPRING_SECURITY_CONTEXT, "security context");

        mockMvc.perform(delete(TOKEN_PATH).header(X_AUTH_TOKEN, sessionId));

        Session session1 = sessionRepository.getSession(sessionId);
        assertThat(session1.getAttribute(SPRING_SECURITY_CONTEXT)).isNull();
    }

}
