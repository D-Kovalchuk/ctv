package com.ctv.registration.rest
import com.ctv.registration.adapter.rest.UserMvcAdapter
import com.ctv.registration.adapter.rest.dto.User
import com.ctv.registration.core.exception.ResourceNotFoundException
import com.ctv.registration.core.exception.DataConflictException
import com.ctv.registration.rest.config.RestTestConfig
import com.ctv.registration.rest.config.SecurityTestConfig
import com.ctv.test.EmbeddedRedis
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

import javax.servlet.Filter

import static com.ctv.registration.core.exception.ErrorData.USERNAME_ALREADY_EXISTS
import static com.ctv.registration.core.exception.ErrorData.USER_ID_NOT_FOUND
import static com.ctv.registration.rest.Endpoint.*
import static com.ctv.test.Converters.toJson
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.util.ReflectionTestUtils.setField
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup
/**
 * @author Dmitry Kovalchuk
 */
@ContextConfiguration(classes = [SecurityTestConfig, RestTestConfig])
@WebAppConfiguration
class UserControllerTest extends Specification {

    @Rule
    EmbeddedRedis embeddedRedis = new EmbeddedRedis(6379);

    @Autowired
    WebApplicationContext wac;

    @Autowired
    Filter springSecurityFilterChain;

    UserMvcAdapter userMvcAdapter = Mock(UserMvcAdapter)

    @Autowired
    UserController userController

    MockMvc mockMvc

    private static final VALID_USER = createValidUser()

    private static final USER_WITHOUT_PASSWORD = createUserWithoutPassword()


    void setup() {
        setField(userController, "userMvcAdapter", userMvcAdapter)
        this.mockMvc = webAppContextSetup(this.wac)
                .addFilters(springSecurityFilterChain)
                .alwaysDo(print())
                .build();
    }

    def "create user"() {
        when:
        mockMvc.perform(post(USER_PATH)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(toJson(VALID_USER)))
                .andExpect(status().isCreated());

        then:
        1 * userMvcAdapter.createUser(_)
    }

    def "create user when user with that username already exists"() {
        given:
        def user = createValidUser()
        userMvcAdapter.createUser(_) >> { throw new DataConflictException(USERNAME_ALREADY_EXISTS) } //todo why exception doesn't throw if user parameter used?

        when:
        def resultActions = mockMvc.perform(post(USER_PATH)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(toJson(user)))

        then:
        resultActions
                .andExpect(status().isConflict());

    }

    @Ignore("@AuthenticationPrincipal does not support yet")
    @WithUserDetails
    def "delete user"() {
        when:
        mockMvc.perform(delete(USER_PATH).header(X_AUTH_TOKEN, X_AUTH_TOKEN))
                .andExpect(status().isNoContent());

        then:
        1 * userMvcAdapter.deleteUser(1)
    }

    def "update user"() {

        when:
        def resultActions = mockMvc.perform(put(USER_PATH)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(toJson(VALID_USER))
                .header(X_AUTH_TOKEN, X_AUTH_TOKEN))

        then:
        resultActions.andExpect(status().isOk());

        and:
        1 * userMvcAdapter.updateUser(_)
    }

    @Unroll
    def "find user when id=#id should return user without password"() {
        given:
        1 * userMvcAdapter.findUserById(id) >> VALID_USER

        when:
        def resultActions = mockMvc.perform(get(USER_PATH + BY_ID, id as String)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))

        then:
        resultActions
                .andExpect(content().string(toJson(USER_WITHOUT_PASSWORD)))
                .andExpect(status().isOk())

        where:
        id = 1
    }

    @Unroll
    def "when user by id=#id not found should return NOT_FOUND statues"() {
        given:
        userMvcAdapter.findUserById(id) >> { throw new ResourceNotFoundException(USER_ID_NOT_FOUND) }

        when:
        def resultActions = mockMvc.perform(get(USER_PATH + BY_ID, id as String)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))

        then:
        resultActions.andExpect(status().isNotFound())

        where:
        id = 1
    }

    def "find all users"() {
        given:
        def users = [VALID_USER]

        when:
        def resultActions = mockMvc.perform(get(USER_PATH)
                .param(PAGE_PARAM, START_PAGE)
                .param(SIZE_PARAM, PAGE_SIZE)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))

        then:
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().string(toJson([USER_WITHOUT_PASSWORD])))

        and:
        1 * userMvcAdapter.findAllUsers(Integer.valueOf(START_PAGE), Integer.valueOf(PAGE_SIZE)) >> users
    }


    def "find all users with default parameters"() {
        given:
        def users = [VALID_USER]

        when:
        def resultActions = mockMvc.perform(get(USER_PATH)
                .param(PAGE_PARAM, "")
                .param(SIZE_PARAM, "")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))

        then:
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().string(toJson([USER_WITHOUT_PASSWORD])))

        and:
        1 * userMvcAdapter.findAllUsers(Integer.valueOf(START_PAGE), Integer.valueOf(PAGE_SIZE)) >> users
    }

    def "update password"() {
        given:
        Integer id = 1
        String oldPass = "oldPassword"
        String newPass = "newPassword"

        when:
        def resultActions = mockMvc.perform(put(USER_PATH + PASSWORD, id)
                .param(OLD_PASSWORD, oldPass)
                .param(NEW_PASSWORD, newPass)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))

        then:
        resultActions
                .andExpect(status().isOk())

        and:
        1 * userMvcAdapter.updatePassword(id, oldPass, newPass)
    }

    private static User createValidUser() {
        User user = new User();
        user.id = 1
        user.email = "email@gmail.com"
        user.password = "password"
        user.type = "organizer"
        user.username = "username"
        user
    }

    private static User createUserWithoutPassword() {
        User user = new User();
        user.id = 1
        user.email = "email@gmail.com"
        user.type = "organizer"
        user.username = "username"
        user
    }


}
