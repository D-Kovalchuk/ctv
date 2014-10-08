package com.ctv.registration.web.adapter;

import com.ctv.registration.core.RegistrationService;
import com.ctv.registration.core.dto.User;

import java.util.List;

/**
 * @author Dmitry Kovalchuk
 */
public class UserMvcAdapterImpl implements UserMvcAdapter {

    private RegistrationService registrationService;

    public UserMvcAdapterImpl(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public void createUser(User user) {
        registrationService.createUser(user);
    }

    @Override
    public void deleteUser(Integer id) {
        registrationService.deleteUser(id);
    }

    @Override
    public void updateUser(User user) {
        registrationService.updateUser(user);
    }

    @Override
    public User findUserById(Integer id) {
        return registrationService.findUserById(id);
    }

    @Override
    public List<User> findAllUsers(int page, int size) {
        return registrationService.findAllUsers(page, size);
    }
}
