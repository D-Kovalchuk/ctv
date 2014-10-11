package com.ctv.registration.adapter.persistence.converter

import com.ctv.registration.adapter.persistence.model.User
import com.ctv.registration.core.model.UserModel
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
/**
 * @author Timur Yarosh
 */
@Component
class UserModelToUserConverter implements Converter<UserModel, User> {

    @Override
    User convert(UserModel source) {
        def userEntity = new User()
        userEntity.with {
            id = source.id
            username = source.username
            password = source.password
            email = source.email
            type = source.type
            site = source.site
            enabled = source.enabled
        }
        return userEntity;
    }

}
