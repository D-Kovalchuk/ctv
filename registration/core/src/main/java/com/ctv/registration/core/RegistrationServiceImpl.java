package com.ctv.registration.core;

import com.ctv.registration.core.adapter.UserPersistenceAdapter;
import com.ctv.registration.core.exception.DataConflictException;
import com.ctv.registration.core.exception.ResourceNotFoundException;
import com.ctv.registration.core.model.UserModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ctv.registration.core.exception.ErrorData.BAD_PASSWORD;
import static com.ctv.registration.core.exception.ErrorData.PAYLOAD_WITH_USER_ID;
import static com.ctv.registration.core.exception.ErrorData.USER_ID_NOT_FOUND;
import static java.util.Objects.nonNull;

/**
 * @author Dmitry Kovalchuk
 */
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

    private final UserPersistenceAdapter persistenceAdapter;
    private final BCryptPasswordEncoder passwordEncoder;

    public RegistrationServiceImpl(UserPersistenceAdapter persistenceAdapter, BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.persistenceAdapter = persistenceAdapter;
    }

    @Override
    public void createUser(UserModel user) {
        if (nonNull(user.getId())) {
            throw new DataConflictException(PAYLOAD_WITH_USER_ID);
        }
        String password = user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        persistenceAdapter.createUser(user);
    }

    @Override
    public void deleteUser(Integer id) {
        UserModel user = persistenceAdapter.findUserById(id);
        if (user == null) {
            throw new ResourceNotFoundException(USER_ID_NOT_FOUND);
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
            throw new ResourceNotFoundException(USER_ID_NOT_FOUND);
        }
        return userById;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserModel> findAllUsers(int page, int size) {
        return persistenceAdapter.findAllUsers(page, size);
    }

    @Override
    public void updatePassword(int id, String oldPass, String newPass) {
        UserModel user = persistenceAdapter.findUserById(id);
        if (user.getPassword().equals(oldPass)) {
            user.setPassword(newPass);
            persistenceAdapter.updateUser(user);
        } else {
            throw new DataConflictException(BAD_PASSWORD);
        }
    }


}
