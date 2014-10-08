package com.ctv.registration.persistence.adapter;

import com.ctv.registration.persistence.adapter.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * @author Dmitry Kovalchuk
 */
@Repository
public interface UserRepository extends org.springframework.data.repository.Repository<UserEntity, Integer> {

    UserEntity save(UserEntity entity);

    void delete(Integer id);

    UserEntity getOne(Integer id);

    Page<UserEntity> findAll(Pageable pageable);

}
