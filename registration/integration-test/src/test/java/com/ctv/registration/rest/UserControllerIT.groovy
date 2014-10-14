package com.ctv.registration.rest
import com.ctv.registration.adapter.rest.dto.User
import com.ctv.registration.rest.config.RegistrationSecurityConfig
import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.ExpectedDatabase
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.transaction.TransactionConfiguration
import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import org.springframework.transaction.annotation.Transactional
import spock.lang.Ignore

import static com.ctv.test.Converters.toJson
import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT
import static org.springframework.http.HttpStatus.CREATED
/**
 * @author Timur Yarosh
 */
@Transactional
@ContextConfiguration(classes = [RegistrationSecurityConfig, PersistenceTestConfig])
@TransactionConfiguration(defaultRollback = false)
@TestExecutionListeners([
        DependencyInjectionTestExecutionListener,
        DbUnitTestExecutionListener,
        TransactionalTestExecutionListener])
class UserControllerIT extends AbstractIntegrationSpecification {

    public static final String NEW_USERNAME = "username2"
    public static final String NEW_EMAIL = "username2@company.com"
    public static final String NEW_PASSWORD = "password"
    public static final String NEW_COMPANY = "http://company.com"
    public static final String NEW_TYPE = "ROLE_WATCHER"

    @Ignore("not finished yet")
    @ExpectedDatabase(value = "/dataset/userCreated.xml", assertionMode = NON_STRICT)
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
        def userJson = toJson(user)

        when:
        def response = restClient.post([
                path       : "http://localhost:8099/users",
                contentType: "application/json",
                body       : userJson
        ])

        then:
        with(response) {
            status == CREATED.value()
        }
    }
}
