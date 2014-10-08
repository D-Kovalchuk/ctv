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
    private RegistrationRepository registrationRepository;

    public RegistrationPersistenceAdapterImpl(RegistrationRepository registrationRepository, ConversionService conversionService) {
        this.registrationRepository = registrationRepository;
        this.conversionService = conversionService;
    }

    @Override
    public void createUser(User user) {
        UserEntity userEntity = conversionService.convert(user, UserEntity.class);
        registrationRepository.saveAndFlush(userEntity);
    }

    @Override
    public void deleteUser(Integer id) {
        registrationRepository.delete(id);
    }

    @Override
    public void updateUser(User user) {
        UserEntity userEntity = conversionService.convert(user, UserEntity.class);
        registrationRepository.saveAndFlush(userEntity);
    }

    @Override
    public User findUserById(Integer id) {
        UserEntity userEntity = registrationRepository.getOne(id);
        return conversionService.convert(userEntity, User.class);
    }

}
