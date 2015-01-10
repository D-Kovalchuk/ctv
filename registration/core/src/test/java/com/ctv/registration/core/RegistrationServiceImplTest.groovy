package com.ctv.registration.core
import com.ctv.registration.core.adapter.UserPersistenceAdapter
import com.ctv.registration.core.exception.DataConflictException
import com.ctv.registration.core.exception.ResourceNotFoundException
import com.ctv.registration.core.model.UserModel
import com.ctv.security.config.client.CtvUserDetailsBuilder
import com.ctv.shared.model.CtvUserDetails
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import static com.ctv.registration.core.exception.UserErrorCode.BAD_PASSWORD
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
    static final String ENCODED_PASSWORD = "encoded user"

    def userModelWithId
    def userModelWithoutId

    @Autowired
    RegistrationService registrationService

    @Autowired
    UserPersistenceAdapter persistenceAdapterMock

    @Autowired
    BCryptPasswordEncoder passwordEncoder

    void setup() {
        userModelWithId = new UserModel()
        userModelWithId.with {
            id = ID
            username = USERNAME
            password = PASSWORD
            email = EMAIL
            type = TYPE
            site = SITE
            enabled = ENABLED
        }

        userModelWithoutId = new UserModel()
        userModelWithoutId.with {
            username = USERNAME
            password = PASSWORD
            email = EMAIL
            type = TYPE
            site = SITE
            enabled = ENABLED
        }

        reset(persistenceAdapterMock)
    }

    def "should throw DataConflictException if new user request contains user's id"() {
        when:
        registrationService.createUser(userModelWithId)

        then:
        thrown(DataConflictException)
    }

    def "should create new User"() {
        given:
        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD)

        when:
        registrationService.createUser(userModelWithoutId)

        then:
        verify(persistenceAdapterMock).createUser(userModelWithoutId)
        userModelWithoutId.password == ENCODED_PASSWORD
    }

    def "should delete existing user"() {
        given:
        when(persistenceAdapterMock.findUserById(ID)).thenReturn(userModelWithId)

        when:
        registrationService.deleteUser(ID)

        then:
        !userModelWithId.enabled
        verify(persistenceAdapterMock).updateUser(userModelWithId)
    }

    def "should throw ResourceNotFoundException if user with passed id not found"() {
        given:
        when(persistenceAdapterMock.findUserById(ID)).thenReturn(null)

        when:
        registrationService.deleteUser(ID)

        then:
        thrown(ResourceNotFoundException)
    }

    def "should find user by id"() {
        given:
        when(persistenceAdapterMock.findUserById(ID)).thenReturn(userModelWithId)

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
        userModelWithId.with {
            it.id = 12
        }

        when:
        registrationService.updateUser(userModelWithId)

        then:
        thrown(AccessDeniedException)
    }

    def "should fail if anonymous user request update"() {
        when:
        registrationService.updateUser(userModelWithId)

        then:
        thrown(AuthenticationCredentialsNotFoundException)
    }

    @WithUserDetails("username")
    def "should update passed user"() {
        when:
        registrationService.updateUser(userModelWithId)

        then:
        verify(persistenceAdapterMock).updateUser(userModelWithId)
    }

    def "update password when old password == new password"() {
        when:
        when(persistenceAdapterMock.findUserById(ID)).thenReturn(userModelWithId)
        registrationService.updatePassword(ID, PASSWORD, "new password")

        then:
        userModelWithId.password == "new password"
        verify(persistenceAdapterMock).updateUser(userModelWithId)
    }

    def "update password when old password != new password"() {
        when:
        when(persistenceAdapterMock.findUserById(ID)).thenReturn(userModelWithId)
        registrationService.updatePassword(ID, "wrong old password", "new password")

        then:
        DataConflictException e = thrown()
        e.getErrorCode() == BAD_PASSWORD.code
        e.getMessage() == BAD_PASSWORD.message
    }


    @EnableGlobalMethodSecurity(prePostEnabled = true)
    @Configuration
    static class CoreTestConfiguration {

        @Bean
        UserPersistenceAdapter userPersistenceAdapter() {
            mock(UserPersistenceAdapter)
        }

        @Bean
        BCryptPasswordEncoder passwordEncoder() {
            mock(BCryptPasswordEncoder)
        }

        @Bean
        RegistrationService registrationService(UserPersistenceAdapter userPersistenceAdapter,
                                                BCryptPasswordEncoder passwordEncoder) {
            new RegistrationServiceImpl(userPersistenceAdapter, passwordEncoder)
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
                            .withPassword(ENCODED_PASSWORD)
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
