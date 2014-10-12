package com.ctv.registration.core

import com.ctv.registration.core.adapter.UserPersistenceAdapter
import com.ctv.registration.core.exception.UserIdNotFoundException
import com.ctv.registration.core.model.UserModel
import com.ctv.security.config.client.CtvUserDetails
import com.ctv.security.config.client.CtvUserDetailsBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import static org.mockito.Mockito.*

/**
 * @author Timur Yarosh
 */
@ContextConfiguration(classes = [CoreTestConfiguration])
class RegistrationServiceImplTest extends Specification {

    static final String USERNAME = "username"
    static final String PASSWORD = "pw"
    static final String EMAIL = "username@company.com"
    static final String TYPE = "WATCHER"
    static final String SITE = "http://company.com"
    static final int ID = 0
    static final boolean ENABLED = true
    static final String ROLE = "USER"

    def userModel

    @Autowired
    RegistrationService registrationService
    @Autowired
    UserPersistenceAdapter persistenceAdapterMock

    void setup() {
        userModel = new UserModel()
        userModel.with {
            id = ID
            username = USERNAME
            password = PASSWORD
            email = EMAIL
            type = TYPE
            site = SITE
            enabled = ENABLED
        }

        reset(persistenceAdapterMock)
    }

    def "should create new User"() {
        when:
        registrationService.createUser(userModel)

        then:
        verify(persistenceAdapterMock).createUser(userModel)
    }

    def "should delete existing user"() {
        given:
        when(persistenceAdapterMock.findUserById(ID)).thenReturn(userModel)

        when:
        registrationService.deleteUser(ID)

        then:
        !userModel.enabled
        verify(persistenceAdapterMock).updateUser(userModel)
    }

    def "should throw UserIdNotFoundException if user with passed id not found"() {
        given:
        when(persistenceAdapterMock.findUserById(ID)).thenReturn(null)

        when:
        registrationService.deleteUser(ID)

        then:
        thrown(UserIdNotFoundException)
    }

    def "should find user by id"() {
        given:
        when(persistenceAdapterMock.findUserById(ID)).thenReturn(userModel)

        when:
        registrationService.findUserById(ID)

        then:
        verify(persistenceAdapterMock).findUserById(ID) == null
    }

    def "should find all users"() {
        given:
        def page = 0
        def size = 10

        when:
        registrationService.findAllUsers(page, size)

        then:
        verify(persistenceAdapterMock).findAllUsers(page, size) == null
    }

    @WithUserDetails("username")
    def "should fail if passed user id not equal to authenticated user id"() {
        given:
        userModel.with {
            it.id = 12
        }

        when:
        registrationService.updateUser(userModel)

        then:
        thrown(AccessDeniedException)
    }

    def "should fail if anonymous user request update"() {
        when:
        registrationService.updateUser(userModel)

        then:
        thrown(AuthenticationCredentialsNotFoundException)
    }

    @WithUserDetails("username")
    def "should update passed user"() {
        when:
        registrationService.updateUser(userModel)

        then:
        verify(persistenceAdapterMock).updateUser(userModel)
    }

    @EnableGlobalMethodSecurity(prePostEnabled = true)
    @Configuration
    static class CoreTestConfiguration {

        @Bean
        UserPersistenceAdapter userPersistenceAdapter() {
            mock(UserPersistenceAdapter)
        }

        @Bean
        RegistrationService registrationService(UserPersistenceAdapter userPersistenceAdapter) {
            new RegistrationServiceImpl(userPersistenceAdapter)
        }

        @Autowired
        void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService());
        }

        @Bean
        UserDetailsService userDetailsService() {
            new UserDetailsService() {
                @Override
                CtvUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                    return CtvUserDetailsBuilder.get()
                            .withUsername(USERNAME)
                            .withPassword(PASSWORD)
                            .withAuthorities(AuthorityUtils.createAuthorityList(ROLE))
                            .withId(ID)
                            .withEmail(EMAIL)
                            .withType(TYPE)
                            .withSite(SITE)
                            .build();
                }
            }
        }

    }

}
