package com.ctv.registration.core;

import com.ctv.registration.core.dto.User;
import com.ctv.registration.core.port.in.RegistrationPersistenceAdapter;

/**
 * @author Dmitry Kovalchuk
 */
public class RegistrationServiceImpl implements RegistrationService {

    private RegistrationPersistenceAdapter persistenceAdapter;

    public RegistrationServiceImpl(RegistrationPersistenceAdapter persistenceAdapter) {
        this.persistenceAdapter = persistenceAdapter;
    }


    @Override
    public void createUser(User user) {
        persistenceAdapter.createUser(user);
    }

    //todo user delete only himself
    @Override
    public void deleteUser(Integer id) {
        persistenceAdapter.deleteUser(id);
    }

    //todo user update only himself
    @Override
    public void updateUser(User user) {
        persistenceAdapter.updateUser(user);
    }

    @Override
    public User findUserById(Integer id) {
        return persistenceAdapter.findUserById(id);
    }
}
