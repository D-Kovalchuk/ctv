package com.ctv.registration.persistence;

import com.ctv.registration.adapter.persistence.api.UserRepository;
import com.ctv.registration.adapter.persistence.model.User;
import org.springframework.stereotype.Repository;

/**
 * @author Dmitry Kovalchuk
 */
@Repository
public interface SpringUserRepository extends UserRepository,
        org.springframework.data.repository.Repository<User, Integer> {}
