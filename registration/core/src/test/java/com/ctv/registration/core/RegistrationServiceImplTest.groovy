package com.ctv.registration.core

import com.ctv.registration.core.adapter.UserPersistenceAdapter
import com.ctv.registration.core.model.UserModel
import spock.lang.Specification

/**
 * @author Timur Yarosh
 */
class RegistrationServiceImplTest extends Specification {

    static final String USERNAME = "username"
    static final String PASSWORD = "pw"
    static final String EMAIL = "username@company.com"
    static final String TYPE = "WATCHER"
    static final String SITE = "http://company.com"
    static final int ID = 0
    static final boolean ENABLED = true

    def persistenceAdapterMock = Mock(UserPersistenceAdapter)
    def registrationService = new RegistrationServiceImpl(persistenceAdapterMock)
    def userModel

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
    }

    def "should create new User"() {
        when:
        registrationService.createUser(userModel)

        then:
        1 * persistenceAdapterMock.createUser(userModel)
    }

    def "should delete existing user"() {
        given:
        persistenceAdapterMock.findUserById(ID) >> userModel

        when:
        registrationService.deleteUser(ID)

        then:
        !userModel.enabled
        1 * persistenceAdapterMock.updateUser(userModel)
    }

    def "should throw UserIdNotFoundException if user with passed id not found"() {
        given:
        persistenceAdapterMock.findUserById(ID) >> null

        when:
        registrationService.deleteUser(ID)

        then:
        thrown(UserIdNotFoundException)
    }

    def "should update existing user"() {
        when:
        registrationService.updateUser(userModel)

        then:
        1 * persistenceAdapterMock.updateUser(userModel)
    }

    def "should find user by id"() {
        when:
        registrationService.findUserById(ID)

        then:
        1 * persistenceAdapterMock.findUserById(ID) >> userModel
    }

    def "should find all users"() {
        given:
        def page = 0
        def size = 10

        when:
        registrationService.findAllUsers(page, size)

        then:
        1 * persistenceAdapterMock.findAllUsers(page, size)
    }
}
