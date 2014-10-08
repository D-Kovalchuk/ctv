package com.ctv.registration.persistence.adapter

import com.ctv.registration.core.dto.User
import com.ctv.registration.persistence.adapter.model.UserEntity
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
    def user = new User()
    def userEntity = new UserEntity()
    def id = 1

    def "save user"() {
        when:
        persistenceAdapter.createUser(user)

        then:
        1 * conversionService.convert(user, UserEntity) >> userEntity
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
        1 * conversionService.convert(user, UserEntity) >> userEntity
        1 * userRepository.save(userEntity)
    }

    def "find user by id"() {
        when:
        persistenceAdapter.findUserById(id)

        then:
        1 * userRepository.getOne(id) >> userEntity
        1 * conversionService.convert(userEntity, User)
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
