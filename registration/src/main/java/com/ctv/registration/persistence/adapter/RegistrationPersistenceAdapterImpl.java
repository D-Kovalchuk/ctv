package com.ctv.registration.persistence.adapter;

import com.ctv.registration.core.dto.User;
import com.ctv.registration.core.port.in.RegistrationPersistenceAdapter;
import com.ctv.registration.persistence.adapter.model.UserEntity;
import org.springframework.core.convert.ConversionService;

/**
 * @author Dmitry Kovalchuk
 */
public class RegistrationPersistenceAdapterImpl implements RegistrationPersistenceAdapter {

    private ConversionService conversionService;
    private UserRepository userRepository;

    public RegistrationPersistenceAdapterImpl(UserRepository userRepository, ConversionService conversionService) {
        this.userRepository = userRepository;
        this.conversionService = conversionService;
    }

    @Override
    public void createUser(User user) {
        UserEntity userEntity = conversionService.convert(user, UserEntity.class);
        userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.delete(id);
    }

    @Override
    public void updateUser(User user) {
        UserEntity userEntity = conversionService.convert(user, UserEntity.class);
        userRepository.save(userEntity);
    }

    @Override
    public User findUserById(Integer id) {
        UserEntity userEntity = userRepository.getOne(id);
        return conversionService.convert(userEntity, User.class);
    }

}
