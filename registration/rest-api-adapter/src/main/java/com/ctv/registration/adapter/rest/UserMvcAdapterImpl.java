package com.ctv.registration.adapter.rest;

import com.ctv.registration.adapter.rest.dto.User;
import com.ctv.registration.core.RegistrationService;
import com.ctv.registration.core.model.UserModel;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

import java.util.List;

/**
 * @author Dmitry Kovalchuk
 */
public class UserMvcAdapterImpl implements UserMvcAdapter {

    private RegistrationService registrationService;
    private ConversionService conversionService;

    public UserMvcAdapterImpl(RegistrationService registrationService, ConversionService conversionService) {
        this.registrationService = registrationService;
        this.conversionService = conversionService;
    }

    @Override
    public void createUser(User user) {
        UserModel domainUsers = conversionService.convert(user, UserModel.class);
        registrationService.createUser(domainUsers);
    }

    @Override
    public void deleteUser(Integer id) {
        registrationService.deleteUser(id);
    }

    @Override
    public void updateUser(User user) {
        UserModel domainUsers = conversionService.convert(user, UserModel.class);
        registrationService.updateUser(domainUsers);
    }

    @Override
    public User findUserById(Integer id) {
        UserModel userById = registrationService.findUserById(id);
        return conversionService.convert(userById, User.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findAllUsers(int page, int size) {
        List<UserModel> allUsers = registrationService.findAllUsers(page, size);
        TypeDescriptor collection = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(User.class));
        TypeDescriptor collection1 = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(UserModel.class));
        return (List<User>) conversionService.convert(allUsers, collection1, collection);
    }
}
