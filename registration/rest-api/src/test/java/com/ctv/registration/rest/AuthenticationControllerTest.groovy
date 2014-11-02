package com.ctv.registration.rest
import com.ctv.registration.adapter.rest.dto.AuthenticationRequest
import com.ctv.registration.rest.config.RestTestConfig
import com.ctv.registration.rest.config.SecurityTestConfig
import com.ctv.registration.rest.dto.ErrorInfo
import com.ctv.test.EmbeddedRedis
import org.junit.ClassRule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisOperations
import org.springframework.session.ExpiringSession
import org.springframework.session.Session
import org.springframework.session.SessionRepository
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification
import spock.lang.Unroll

import javax.servlet.Filter

import static com.ctv.registration.adapter.rest.Constraints.PASSWORD_MAXIMUM_LENGTH
import static com.ctv.registration.adapter.rest.Constraints.USERNAME_MAXIMUM_LENGTH
import static com.ctv.registration.core.exception.ErrorData.BAD_CREDENTIALS
import static com.ctv.registration.rest.Endpoint.TOKEN_PATH
import static com.ctv.registration.rest.Endpoint.X_AUTH_TOKEN
import static com.ctv.test.Converters.toJson
import static org.assertj.core.api.Assertions.assertThat
import static org.hamcrest.CoreMatchers.any
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup
/**
 * @author Dmitry Kovalchuk
 */
@WebAppConfiguration
@ContextConfiguration(classes = [SecurityTestConfig, RestTestConfig])
class AuthenticationControllerTest extends Specification {

    static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";
    static final String USERNAME = "username";
    static final String PASSWORD = "password";
    static final String WRONG_USERNAME = "wrong-username";
    static final String WRONG_PASSWORD = "wrong-password";

    @ClassRule
    static EmbeddedRedis embeddedRedis = new EmbeddedRedis(6379);

    @Autowired
    WebApplicationContext wac;

    @Autowired
    Filter springSecurityFilterChain;

    @Autowired
    SessionRepository<? extends ExpiringSession> sessionRepository;

    @Autowired
    RedisOperations<String, ExpiringSession> redisOperations;

    MockMvc mockMvc;

    void setup() {
        this.mockMvc = webAppContextSetup(this.wac)
                .addFilters(springSecurityFilterChain)
                .alwaysDo(print())
                .build();
    }

    void tearDown() throws Exception {
        embeddedRedis.clean();
    }

    def "authenticate user when credentials is good"() {
        when:
        def resultActions = performTokenRequest(USERNAME, PASSWORD)

        then:
        resultActions.andExpect(header().string(X_AUTH_TOKEN, any(String.class)));
    }

    def "authenticate user when credentials is bad"() {
        setup:
        ErrorInfo errorInfo = new ErrorInfo(BAD_CREDENTIALS.code, BAD_CREDENTIALS.message);

        expect:
        performTokenRequest(WRONG_USERNAME, WRONG_PASSWORD)
                .andExpect(header().doesNotExist(X_AUTH_TOKEN))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(toJson(errorInfo)));

    }

    @Unroll
    def "should fail when username = #username or password = #password"() {
        given:
        String violentPassword = "123"

        when:
        def resultActions = performTokenRequest(USERNAME, violentPassword)

        then:
        resultActions.andExpect(status().isConflict())

        //todo check message text
        where:
        username                            | password
        "1"                                 | PASSWORD
        "1" * (USERNAME_MAXIMUM_LENGTH + 1) | PASSWORD
        USERNAME                            | "1"
        USERNAME                            | "1" * (PASSWORD_MAXIMUM_LENGTH + 1)
    }

    def "logout"() {
        given:
        String sessionId = "9ce60ab3-3566-4dfd-a991-b2869966f5e8";
        String key = "spring:session:sessions:" + sessionId;
        redisOperations.boundHashOps(key).put("sessionAttr:" + SPRING_SECURITY_CONTEXT, "security context");

        when:
        mockMvc.perform(delete(TOKEN_PATH).header(X_AUTH_TOKEN, sessionId));

        then:
        Session session1 = sessionRepository.getSession(sessionId);
        assertThat(session1.getAttribute(SPRING_SECURITY_CONTEXT)).isNull();
    }

    private ResultActions performTokenRequest(String username, String password) throws Exception {
        AuthenticationRequest request = new AuthenticationRequest(username, password);
        return mockMvc.perform(post(TOKEN_PATH).content(toJson(request))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON));
    }
}
