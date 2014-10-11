package com.ctv.registration.adapter.persistence.converter

import com.ctv.registration.adapter.persistence.model.User
import com.ctv.registration.core.model.UserModel
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * @author Timur Yarosh
 */
@Component
class UserToUserModelConverter implements Converter<User, UserModel> {

    @Override
    UserModel convert(User source) {
        def user = new UserModel()
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
