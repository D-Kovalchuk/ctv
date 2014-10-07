package com.ctv.registration.persistence.adapter;

import com.ctv.registration.core.dto.User;
import com.ctv.registration.core.port.in.RegistrationPersistenceAdapter;
import com.ctv.registration.persistence.adapter.model.Account;
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
        Account account = conversionService.convert(user, Account.class);
        registrationRepository.saveAndFlush(account);
    }

    @Override
    public void deleteUser(Integer id) {
        registrationRepository.delete(id);
    }

    @Override
    public void updateUser(User user) {
        Account account = conversionService.convert(user, Account.class);
        registrationRepository.saveAndFlush(account);
    }

    @Override
    public User findUserById(Integer id) {
        Account account = registrationRepository.getOne(id);
        return conversionService.convert(account, User.class);
    }

}
