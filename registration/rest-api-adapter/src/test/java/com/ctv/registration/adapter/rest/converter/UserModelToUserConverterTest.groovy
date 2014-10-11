package com.ctv.registration.adapter.rest.converter

import com.ctv.registration.core.model.UserModel
import spock.lang.Specification

/**
 * @author Dmitry Kovalchuk
 */
class UserModelToUserConverterTest extends Specification {

    public static final String USERNAME = "username"
    public static final String PASSWORD = "pw"
    public static final String EMAIL = "username@company.com"
    public static final String TYPE = "WATCHER"
    public static final String SITE = "http://company.com"
    public static final int ID = 0

    def "convert UserModel to User"() {
        given:
        def userModel = new UserModel()
        userModel.with {
            id = ID
            username = USERNAME
            password = PASSWORD
            email = EMAIL
            type = TYPE
            site = SITE
        }

        when:
        def user = new UserModelToUserConverter().convert(userModel)

        then:
        user.id == ID
        user.username == USERNAME
        user.password == PASSWORD
        user.email == EMAIL
        user.type == TYPE
        user.site == SITE
    }

}
