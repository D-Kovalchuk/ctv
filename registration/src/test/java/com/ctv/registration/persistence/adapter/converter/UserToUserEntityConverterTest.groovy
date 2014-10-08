package com.ctv.registration.persistence.adapter.converter

import com.ctv.registration.core.dto.User
import spock.lang.Specification

/**
 * @author Timur Yarosh
 */
class UserToUserEntityConverterTest extends Specification {

    public static final String USERNAME = "username"
    public static final String PASSWORD = "pw"
    public static final String EMAIL = "username@company.com"
    public static final String TYPE = "WATCHER"
    public static final String SITE = "http://company.com"
    public static final int ID = 0

    def "convert User to UserEntity"() {
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
        def userEntity = new UserToUserEntityConverter().convert(user)

        then:
        userEntity.id == ID
        userEntity.username == USERNAME
        userEntity.password == PASSWORD
        userEntity.email == EMAIL
        userEntity.type == TYPE
        userEntity.site == SITE
    }

}
