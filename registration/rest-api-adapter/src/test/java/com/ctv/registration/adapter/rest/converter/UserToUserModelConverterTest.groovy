package com.ctv.registration.adapter.rest.converter

import com.ctv.registration.adapter.rest.dto.User
import spock.lang.Specification

/**
 * @author Dmitry Kovalchuk
 */
class UserToUserModelConverterTest extends Specification {

    public static final String USERNAME = "username"
    public static final String PASSWORD = "pw"
    public static final String EMAIL = "username@company.com"
    public static final String TYPE = "WATCHER"
    public static final String SITE = "http://company.com"
    public static final int ID = 0

    def "Convert User to UserModel"() {
        given:
        def user = new User()
        user.with {
            id = ID
            username = USERNAME
            password = PASSWORD
            email = EMAIL
            type = TYPE
            site = SITE
        }

        when:
        def userModel = new UserToUserModelConverter().convert(user)

        then:
        userModel.id == ID
        userModel.username == USERNAME
        userModel.password == PASSWORD
        userModel.email == EMAIL
        userModel.type == TYPE
        userModel.site == SITE
    }

}
