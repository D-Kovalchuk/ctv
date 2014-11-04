package com.ctv.registration.adapter.rest
import com.ctv.registration.adapter.rest.dto.User
import com.ctv.registration.core.RegistrationService
import com.ctv.registration.core.model.UserModel
import org.springframework.core.convert.ConversionService
import org.springframework.core.convert.TypeDescriptor
import spock.lang.Specification

import static java.util.Arrays.asList
import static org.springframework.core.convert.TypeDescriptor.collection
import static org.springframework.core.convert.TypeDescriptor.valueOf
/**
 * @author Dmitry Kovalchuk
 */
class UserMvcAdapterImplTest extends Specification {

    public static final String OLD_PASSWORD = "old_password"
    public static final String NEW_PASSWORD = "new_password"
    RegistrationService registrationService = Mock()
    ConversionService conversionService = Mock()
    UserMvcAdapter userMvcAdapter
    User user = new User()
    UserModel model = new UserModel()
    static final Integer ID = 1;

    void setup() {
        userMvcAdapter = new UserMvcAdapterImpl(registrationService, conversionService)
    }

    def "create user"() {
        when:
        userMvcAdapter.createUser(user)

        then:
        1 * conversionService.convert(user, UserModel) >> model
        1 * registrationService.createUser(model)
    }

    def "delete user"() {
        when:
        userMvcAdapter.deleteUser(ID)

        then:
        1 * registrationService.deleteUser(ID)
    }

    def "update user"() {
        when:
        userMvcAdapter.updateUser(user)

        then:
        1 * conversionService.convert(user, UserModel) >> model
        1 * registrationService.updateUser(model)
    }

    def "find user by id"() {
        when:
        def foundUser = userMvcAdapter.findUserById(ID)

        then:
        1 * registrationService.findUserById(ID) >> model
        1 * conversionService.convert(model, User) >> user
        user == foundUser
    }

    def "find all users"() {
        given:
        def list = asList(model)
        TypeDescriptor userList = collection(List.class, valueOf(User.class));
        TypeDescriptor userModelList = collection(List.class, valueOf(UserModel.class));

        when:
        userMvcAdapter.findAllUsers(1, 10)

        then:
        1 * registrationService.findAllUsers(1, 10) >> list
        1 * conversionService.convert(list, userModelList, userList)
    }

    def "update password"() {
        when:
        userMvcAdapter.updatePassword(ID, OLD_PASSWORD, NEW_PASSWORD)

        then:
        1 * registrationService.updatePassword(ID, OLD_PASSWORD, NEW_PASSWORD)
    }
}
