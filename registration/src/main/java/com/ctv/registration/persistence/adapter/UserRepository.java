package com.ctv.registration.persistence.adapter;

import com.ctv.registration.persistence.adapter.model.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Dmitry Kovalchuk
 */
@Repository
public interface UserRepository extends org.springframework.data.repository.Repository<UserEntity, Integer> {

    UserEntity save(UserEntity entity);

    void delete(Integer id);

    UserEntity getOne(Integer id);

    List<UserEntity> findAll();

}
