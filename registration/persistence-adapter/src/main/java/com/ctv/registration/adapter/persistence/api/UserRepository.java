package com.ctv.registration.adapter.persistence.api;

import com.ctv.registration.adapter.persistence.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Dmitry Kovalchuk
 */
public interface UserRepository {

    User save(User entity);

    void delete(Integer id);

    User findOne(Integer id);

    Page<User> findAll(Pageable pageable);

}
