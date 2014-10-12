package com.ctv.registration.adapter.persistence;

import com.ctv.registration.adapter.persistence.api.UserRepository;
import com.ctv.registration.adapter.persistence.model.User;
import com.ctv.registration.core.exception.UsernameAlreadyExistsException;
import com.ctv.registration.core.adapter.UserPersistenceAdapter;
import com.ctv.registration.core.model.UserModel;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.jpa.JpaSystemException;

import java.util.List;

/**
 * @author Dmitry Kovalchuk
 */
public class UserPersistenceAdapterImpl implements UserPersistenceAdapter {

    private ConversionService conversionService;
    private UserRepository userRepository;

    public UserPersistenceAdapterImpl(UserRepository userRepository, ConversionService conversionService) {
        this.userRepository = userRepository;
        this.conversionService = conversionService;
    }

    @Override
    public void createUser(UserModel user) {
        User userEntity = conversionService.convert(user, User.class);
        try {
            userRepository.save(userEntity);
            //todo change to DataAccessException
        } catch (JpaSystemException e) {
            String username = user.getUsername();
            throw new UsernameAlreadyExistsException(username);
        }
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.delete(id);
    }

    @Override
    public void updateUser(UserModel user) {
        User userEntity = conversionService.convert(user, User.class);
        userRepository.save(userEntity);
    }

    @Override
    public UserModel findUserById(Integer id) {
        User userEntity = userRepository.findOne(id);
        return conversionService.convert(userEntity, UserModel.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserModel> findAllUsers(int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size);
        Page<User> entities = userRepository.findAll(pageRequest);
        TypeDescriptor sourceType = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(User.class));
        TypeDescriptor targetType = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(UserModel.class));
        return (List<UserModel>) conversionService.convert(entities.getContent(), sourceType, targetType);
    }

}
