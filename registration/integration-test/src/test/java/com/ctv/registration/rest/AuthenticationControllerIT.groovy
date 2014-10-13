package com.ctv.registration.rest

import com.ctv.registration.persistence.config.PersistenceConfig
import com.ctv.registration.rest.config.RegistrationSecurityConfig
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.session.ExpiringSession
import org.springframework.session.SessionRepository
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

/**
 * @author Timur Yarosh
 */
@ContextConfiguration(classes = [RegistrationSecurityConfig, PersistenceConfig])
class AuthenticationControllerIT extends Specification {

    def final jettyPort = System.getProperty("jetty.port")
    def final rest = new RESTClient("http://localhost:${jettyPort}/")
    static final USERNAME = "username";
    static final PASSWORD = "password";
    static final WRONG_USERNAME = "wrong-username";
    static final TOKENS_ENDPOINT = "http://localhost:8099/tokens";
    static final TOKEN_HEADER = "x-auth-token";
    static final String WRONG_TOKEN = "wrongToken"
    static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";

    @Autowired
    SessionRepository<? extends ExpiringSession> sessionRepository

    def "should authenticate user if credentials is good"() {
        when:
        def response = rest.post([
                path              : TOKENS_ENDPOINT,
                body              : "{\"username\":\"${USERNAME}\", \"password\":\"${PASSWORD}\"}",
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

    def "should fail if credentials is bad"() {
        when:
        rest.post([
                path              : TOKENS_ENDPOINT,
                body              : "{\"username\":\"${WRONG_USERNAME}\", \"password\":\"${PASSWORD}\"}",
                requestContentType: APPLICATION_JSON_VALUE])

        then:
        HttpResponseException e = thrown()
        with(e.response as HttpResponseDecorator) {
            status == HttpStatus.UNAUTHORIZED.value()
            headers[TOKEN_HEADER] == null
            data == [code: 401, message: "Bad credentials"]
        }
    }

    def "should logout"() {
        setup:
        def session = sessionRepository.createSession()
        sessionRepository.save(session)
        def token = session.getId()

        rest.delete([
                path   : TOKENS_ENDPOINT,
                headers: ["${TOKEN_HEADER}": token]
        ])

        expect:
        sessionRepository.getSession(token).getAttribute(SPRING_SECURITY_CONTEXT) == null
    }
}
