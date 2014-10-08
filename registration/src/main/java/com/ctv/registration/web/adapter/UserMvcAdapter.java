package com.ctv.registration.web.adapter;


import com.ctv.registration.core.dto.User;

/**
 * @author Dmitry Kovalchuk
 */
public interface UserMvcAdapter {

    void createUser(User user);

    void deleteUser(Integer id);

    void updateUser(User user);

    User findUserById(Integer id);


}
