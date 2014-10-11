package com.ctv.registration.adapter.persistence

import com.ctv.registration.adapter.persistence.api.UserRepository
import com.ctv.registration.adapter.persistence.model.User
import com.ctv.registration.core.model.UserModel
import org.springframework.core.convert.ConversionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

/**
 * @author Timur Yarosh
 */
class UserPersistenceAdapterImplTest extends Specification {

    def userRepository = Mock(UserRepository)
    def conversionService = Mock(ConversionService)
    def persistenceAdapter = new UserPersistenceAdapterImpl(userRepository, conversionService)
    def user = new UserModel()
    def userEntity = new User()
    def id = 1

    def "save user"() {
        when:
        persistenceAdapter.createUser(user)

        then:
        1 * conversionService.convert(user, User) >> userEntity
        1 * userRepository.save(userEntity)
    }

    def "delete user"() {
        when:
        persistenceAdapter.deleteUser(id)

        then:
        1 * userRepository.delete(id)
    }

    def "update user"() {
        when:
        persistenceAdapter.updateUser(user)

        then:
        1 * conversionService.convert(user, User) >> userEntity
        1 * userRepository.save(userEntity)
    }

    def "find user by id"() {
        when:
        persistenceAdapter.findUserById(id)

        then:
        1 * userRepository.findOne(id) >> userEntity
        1 * conversionService.convert(userEntity, UserModel)
    }

    def "find all users"() {
        given:
        def pageNum = 0
        def size = 10
        def pageRequest = new PageRequest(pageNum, size)
        def page = Mock(Page)
        def userEntities = Arrays.asList(userEntity)
        page.getContent() >> userEntities

        when:
        persistenceAdapter.findAllUsers(pageNum, size)

        then:
        1 * userRepository.findAll(pageRequest) >> page
        1 * conversionService.convert(userEntities, _, _) >> userEntities
    }


}
