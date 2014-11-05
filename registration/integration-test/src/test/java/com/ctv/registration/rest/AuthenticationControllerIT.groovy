package com.ctv.registration.rest
import com.ctv.registration.PersistenceTestConfig
import com.ctv.registration.adapter.rest.dto.AuthenticationRequest
import com.ctv.registration.core.config.RegistrationSecurityConfig
import com.github.springtestdbunit.DbUnitTestExecutionListener
import com.github.springtestdbunit.annotation.DatabaseSetup
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.session.ExpiringSession
import org.springframework.session.SessionRepository
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener

import static com.ctv.registration.rest.Endpoint.TOKEN_PATH
import static com.ctv.test.Converters.toJson
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
/**
 * @author Timur Yarosh
 */
@ContextConfiguration(classes = [RegistrationSecurityConfig, PersistenceTestConfig])
@TestExecutionListeners([
        DependencyInjectionTestExecutionListener,
        DbUnitTestExecutionListener])
class AuthenticationControllerIT extends AbstractIntegrationSpecification {

    static final USERNAME = "username";
    static final PASSWORD = "password";
    static final WRONG_USERNAME = "wrong-username";
    static final TOKEN_HEADER = "x-auth-token";
    static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";

    @Autowired
    SessionRepository<? extends ExpiringSession> sessionRepository

    @DatabaseSetup(value = "/dataset/userCreated.xml")
    def "should authenticate user if credentials is good"() {
        given:
        def request = new AuthenticationRequest(USERNAME, PASSWORD)

        when:
        def response = restClient.post([
                path              : TOKEN_PATH,
                body              : toJson(request),
                requestContentType: APPLICATION_JSON_VALUE])

        then:
        with(response as HttpResponseDecorator) {
            status == HttpStatus.CREATED.value()
            headers[TOKEN_HEADER] != null
            data == null
        }

        and:
        sessionRepository.getSession(response.headers[TOKEN_HEADER].value) != null
    }

    @DatabaseSetup(value = "/dataset/userCreated.xml")
    def "should fail if credentials is bad"() {
        given:
        def request = new AuthenticationRequest(WRONG_USERNAME, PASSWORD)

        when:
        restClient.post([
                path              : TOKEN_PATH,
                body              : toJson(request),
                requestContentType: APPLICATION_JSON_VALUE])

        then:
        HttpResponseException e = thrown()
        with(e.response as HttpResponseDecorator) {
            status == HttpStatus.UNAUTHORIZED.value()
            headers[TOKEN_HEADER] == null
            data == [code: 1102, message: "Bad credentials"]
        }
    }

    def "should logout"() {
        given:
        def session = sessionRepository.createSession()
        sessionRepository.save(session)
        def token = session.getId()

        when:
        restClient.delete([
                path   : TOKEN_PATH,
                headers: ["${TOKEN_HEADER}": token]
        ])

        then:
        sessionRepository.getSession(token).getAttribute(SPRING_SECURITY_CONTEXT) == null
    }
}
