package com.ctv.registration.core;

import com.ctv.registration.core.dto.User;

import java.util.List;

/**
 * @author Dmitry Kovalchuk
 */
public interface RegistrationService {

    void createUser(User user);

    void deleteUser(Integer id);

    void updateUser(User user);

    User findUserById(Integer id);

    List<User> findAllUsers(int page, int size);
}
