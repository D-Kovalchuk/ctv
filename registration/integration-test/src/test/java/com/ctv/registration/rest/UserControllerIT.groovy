package com.ctv.registration.rest
import com.ctv.registration.PersistenceTestConfig
import com.ctv.registration.adapter.rest.dto.User
import com.ctv.registration.rest.config.RegistrationSecurityConfig
import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.ExpectedDatabase
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import spock.lang.Ignore

import static com.ctv.registration.rest.Endpoint.USER_PATH
import static com.ctv.test.Converters.toJson
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
/**
 * @author Timur Yarosh
 */
@ContextConfiguration(classes = [RegistrationSecurityConfig, PersistenceTestConfig])
@TestExecutionListeners([
        DependencyInjectionTestExecutionListener,
        DbUnitTestExecutionListener])
class UserControllerIT extends AbstractIntegrationSpecification {

    public static final String NEW_USERNAME = "username2"
    public static final String NEW_EMAIL = "username2@company.com"
    public static final String NEW_PASSWORD = "password"
    public static final String NEW_COMPANY = "http://company.com"
    public static final String NEW_TYPE = "ROLE_WATCHER"

    @Ignore("not finished yet")
    @ExpectedDatabase(value = "/dataset/userCreated.xml")
    def "should save new user into database"() {
        given:
        def user = new User()
        user.with {
            it.username = NEW_USERNAME
            it.email = NEW_EMAIL
            it.password = NEW_PASSWORD
            it.site = NEW_COMPANY
            it.type = NEW_TYPE
        }

        when:
        def response = restClient.post([
                path       : USER_PATH,
                contentType: APPLICATION_JSON_VALUE,
                body       : toJson(user)
        ])

        then:
        with(response) {
            status == CREATED.value()
        }
    }
}
