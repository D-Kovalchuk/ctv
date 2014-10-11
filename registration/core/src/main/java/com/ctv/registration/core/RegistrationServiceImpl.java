package com.ctv.registration.core;

import com.ctv.registration.core.adapter.UserPersistenceAdapter;
import com.ctv.registration.core.model.UserModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Dmitry Kovalchuk
 */
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

    private UserPersistenceAdapter persistenceAdapter;

    public RegistrationServiceImpl(UserPersistenceAdapter persistenceAdapter) {
        this.persistenceAdapter = persistenceAdapter;
    }

    @Override
    public void createUser(UserModel user) {
        persistenceAdapter.createUser(user);
    }

    @Override
    public void deleteUser(Integer id) {
        UserModel user = persistenceAdapter.findUserById(id);
        if (user == null) {
            throw new UserIdNotFoundException(id);
        }
        user.setEnabled(false);
        persistenceAdapter.updateUser(user);
    }

    @Override
    @PreAuthorize("principal.id == #user.id")
    public void updateUser(UserModel user) {
        persistenceAdapter.updateUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserModel findUserById(Integer id) {
        UserModel userById = persistenceAdapter.findUserById(id);
        if (userById == null) {
            throw new UserIdNotFoundException(id);
        }
        return userById;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserModel> findAllUsers(int page, int size) {
        return persistenceAdapter.findAllUsers(page, size);
    }


}
