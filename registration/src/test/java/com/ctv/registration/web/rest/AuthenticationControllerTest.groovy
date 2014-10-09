package com.ctv.registration.web.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.context.support.WithSecurityContextTestExcecutionListener
import org.springframework.session.data.redis.RedisOperationsSessionRepository
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener
import org.springframework.test.context.support.DirtiesContextTestExecutionListener
import org.springframework.test.context.transaction.TransactionalTestExecutionListener
import org.springframework.test.context.web.ServletTestExecutionListener
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import spock.lang.Specification

import javax.servlet.Filter

import static com.ctv.registration.web.rest.Endpoint.TOKEN_PATH
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

/**
 * @author Timur Yarosh
 */
@ContextConfiguration(classes = [MvcTestConfig, SecurityTestConfig])
@WebAppConfiguration
@TestExecutionListeners(listeners = [ServletTestExecutionListener,
        DependencyInjectionTestExecutionListener,
        DirtiesContextTestExecutionListener,
        TransactionalTestExecutionListener,
        WithSecurityContextTestExcecutionListener])
class AuthenticationControllerTest extends Specification {

    @Autowired
    def WebApplicationContext webApplicationContext
    @Autowired
    def Filter springSecurityFilterChain

    @WithMockUser
    def "authenticate"() {
        given:
        def mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .defaultRequest(get("/").with(testSecurityContext()))
                .addFilter(springSecurityFilterChain)
                .build()
        when:
        def perform = mockMvc.perform(post(TOKEN_PATH)
                .contentType(APPLICATION_JSON)
                .content("{ \"username\":\"user\", \"password\":\"password\" }")
        )
        then:
        perform.andExpect(authenticated())
    }

    @Configuration
    @EnableWebMvc
    static class MvcTestConfig extends WebMvcConfigurerAdapter {

        def authenticationManagerMock = Mock(AuthenticationManager)

        @Bean
        AuthenticationManager authenticationManagerMock() {
            return
        }

        @Bean
        RedisOperationsSessionRepository sessionRepositoryMock() {

        }

        @Bean
        AuthenticationController authenticationController() {
            return new AuthenticationController(authenticationManagerMock(), AuthenticationControllerTest.this.sessionRepositoryMock())
        }
    }

    @EnableWebMvcSecurity
    static class SecurityTestConfig extends WebSecurityConfigurerAdapter {
    }
}
