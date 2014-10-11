package com.ctv.registration.adapter.rest;


import com.ctv.registration.adapter.rest.dto.User;

import java.util.List;

/**
 * @author Dmitry Kovalchuk
 */
public interface UserMvcAdapter {

    void createUser(User user);

    void deleteUser(Integer id);

    void updateUser(User user);

    User findUserById(Integer id);

    List<User> findAllUsers(int page, int size);
}
