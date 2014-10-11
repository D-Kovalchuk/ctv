package com.ctv.registration.adapter.persistence.converter

import com.ctv.registration.core.model.UserModel
import spock.lang.Specification
/**
 * @author Timur Yarosh
 */
class UserModelToUserConverterTest extends Specification {

    public static final String USERNAME = "username"
    public static final String PASSWORD = "pw"
    public static final String EMAIL = "username@company.com"
    public static final String TYPE = "WATCHER"
    public static final String SITE = "http://company.com"
    public static final int ID = 0
    public static final boolean ENABLED = true

    def "convert UserModel to User"() {
        given:
        def user = new UserModel()
        user.with {
            id = ID
            username = USERNAME
            password = PASSWORD
            email = EMAIL
            type = TYPE
            site = SITE
            enabled = ENABLED
        }

        when:
        def userEntity = new UserModelToUserConverter().convert(user)

        then:
        userEntity.id == ID
        userEntity.username == USERNAME
        userEntity.password == PASSWORD
        userEntity.email == EMAIL
        userEntity.type == TYPE
        userEntity.site == SITE
        userEntity.enabled
    }

}
