package com.ctv.registration.core.port.in;

import com.ctv.registration.core.dto.User;

/**
 * @author Dmitry Kovalchuk
 */
public interface UserPersistenceAdapter {

    void createUser(User user);

    void deleteUser(Integer id);

    void updateUser(User user);

    User findUserById(Integer id);

}
