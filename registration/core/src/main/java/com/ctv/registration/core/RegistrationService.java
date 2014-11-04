package com.ctv.registration.core;

import com.ctv.registration.core.model.UserModel;

import java.util.List;

/**
 * @author Dmitry Kovalchuk
 */
public interface RegistrationService {

    void createUser(UserModel user);

    void deleteUser(Integer id);

    void updateUser(UserModel user);

    UserModel findUserById(Integer id);

    List<UserModel> findAllUsers(int page, int size);

    void updatePassword(int id, String oldPass, String newPass);
}
