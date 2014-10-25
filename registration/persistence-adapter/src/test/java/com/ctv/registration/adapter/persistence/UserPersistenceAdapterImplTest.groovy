package com.ctv.registration.adapter.persistence
import com.ctv.registration.adapter.persistence.api.UserRepository
import com.ctv.registration.adapter.persistence.model.User
import com.ctv.registration.core.exception.UsernameAlreadyExistsException
import com.ctv.registration.core.model.UserModel
import org.springframework.core.convert.ConversionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.orm.jpa.JpaSystemException
import spock.lang.Specification
/**
 * @author Timur Yarosh
 */
class UserPersistenceAdapterImplTest extends Specification {

    public static final String PASSWORD = "ps"
    public static final String USERNAME = "un"
    public static final String EMAIL = "email@gmail.com"
    public static final String TYPE = "or"
    public static final String SITE = "http://url.com"
    public static final int ID = 1

    def userRepository = Mock(UserRepository)
    def conversionService = Mock(ConversionService)
    def persistenceAdapter = new UserPersistenceAdapterImpl(userRepository, conversionService)
    def user = new UserModel()
    def userEntity

    void setup() {
        userEntity = new User()
        userEntity.with {
            id = ID
            password = PASSWORD
            username = USERNAME
            email = EMAIL
            type = TYPE
            site = SITE
        }
    }

    def "save user"() {
        when:
        persistenceAdapter.createUser(user)

        then:
        1 * conversionService.convert(user, User) >> userEntity
        1 * userRepository.save(userEntity)
    }

    def "should throw UsernameAlreadyExistsException if JpaSystemException has been thrown"() {
        given:
        userRepository.save(userEntity) >> { throw new JpaSystemException(new RuntimeException()) }
        conversionService.convert(user, User) >> userEntity

        when:
        persistenceAdapter.createUser(user)

        then:
        thrown(UsernameAlreadyExistsException)
    }

    def "delete user"() {
        when:
        persistenceAdapter.deleteUser(ID)

        then:
        1 * userRepository.delete(ID)
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
        persistenceAdapter.findUserById(ID)

        then:
        1 * userRepository.findOne(ID) >> userEntity
        userEntity.password == null
        1 * conversionService.convert(userEntity, UserModel)
    }

    def "find all users"() {
        given:
        def pageNum = 0
        def size = 10
        def pageRequest = new PageRequest(pageNum, size)
        def page = Mock(Page)
        def userEntities = [userEntity]
        page.getContent() >> userEntities

        when:
        persistenceAdapter.findAllUsers(pageNum, size)

        then:
        1 * userRepository.findAll(pageRequest) >> page
        page.getContent()[0].password == null
        1 * conversionService.convert(userEntities, _, _) >> userEntities
    }


}
