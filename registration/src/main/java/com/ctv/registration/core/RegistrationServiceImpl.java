package com.ctv.registration.core;

import com.ctv.registration.core.dto.User;
import com.ctv.registration.core.port.in.UserPersistenceAdapter;
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
    public void createUser(User user) {
        persistenceAdapter.createUser(user);
    }

    //todo user delete only himself
    @PreAuthorize("authentication.getDetails().getId() == #id")
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
    @Transactional(readOnly = true)
    public User findUserById(Integer id) {
        return persistenceAdapter.findUserById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers(int page, int size) {
        return persistenceAdapter.findAllUsers(page, size);
    }


}
