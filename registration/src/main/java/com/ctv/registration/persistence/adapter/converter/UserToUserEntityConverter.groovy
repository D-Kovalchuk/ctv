package com.ctv.registration.persistence.adapter.converter

import com.ctv.registration.core.dto.User
import com.ctv.registration.persistence.adapter.model.UserEntity
import org.springframework.core.convert.converter.Converter

/**
 * @author Timur Yarosh
 */
class UserToUserEntityConverter implements Converter<User, UserEntity> {

    @Override
    UserEntity convert(User source) {
        def userEntity = new UserEntity()
        userEntity.with {
            id = source.id
            username = source.username
            password = source.password
            email = source.email
            type = source.type
            site = source.site
        }
        return userEntity;
    }

}
