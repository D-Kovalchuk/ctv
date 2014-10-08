package com.ctv.registration.persistence.adapter.converter

import com.ctv.registration.core.dto.User
import com.ctv.registration.persistence.adapter.model.UserEntity
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * @author Timur Yarosh
 */
@Component
class UserEntityToUserConverter implements Converter<UserEntity, User> {

    @Override
    User convert(UserEntity source) {
        def user = new User()
        user.with {
            id = source.id
            username = source.username
            password = source.password
            email = source.email
            type = source.type
            site = source.site
        }
        return user;
    }

}
