package com.ctv.registration.adapter.persistence.converter

import com.ctv.registration.adapter.persistence.model.User
import spock.lang.Specification

/**
 * @author Timur Yarosh
 */
class UserToUserModelConverterTest extends Specification {

    public static final String USERNAME = "username"
    public static final String PASSWORD = "pw"
    public static final String EMAIL = "username@company.com"
    public static final String TYPE = "WATCHER"
    public static final String SITE = "http://company.com"
    public static final int ID = 0

    def "convert User to UserModel"() {
        given:
        def userEntity = new User()
        userEntity.with {
            id = ID
            username = USERNAME
            password = PASSWORD
            email = EMAIL
            type = TYPE
            site = SITE
        }

        when:
        def user = new UserToUserModelConverter().convert(userEntity)

        then:
        user.id == ID
        user.username == USERNAME
        user.password == PASSWORD
        user.email == EMAIL
        user.type == TYPE
        user.site == SITE
    }
}
